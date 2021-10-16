package com.ssafy.match.member.service;

import com.ssafy.match.member.dto.*;
import com.ssafy.match.common.entity.*;
import com.ssafy.match.member.entity.EmailCheck;
import com.ssafy.match.member.entity.MemberTechstack;
import com.ssafy.match.member.entity.composite.CompositeMemberTechstack;
import com.ssafy.match.common.repository.*;
import com.ssafy.match.jwt.TokenProvider;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.common.entity.DetailPosition;
import com.ssafy.match.common.repository.DetailPositionRepository;
import com.ssafy.match.member.repository.EmailCheckRepository;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.member.repository.MemberTechstackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TechstackRepository techstackRepository;
    private final DetailPositionRepository detailPositionRepository;
    private final MemberTechstackRepository memberTechstackRepository;
    private final EmailCheckRepository emailCheckRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Transactional
    public Boolean certEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return Boolean.FALSE;
        } else {
            String key = certified_key();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom(from);
            message.setSubject("이메일 인증");
            message.setText(key);
            javaMailSender.send(message);
            Optional<EmailCheck> emailCheck = emailCheckRepository.findByEmail(email);
            if(emailCheck.isEmpty()) {
                EmailCheck check = new EmailCheck(email, key, Boolean.FALSE);
                emailCheckRepository.save(check);
            } else {
                emailCheck.get().updateKey(key);
            }
            return Boolean.TRUE;
        }
    }

    @Transactional
    public String emailAuthCode(EmailCertRequestDto emailCertRequestDto) {
        Optional<EmailCheck> emailCheck = emailCheckRepository.findByEmail(emailCertRequestDto.getEmail());
        if (emailCheck.isPresent()) {
            if (emailCheck.get().getAuthCode().equals(emailCertRequestDto.getAuthCode())) {
                emailCheck.get().updateIsCheck(Boolean.TRUE);
                return emailCheck.get().getEmail();
            }
        }
        return "";
    }

    @Transactional(readOnly = true)
    public Boolean checkNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional
    public MemberResponseDto signup(SignupRequestDto signupRequestDto) throws Exception {
        if (memberRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        if (memberRepository.existsByNickname(signupRequestDto.getNickname())) {
            throw new Exception("중복된 닉네임 입니다");
        }
        Optional<EmailCheck> emailCheck = emailCheckRepository.findByEmail(signupRequestDto.getEmail());
        if (emailCheck.isEmpty() || emailCheck.get().getIs_check() == Boolean.FALSE) {
            throw new Exception("email인증이 완료되지 않았습니다!");
        }
        Member member = signupRequestDto.toMember(passwordEncoder);
        Member ret = memberRepository.save(member);

        if (signupRequestDto.getDpositionList() != null) {
            addDetailPosition(signupRequestDto.getDpositionList(), ret);
        }
        if (signupRequestDto.getTechList() != null) {
            addTechList(signupRequestDto.getTechList(), ret);
        }
        return MemberResponseDto.of(ret);
    }

    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) throws Exception{
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //탈퇴회원 체크
        Member member = memberRepository.getById(Long.parseLong(authentication.getName()));
        if (!member.getIs_active()) {
            throw new Exception("탈퇴한 회원입니다!");
        }
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public void addDetailPosition(List<String> dPositionList, Member member) {
        for (String dPosition : dPositionList) {
            DetailPosition innerDposition = DetailPosition
                    .builder()
                    .member(member)
                    .name(dPosition)
                    .build();
            detailPositionRepository.save(innerDposition);
        }
    }

    @Transactional
    public void addTechList(HashMap<String, String> techList, Member member) throws Exception {
        for (Map.Entry<String, String> entry : techList.entrySet()) {
            Techstack techstack = techstackRepository.findByName(entry.getKey())
                    .orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
            validLevel(entry.getValue());
            CompositeMemberTechstack compositeMemberTechstack = CompositeMemberTechstack
                    .builder()
                    .member(member)
                    .techstack(techstack)
                    .build();
            MemberTechstack memberTechstack = MemberTechstack.builder().compositeMemberTechstack(compositeMemberTechstack).level(entry.getValue()).build();
            memberTechstackRepository.save(memberTechstack);
        }
    }

    @Transactional(readOnly = true)
    public void validLevel(String level) throws Exception {
        if (!Stream.of(Level.values()).map(Enum::name)
                .collect(Collectors.toList()).contains(level)) {
            throw new Exception("존재하지 않는 level입니다");
        }
    }

    private String certified_key() { //10자리 임의의 문자열을 만드는 함수
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = random.nextInt(75) + 48; //0<=num<75
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 10);
        return sb.toString();
    }

}