package com.ssafy.match.member.service;

import com.ssafy.match.common.dto.DetailPositionInterface;
import com.ssafy.match.group.club.repository.MemberClubRepository;
import com.ssafy.match.group.study.repository.MemberStudyRepository;
import com.ssafy.match.member.dto.*;
import com.ssafy.match.common.entity.*;
import com.ssafy.match.member.dto.request.*;
import com.ssafy.match.member.dto.response.*;
import com.ssafy.match.member.entity.composite.CompositeMemberTechstack;
import com.ssafy.match.common.repository.*;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.group.project.repository.MemberProjectRepository;
import com.ssafy.match.member.entity.*;
import com.ssafy.match.common.repository.DetailPositionRepository;
import com.ssafy.match.member.repository.*;
import com.ssafy.match.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final DBFileRepository dbFileRepository;
    private final MemberClubRepository memberClubRepository;
    private final MemberProjectRepository memberProjectRepository;
    private final MemberSnsRepository memberSnsRepository;
    private final DetailPositionRepository detailPositionRepository;
    private final TechstackRepository techstackRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberStudyRepository memberStudyRepository;
    private final MemberTechstackRepository memberTechstackRepository;
    private final CareerRepository careerRepository;
    private final CertificationRepository certificationRepository;
    private final EducationRepository educationRepository;

    @Transactional(readOnly = true)
    public Boolean checkPassword(MemberCheckPasswordDto memberCheckPasswordDto) {
        UsernamePasswordAuthenticationToken authenticationToken = memberCheckPasswordDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional
    public HttpStatus updatePassword(ChangePasswordDto changePasswordDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("잘못된 토큰입니다."));
        changePassword(member, changePasswordDto);
        return HttpStatus.OK;
    }

    @Transactional(readOnly = true)
    public MypageResponseDto getMyPage(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<CareerInterface> careers = careerRepository.findAllByMember(member);
        List<EducationInterface> educations = educationRepository.findAllByMember(member);
        List<CertificationInterface> certifications = certificationRepository.findAllByMember(member);
        List<MemberTechstackInterface> techList = memberTechstackRepository.findTechstackByMember(member);
        List<MemberSns> snsList = memberSnsRepository.findAllByMember(member);
        List<DetailPositionInterface> dpositionList = detailPositionRepository.findAllByMemberWithInterface(member);

        MypageResponseDto mypageResponseDto = MypageResponseDto.of(member, careers, educations, certifications, techList, snsList, dpositionList);
        return mypageResponseDto;
    }

    @Transactional(readOnly = true)
    public MypageResponseDto getMyPage() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<CareerInterface> careers = careerRepository.findAllByMember(member);
        List<EducationInterface> educations = educationRepository.findAllByMember(member);
        List<CertificationInterface> certifications = certificationRepository.findAllByMember(member);
        List<MemberTechstackInterface> techList = memberTechstackRepository.findTechstackByMember(member);
        List<MemberSns> snsList = memberSnsRepository.findAllByMember(member);
        List<DetailPositionInterface> dpositionList = detailPositionRepository.findAllByMemberWithInterface(member);

        MypageResponseDto mypageResponseDto = MypageResponseDto.of(member, careers, educations, certifications, techList, snsList, dpositionList);
        return mypageResponseDto;
    }

    @Transactional(readOnly = true)
    public MemberBasicinfoResponseDto getMemberBasicinfo() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        MemberBasicinfoResponseDto memberBasicinfoResponseDto = MemberBasicinfoResponseDto.of(member);
        return memberBasicinfoResponseDto;
    }

    @Transactional
    public HttpStatus updateMemberBasicInfo(MemberBasicInfoRequestDto memberBasicinfoRequestDto) throws Exception {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        validNickname(member, memberBasicinfoRequestDto.getNickname());
        memberBasicinfoRequestDto.setMember(member);
        setCoverPic(member, memberBasicinfoRequestDto.getCoverpic_uuid());
        return HttpStatus.OK;
    }

    @Transactional(readOnly = true)
    public MemberCareerAllResponseDto getMemberCareerAll() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<CareerInterface> careers = careerRepository.findAllByMember(member);
        List<EducationInterface> educations = educationRepository.findAllByMember(member);
        List<CertificationInterface> certifications = certificationRepository.findAllByMember(member);

        MemberCareerAllResponseDto memberCareerAllResponseDto = MemberCareerAllResponseDto.of(careers, educations, certifications);
        return memberCareerAllResponseDto;
    }

    @Transactional(readOnly = true)
    public CareerResponseDto getMemberCareer(Long id) {
        CareerResponseDto careerResponseDto = careerRepository.findById(id).map(CareerResponseDto::of).orElseThrow(() -> new RuntimeException("해당 경력이 없습니다!"));
        return careerResponseDto;
    }

    @Transactional
    public HttpStatus createMemberCareer(MemberCareerRequestDto memberCareerRequestDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Career career = memberCareerRequestDto.toCareer(member);
        careerRepository.save(career);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus updateMemberCareer(Long id, MemberCareerUpdateRequestDto memberCareerUpdateRequestDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Career career = careerRepository.findByMemberAndId(member,id).orElseThrow(() -> new NullPointerException("잘못된 사용자이거나 혹은 존재하지 않는 경력입니다!"));
        memberCareerUpdateRequestDto.setCareer(career);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus deleteMemberCareer(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Career career = careerRepository.findByMemberAndId(member, id).orElseThrow(() -> new NullPointerException("잘못된 사용자이거나 혹은 존재하지 않는 경력입니다!"));
        careerRepository.delete(career);
        return HttpStatus.OK;
    }

    @Transactional(readOnly = true)
    public CertificationResponseDto getMemberCertification(Long id) {
        CertificationResponseDto certificationResponseDto = certificationRepository.findById(id).map(CertificationResponseDto::of).orElseThrow(() -> new RuntimeException("해당 자격증이 없습니다!"));
        return certificationResponseDto;
    }

    @Transactional
    public HttpStatus createMemberCertification(MemberCertificationRequestDto memberCertificationRequestDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Certification certification = memberCertificationRequestDto.toCertification(member);
        certificationRepository.save(certification);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus updateMemberCertification(Long id, MemberCertificationUpdateRequestDto memberCertificationUpdateRequestDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Certification certification = certificationRepository.findByMemberAndId(member, id).orElseThrow(() -> new NullPointerException("잘못된 사용자이거나 혹은 존재하지 않는 자격증입니다!"));
        memberCertificationUpdateRequestDto.setCertification(certification);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus deleteMemberCertification(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Certification certification = certificationRepository.findByMemberAndId(member, id).orElseThrow(() -> new NullPointerException("잘못된 사용자이거나 혹은 존재하지 않는 자격증입니다!"));
        certificationRepository.delete(certification);
        return HttpStatus.OK;
    }

    @Transactional(readOnly = true)
    public EducationResponseDto getMemberEducation(Long id) {
        EducationResponseDto educationResponseDto = educationRepository.findById(id).map(EducationResponseDto::of).orElseThrow(() -> new RuntimeException("해당 교육이 없습니다!"));
        return educationResponseDto;
    }

    @Transactional
    public HttpStatus createMemberEducation(MemberEducationRequestDto memberEducationRequestDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Education education = memberEducationRequestDto.toCareer(member);
        educationRepository.save(education);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus deleteMemberEducation(Long id) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Education education = educationRepository.findByMemberAndId(member, id).orElseThrow(() -> new NullPointerException("잘못된 사용자이거나 혹은 존재하지 않는 교육입니다!"));
        educationRepository.delete(education);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus updateMemberEducation(Long id, MemberEducationUpdateRequestDto memberEducationUpdateRequestDto) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        Education education = educationRepository.findByMemberAndId(member, id).orElseThrow(() -> new NullPointerException("잘못된 사용자이거나 혹은 존재하지 않는 교육입니다!"));
        memberEducationUpdateRequestDto.setEducation(education);
        return HttpStatus.OK;
    }

    @Transactional(readOnly = true)
    public MemberSkillResponseDto getMemberSkills() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<DetailPositionInterface> dpositionList = detailPositionRepository.findAllByMemberWithInterface(member);
        List<MemberTechstackInterface> techList = memberTechstackRepository.findTechstackByMember(member);

        MemberSkillResponseDto memberSkillResponseDto = MemberSkillResponseDto.of(member, dpositionList, techList);
        return memberSkillResponseDto;
    }

    @Transactional
    public HttpStatus updateMemberSkills(MemberSkillRequestDto memberSkillRequestDto) throws Exception {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        updateTechList(member, memberSkillRequestDto.getTechList());
        updateDposition(member, memberSkillRequestDto.getDpositionList());
        updatePosition(member, memberSkillRequestDto.getPosition());
        return HttpStatus.OK;
    }

    @Transactional(readOnly = true)
    public MemberSnsPortfolioResponseDto getMemberSnsPortfolio() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<MemberSns> snsList = memberSnsRepository.findAllByMember(member);
        MemberSnsPortfolioResponseDto memberSnsPortfolioResponseDto = MemberSnsPortfolioResponseDto.of(member, snsList);
        return memberSnsPortfolioResponseDto;
    }

//    @Transactional
//    public HttpStatus createMemberPortfolio(MemberPortfolioRequestDto memberPortfolioRequestDto) throws Exception {
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
//        setPortfolioUuid(member, memberPortfolioRequestDto.getPortfolio_uuid());
//        if (!memberPortfolioRequestDto.getPortfolio_uri().isEmpty()) {
//            member.setPortfolio_uri(memberPortfolioRequestDto.getPortfolio_uri());
//        }
//        return HttpStatus.OK;
//    }

    @Transactional
    public HttpStatus updateMemberPortfolio(MemberPortfolioRequestDto memberPortfolioRequestDto) throws Exception {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        setPortfolioUuid(member, memberPortfolioRequestDto.getPortfolio_uuid());
//        updateSns(member, memberPortfolioRequestDto.getSnsHashMap());
        member.setPortfolio_uri(memberPortfolioRequestDto.getPortfolio_uri());
        return HttpStatus.OK;
    }

    @Transactional
    public void deleteMember() {
        Member member = memberRepository.getById(SecurityUtil.getCurrentMemberId());
        deleteMem(member);
    }

    @Transactional
    public void deleteMem(Member member) {
        member.setIs_active(Boolean.FALSE);
    }

    @Transactional
    public void setCoverPic(Member member, String uuid) throws Exception {
        if (uuid == null || uuid.equals("")) {
            if (member.getCover_pic() != null) {
                dbFileRepository.delete(member.getCover_pic());
                member.setCover_pic(null);
            }
            return;
        } else {
            if (member.getCover_pic() == null) {
                DBFile dbFile = dbFileRepository.findById(uuid).orElseThrow(() -> new NullPointerException("존재하지 않는 파일입니다."));
                member.setCover_pic(dbFile);
            } else if (!member.getCover_pic().getId().equals(uuid)) {
                DBFile dbFile = dbFileRepository.findById(uuid).orElseThrow(() -> new NullPointerException("존재하지 않는 파일입니다."));
                dbFileRepository.delete(member.getCover_pic());
                member.setCover_pic(dbFile);
            }
        }
    }

    @Transactional
    public void setPortfolioUuid(Member member, String uuid) throws Exception{
        if (uuid == null || uuid.equals("")) {
            if (member.getPortfolio() != null) {
                dbFileRepository.delete(member.getPortfolio());
                member.setPortfolio(null);
            }
            return;
        } else {
            if (member.getPortfolio() == null) {
                DBFile dbFile = dbFileRepository.findById(uuid).orElseThrow(() -> new NullPointerException("존재하지 않는 파일입니다."));
                member.setPortfolio(dbFile);
            } else if (!member.getPortfolio().getId().equals(uuid)) {
                DBFile dbFile = dbFileRepository.findById(uuid).orElseThrow(() -> new NullPointerException("존재하지 않는 파일입니다."));
                dbFileRepository.delete(member.getPortfolio());
                member.setPortfolio(dbFile);
            }
        }
    }

    @Transactional
    public void updatePosition(Member member, String position) {
        member.setPosition(position);
    }

    @Transactional
    public void updateSns(Member member, HashMap<String, String> snsList) {
        if (!snsList.isEmpty()) {
//        if (snsList != null && !snsList.isEmpty()) {
            snsList.forEach((strKey, strValue) -> {
                Optional<MemberSns> memberSns = memberSnsRepository.findByMemberAndSnsName(member, strKey);
                if (memberSns.isEmpty()) {
                    MemberSns innerMemberSns = MemberSns.builder()
                            .member(member)
                            .snsAccount(strValue)
                            .snsName(strKey)
                            .build();
                    memberSnsRepository.save(innerMemberSns);
                } else {
                    MemberSns innerMemberSns = memberSns.get();
                    innerMemberSns.setSnsAccount(strValue);
                    innerMemberSns.setSnsName(strKey);
                }
            });
        }
    }

    @Transactional
    public void updateDposition(Member member, List<String> dpositionList) {
        List<DetailPosition> detailPositions = detailPositionRepository.findAllByMember(member);
        if (!detailPositions.isEmpty()) {
            detailPositionRepository.deleteAll(detailPositions);
        }
        if (dpositionList != null && !dpositionList.isEmpty()) {
            for (String dposition : dpositionList) {
                DetailPosition innerDposition = DetailPosition
                        .builder()
                        .member(member)
                        .name(dposition)
                        .build();
                detailPositionRepository.save(innerDposition);
            }
        }
    }

    @Transactional
    public void updateTechList(Member member, HashMap<String, String> techList) throws Exception {
        List<MemberTechstack> memberTechstacks = memberTechstackRepository.findAllByCompositeMemberTechstack_Member(member);
        if (!memberTechstacks.isEmpty()) {
            memberTechstackRepository.deleteAll(memberTechstacks);
        }
//        if (techList != null && !techList.isEmpty()) {
        if (!techList.isEmpty()) {
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
    }

    @Transactional(readOnly = true)
    public void validLevel(String level) throws Exception {
        if (!Stream.of(Level.values()).map(Enum::name)
                .collect(Collectors.toList()).contains(level)) {
            throw new Exception("존재하지 않는 level입니다");
        }
    }

    @Transactional(readOnly = true)
    public void validNickname(Member member, String nickname) throws Exception {
        if (member.getNickname().equals(nickname)) {
            return;
        } else if (memberRepository.existsByNickname(nickname)) {
            throw new Exception("닉네임이 존재합니다!");
        }
    }

    @Transactional
    public void changePassword(Member member, ChangePasswordDto changePasswordDto) {
        member.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
    }
}
