package com.ssafy.match.member.service;

import com.ssafy.match.member.dto.*;
import com.ssafy.match.db.entity.*;
import com.ssafy.match.db.entity.embedded.CompositeMemberTechstack;
import com.ssafy.match.db.repository.*;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.jwt.TokenProvider;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberBeginnerTechstack;
import com.ssafy.match.member.entity.MemberExperiencedTechstack;
import com.ssafy.match.member.entity.Position;
import com.ssafy.match.member.repository.MemberBeginnerTechstackRepository;
import com.ssafy.match.member.repository.MemberExperiencedTechstackRepository;
import com.ssafy.match.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberExperiencedTechstackRepository memberExperiencedTechstackRepository;
    private final TechstackRepository techstackRepository;
    private final MemberBeginnerTechstackRepository memberBeginnerTechstackRepository;
    private final PositionRepository positionRepository;
    private final DBFileRepository dbFileRepository;

    @Transactional(readOnly = true)
    public Boolean checkEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    public Boolean checkNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) throws Exception {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Member member = memberRequestDto.toMember(passwordEncoder);
        Member ret = memberRepository.save(member);
//        DBFile coverPic = setCoverPic(memberRequestDto.getCover_pic());
//        DBFile portfolio = setPortfolioUuid(memberRequestDto.getPortfolio_uuid());

        if (memberRequestDto.getExpTechList() != null){
            for (String techExp : memberRequestDto.getExpTechList()) {
                Techstack techstackExp = techstackRepository.findByName(techExp)
                        .orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                CompositeMemberTechstack compositeMemberTechstackExp = CompositeMemberTechstack
                        .builder()
                        .member(ret)
                        .techstack(techstackExp)
                        .build();
                MemberExperiencedTechstack memberExperiencedTechstack = MemberExperiencedTechstack.builder().compositeMemberTechstack(compositeMemberTechstackExp).build();

                memberExperiencedTechstackRepository.save(memberExperiencedTechstack);
                //            MemberExperiencedTechstack memberExperiencedTechstack = memberRequestDto.toMemberExperiencedTechstack(member, techstack);
                //            memberExperiencedTechstackRepository.save(memberExperiencedTechstack);
            }
        }
        if (memberRequestDto.getBeginTechList() != null){
            for (String techBegin : memberRequestDto.getBeginTechList()) {
                Techstack techstackBegin = techstackRepository.findByName(techBegin)
                        .orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                CompositeMemberTechstack compositeMemberTechstackBegin = CompositeMemberTechstack
                        .builder()
                        .member(ret)
                        .techstack(techstackBegin)
                        .build();
                MemberBeginnerTechstack memberBeginnerTechstack = MemberBeginnerTechstack.builder().compositeMemberTechstack(compositeMemberTechstackBegin).build();
                memberBeginnerTechstackRepository.save(memberBeginnerTechstack);
            }
        }
        if (memberRequestDto.getDpositionList() != null) {
            for (String dposition : memberRequestDto.getDpositionList()) {
                Position innerDposition = Position
                        .builder()
                        .member(ret)
                        .name(dposition)
                        .build();
                positionRepository.save(innerDposition);
            }
        }
//        member.setCover_pic(coverPic);
//        member.setPortfolio(portfolio);
        return MemberResponseDto.of(ret);
    }

    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) throws Exception{
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
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

    public DBFile setCoverPic(String uuid) {
//        if(uuid == null) {
//            member.setCover_pic(null);
//            return;
//        }
        DBFile dbFile = dbFileRepository.getById(uuid);
        return dbFile;
    }

    @Transactional
    public DBFile setPortfolioUuid(String uuid) {
//        if(uuid == null) {
//            member.setPortfolio(null);
//            return;
//        }
        DBFile dbFile = dbFileRepository.getById(uuid);
//        member.setPortfolio(dbFile);
        return dbFile;
    }
}