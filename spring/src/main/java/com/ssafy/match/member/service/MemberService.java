package com.ssafy.match.member.service;

import com.ssafy.match.group.dto.club.response.ClubInfoResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoResponseDto;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.group.repository.club.MemberClubRepository;
import com.ssafy.match.group.repository.study.MemberStudyRepository;
import com.ssafy.match.member.dto.*;
import com.ssafy.match.db.entity.*;
import com.ssafy.match.db.entity.embedded.CompositeMemberTechstack;
import com.ssafy.match.db.repository.*;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.repository.project.MemberProjectRepository;
import com.ssafy.match.member.entity.*;
import com.ssafy.match.member.repository.MemberBeginnerTechstackRepository;
import com.ssafy.match.member.repository.MemberExperiencedTechstackRepository;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.member.repository.MemberSnsRepository;
import com.ssafy.match.util.SecurityUtil;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final DBFileRepository dbFileRepository;
    private final MemberExperiencedTechstackRepository memberExperiencedTechstackRepository;
    private final MemberBeginnerTechstackRepository memberBeginnerTechstackRepository;
    private final MemberClubRepository memberClubRepository;
    private final MemberProjectRepository memberProjectRepository;
    private final MemberSnsRepository memberSnsRepository;
    private final PositionRepository positionRepository;
    private final TechstackRepository techstackRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberStudyRepository memberStudyRepository;
//    private final MemberService memberService;
//    @Transactional(readOnly = true)
//    public MemberResponseDto getMemberInfo(String email) {
//        return memberRepository.findByEmail(email)
//                .map(MemberResponseDto::of)
//                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
//    }
//
//    // 현재 SecurityContext 에 있는 유저 정보 가져오기
//    @Transactional(readOnly = true)
//    public MemberResponseDto getMyInfo() {
//        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
//                .map(MemberResponseDto::of)
//                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
//    }

    @Transactional(readOnly = true)
    public Boolean checkPassword(MemberCheckPasswordDto memberCheckPasswordDto) {
        UsernamePasswordAuthenticationToken authenticationToken = memberCheckPasswordDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
//        System.out.println(authentication);
//        if (memberRepository.existsMemberByIdAndPassword(SecurityUtil.getCurrentMemberId(), passwordEncoder.encode(password))) {
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
//        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public MemberInfoDto getMyPage(String email) {
//        MemberInfoDto memberInfoDto = memberRepository.findById(SecurityUtil.getCurrentMemberId())
//                .map(MemberInfoDto::of)
//                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
//                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        MemberInfoDto memberInfoDto = memberRepository.findByEmail(email)
                .map(MemberInfoDto::of)
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<Club> myClubList = memberClubRepository.findClubByMember(member);
        List<Project> myProjectList = memberProjectRepository.projectInMember(member);
        List<Study> myStudyList = memberStudyRepository.studyInMember(member);
//        List<ClubInfoResponseDto> myClubList = new ArrayList<>();
//        for (Club club : memberClubRepository.findClubByMember(member)) {
//            myClubList.add(ClubInfoResponseDto.of(club));
//        }
//        List<ProjectInfoResponseDto> myProjectList = new ArrayList<>();
//        for (Project project : memberProjectRepository.projectInMember(member)) {
//            myProjectList.add(ProjectInfoResponseDto.of(project));
//        }
//        List<StudyInfoResponseDto> myStudyList = new ArrayList<>();
//        for (Study study : memberStudyRepository.studyInMember(member)) {
//            myStudyList.add(StudyInfoResponseDto.of(study));
//        }
        List<String> expTechList = memberExperiencedTechstackRepository.findTechstackByMemberName(member);
        List<String> begTechList = memberBeginnerTechstackRepository.findTechstackByMemberName(member);
        List<MemberSns> snsList = memberSnsRepository.findAllByMember(member);
        List<Position> dpositionList = positionRepository.findAllByMember(member);
        DBFile cover_pic = member.getCover_pic();
        DBFile portpolio = member.getPortfolio();
        memberInfoDto.setCover_pic((cover_pic == null) ? null : cover_pic.getDownload_uri());
        memberInfoDto.setPortfolio((portpolio == null) ? null : portpolio.getDownload_uri());
        memberInfoDto.setMyStudyList(myStudyList);
        memberInfoDto.setMyProjectList(myProjectList);
        memberInfoDto.setMyClubList(myClubList);
        memberInfoDto.setExpTechList(expTechList);
        memberInfoDto.setBeginTechList(begTechList);
        memberInfoDto.setSnsList(snsList);
        memberInfoDto.setDpositionList(dpositionList);
        return memberInfoDto;
    }

    @Transactional(readOnly = true)
    public MemberInfoDto getMyPage() {
        MemberInfoDto memberInfoDto = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberInfoDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<Club> myClubList = memberClubRepository.findClubByMember(member);
        List<Project> myProjectList = memberProjectRepository.projectInMember(member);
        List<Study> myStudyList = memberStudyRepository.studyInMember(member);
//        List<ClubInfoResponseDto> myClubList = new ArrayList<>();
//        for (Club club : memberClubRepository.findClubByMember(member)) {
//            myClubList.add(ClubInfoResponseDto.of(club));
//        }
//        List<ProjectInfoResponseDto> myProjectList = new ArrayList<>();
//        for (Project project : memberProjectRepository.projectInMember(member)) {
//            myProjectList.add(ProjectInfoResponseDto.of(project));
//        }
//        List<StudyInfoResponseDto> myStudyList = new ArrayList<>();
//        for (Study study : memberStudyRepository.studyInMember(member)) {
//            myStudyList.add(StudyInfoResponseDto.of(study));
//        }
        List<String> expTechList = memberExperiencedTechstackRepository.findTechstackByMemberName(member);
        List<String> begTechList = memberBeginnerTechstackRepository.findTechstackByMemberName(member);
        List<MemberSns> snsList = memberSnsRepository.findAllByMember(member);
        List<Position> dpositionList = positionRepository.findAllByMember(member);
//        memberInfoDto.setCover_pic(member.getCover_pic());
//        memberInfoDto.setPortfolio(member.getPortfolio());
        if (member.getCover_pic() != null) {
            DBFile cover_pic = member.getCover_pic();
            memberInfoDto.setCover_pic(cover_pic.getDownload_uri());
        }
        if (member.getPortfolio() != null) {
            DBFile portpolio = member.getPortfolio();
            memberInfoDto.setPortfolio(portpolio.getDownload_uri());
        }
        memberInfoDto.setMyStudyList(myStudyList);
        memberInfoDto.setMyProjectList(myProjectList);
        memberInfoDto.setMyClubList(myClubList);
        memberInfoDto.setExpTechList(expTechList);
        memberInfoDto.setBeginTechList(begTechList);
        memberInfoDto.setSnsList(snsList);
        memberInfoDto.setDpositionList(dpositionList);
        return memberInfoDto;
    }

    //    @Transactional(readOnly = true)
//    public MemberInfoDto getMyPage() {
//        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
//                .map(MemberInfoDto::new)
//                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
//    }
    @Transactional
    public MemberUpdateResponseDto updateMyInfo(MemberUpdateRequestDto memberUpdateRequestDto) {
        Member member = memberRepository.getById(SecurityUtil.getCurrentMemberId());

        updateMember(memberUpdateRequestDto.getEmail(), memberUpdateRequestDto.getName(), memberUpdateRequestDto.getPassword(), memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getTel(), memberUpdateRequestDto.getBio(), memberUpdateRequestDto.getCity(), memberUpdateRequestDto.getPosition(), memberUpdateRequestDto.getPortfolio_uri());
        updateSns(memberUpdateRequestDto.getSnsHashMap());
        addExpTechstack(member, memberUpdateRequestDto.getExpAddTechList());
        delExpTechstack(member, memberUpdateRequestDto.getExpDelTechList());
        addBegTechstack(member, memberUpdateRequestDto.getBeginAddTechList());
        delBegTechstack(member, memberUpdateRequestDto.getBeginDelTechList());
        addDposition(member, memberUpdateRequestDto.getDpositionAddList());
        delDposition(memberUpdateRequestDto.getDpositionDelList());
        setCoverPic(member, memberUpdateRequestDto.getCover_pic());
        setPortfolioUuid(member, memberUpdateRequestDto.getPortfolio_uuid());

        return MemberUpdateResponseDto.of(SecurityUtil.getCurrentMemberId());
    }

    @Transactional
    public void deleteMember() {
        Member member = memberRepository.getById(SecurityUtil.getCurrentMemberId());
        deleteMem(member);
    }

    @Transactional
    public void setCoverPic(Member member, String uuid){
        if(uuid == null) {
            member.setCover_pic(null);
            return;
        }
        DBFile dbFile = dbFileRepository.getById(uuid);
        member.setCover_pic(dbFile);
    }

    @Transactional
    public void setPortfolioUuid(Member member, String uuid){
        if(uuid == null) {
            member.setPortfolio(null);
            return;
        }
        DBFile dbFile = dbFileRepository.getById(uuid);
        member.setPortfolio(dbFile);
    }

    @Transactional
    public void deleteMem(Member member) {
        member.setIs_active(Boolean.FALSE);
    }

    @Transactional
    public void updateMember(String email, String name, String password, String nickname, String tel, String bio, String city, String position, String portfolio_uri) {
        Member member = memberRepository.getById(SecurityUtil.getCurrentMemberId());
        if (memberRepository.existsByNickname(nickname)) {
        } else {
            member.setNickname(nickname);
        }
        if (password == null || password == "") {
        } else {
            member.setPassword(passwordEncoder.encode(password));
        }
        member.setTel(tel);
        if (name == null || name == "") {
        } else {
            member.setName(name);
        }
        if (email == null || email == "") {
        } else {
            member.setEmail(email);
        }
        member.setBio(bio);
        member.setCity(city);
        member.setPosition(position);
        member.setPortfolio_uri(portfolio_uri);
    }

    @Transactional
    public void updateSns(HashMap<String, String> snsList) {
        Member member = memberRepository.getById(SecurityUtil.getCurrentMemberId());
        if (snsList != null && !snsList.isEmpty()) {
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
    public void addDposition(Member member, List<String> dpositionAddList) {
        if (dpositionAddList != null) {
            for (String dposition : dpositionAddList) {
                if (!positionRepository.existsByMemberAndName(member, dposition)) {
                    Position innerDposition = Position
                            .builder()
                            .member(member)
                            .name(dposition)
                            .build();
                    positionRepository.save(innerDposition);
                }
            }
        }
    }

    @Transactional
    public void delDposition(List<Integer> dpositionDelList) {
        if (dpositionDelList != null) {
            for (Integer dposition : dpositionDelList) {
                if (positionRepository.existsById(dposition)) {
                    positionRepository.delete(positionRepository.getById(dposition));
                }
            }
        }
    }

    @Transactional
    public void addExpTechstack(Member member, List<String> expAddTechList) {
        if (expAddTechList != null) {
            for (String techstack : expAddTechList) {
                Techstack techstackExp = techstackRepository.findByName(techstack).orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                CompositeMemberTechstack compositeMemberTechstackExp = CompositeMemberTechstack
                        .builder()
                        .member(member)
                        .techstack(techstackExp)
                        .build();
                MemberExperiencedTechstack memberExperiencedTechstack = MemberExperiencedTechstack.builder().compositeMemberTechstack(compositeMemberTechstackExp).build();
                memberExperiencedTechstackRepository.save(memberExperiencedTechstack);
            }
        }
    }

    @Transactional
    public void delExpTechstack(Member member, List<String> expDelTechList) {
        if (expDelTechList != null) {
            for (String techstack : expDelTechList) {
                Techstack techstackExp = techstackRepository.findByName(techstack).orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                Optional<MemberExperiencedTechstack> met = memberExperiencedTechstackRepository.findByCompositeMemberTechstack_MemberAndCompositeMemberTechstack_Techstack(member, techstackExp);
                if (met.isPresent()){
                    memberExperiencedTechstackRepository.delete(met.get());
                }
            }
        }
    }

    @Transactional
    public void addBegTechstack(Member member, List<String> begAddTechList) {
        if (begAddTechList != null) {
            for (String techstack : begAddTechList) {
                Techstack techstackBeg = techstackRepository.findByName(techstack).orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                CompositeMemberTechstack compositeMemberTechstackBeg = CompositeMemberTechstack
                        .builder()
                        .member(member)
                        .techstack(techstackBeg)
                        .build();
                MemberBeginnerTechstack memberBeginnerTechstack = MemberBeginnerTechstack.builder().compositeMemberTechstack(compositeMemberTechstackBeg).build();
                memberBeginnerTechstackRepository.save(memberBeginnerTechstack);
            }
        }
    }

    @Transactional
    public void delBegTechstack(Member member, List<String> begDelTechList) {
        if (begDelTechList != null) {
            for (String techstack : begDelTechList) {
                Techstack techstackBeg = techstackRepository.findByName(techstack).orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                Optional<MemberBeginnerTechstack> met = memberBeginnerTechstackRepository.findByCompositeMemberTechstack_MemberAndCompositeMemberTechstack_Techstack(member, techstackBeg);
                if (met.isPresent()){
                    memberBeginnerTechstackRepository.delete(met.get());
                }
            }
        }
    }
}
