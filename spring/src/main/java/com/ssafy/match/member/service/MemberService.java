package com.ssafy.match.member.service;

import com.ssafy.match.group.club.dto.response.ClubInfoResponseDto;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.project.dto.response.ProjectInfoResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoResponseDto;
import com.ssafy.match.group.study.entity.Study;
import com.ssafy.match.group.club.repository.MemberClubRepository;
import com.ssafy.match.group.study.repository.MemberStudyRepository;
import com.ssafy.match.member.dto.*;
import com.ssafy.match.common.entity.*;
import com.ssafy.match.member.entity.composite.CompositeMemberTechstack;
import com.ssafy.match.common.repository.*;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.group.project.entity.Project;
import com.ssafy.match.group.project.repository.MemberProjectRepository;
import com.ssafy.match.member.entity.*;
import com.ssafy.match.member.repository.DetailPositionRepository;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.member.repository.MemberSnsRepository;
import com.ssafy.match.member.repository.MemberTechstackRepository;
import com.ssafy.match.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public Boolean checkPassword(MemberCheckPasswordDto memberCheckPasswordDto) {
        UsernamePasswordAuthenticationToken authenticationToken = memberCheckPasswordDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    public MemberInfoDto getMyPage(String email) {
        MemberInfoDto memberInfoDto = memberRepository.findByEmail(email)
                .map(MemberInfoDto::of)
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("유저가 없습니다."));
        List<ClubInfoResponseDto> myClubList = new ArrayList<>();
        for (Club club : memberClubRepository.findClubByMember(member)) {
            myClubList.add(ClubInfoResponseDto.of(club));
        }
        List<ProjectInfoResponseDto> myProjectList = new ArrayList<>();
        for (Project project : memberProjectRepository.projectInMember(member)) {
            myProjectList.add(ProjectInfoResponseDto.of(project));
        }
        List<StudyInfoResponseDto> myStudyList = new ArrayList<>();
        for (Study study : memberStudyRepository.studyInMember(member)) {
            myStudyList.add(StudyInfoResponseDto.of(study));
        }
        List<MemberTechstackInterface> techList = memberTechstackRepository.findTechstackByMemberName(member);
        List<MemberSns> snsList = memberSnsRepository.findAllByMember(member);
        List<DetailPosition> dpositionList = detailPositionRepository.findAllByMember(member);

        getCoverPic(memberInfoDto, member.getCover_pic());
        getPortfolio(memberInfoDto, member.getPortfolio());

        memberInfoDto.setMyStudyList(myStudyList);
        memberInfoDto.setMyProjectList(myProjectList);
        memberInfoDto.setMyClubList(myClubList);
        memberInfoDto.setTechList(techList);
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
        List<ClubInfoResponseDto> myClubList = new ArrayList<>();
        for (Club club : memberClubRepository.findClubByMember(member)) {
            myClubList.add(ClubInfoResponseDto.of(club));
        }
        List<ProjectInfoResponseDto> myProjectList = new ArrayList<>();
        for (Project project : memberProjectRepository.projectInMember(member)) {
            myProjectList.add(ProjectInfoResponseDto.of(project));
        }
        List<StudyInfoResponseDto> myStudyList = new ArrayList<>();
        for (Study study : memberStudyRepository.studyInMember(member)) {
            myStudyList.add(StudyInfoResponseDto.of(study));
        }
        List<MemberTechstackInterface> techList = memberTechstackRepository.findTechstackByMemberName(member);
        List<MemberSns> snsList = memberSnsRepository.findAllByMember(member);
        List<DetailPosition> dpositionList = detailPositionRepository.findAllByMember(member);

        getCoverPic(memberInfoDto, member.getCover_pic());
        getPortfolio(memberInfoDto, member.getPortfolio());

        memberInfoDto.setMyStudyList(myStudyList);
        memberInfoDto.setMyProjectList(myProjectList);
        memberInfoDto.setMyClubList(myClubList);
        memberInfoDto.setTechList(techList);
        memberInfoDto.setSnsList(snsList);
        memberInfoDto.setDpositionList(dpositionList);
        return memberInfoDto;
    }

    @Transactional
    public MemberUpdateResponseDto updateMyInfo(MemberUpdateRequestDto memberUpdateRequestDto) throws Exception {
        Member member = memberRepository.getById(SecurityUtil.getCurrentMemberId());

        updateMember(memberUpdateRequestDto.getName(), memberUpdateRequestDto.getNickname(), memberUpdateRequestDto.getBio(), memberUpdateRequestDto.getCity(), memberUpdateRequestDto.getPosition(), memberUpdateRequestDto.getPortfolio_uri());
        updateSns(memberUpdateRequestDto.getSnsHashMap());
        addTechstack(member, memberUpdateRequestDto.getAddTechList());
        delTechstack(member, memberUpdateRequestDto.getDelTechList());
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
    public void updateMember(String name, String nickname, String bio, String city, String position, String portfolio_uri) {
        Member member = memberRepository.getById(SecurityUtil.getCurrentMemberId());
        if (memberRepository.existsByNickname(nickname)) {
        } else {
            member.setNickname(nickname);
        }
        if (name == null || name == "") {
        } else {
            member.setName(name);
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
                if (!detailPositionRepository.existsByMemberAndName(member, dposition)) {
                    DetailPosition innerDposition = DetailPosition
                            .builder()
                            .member(member)
                            .name(dposition)
                            .build();
                    detailPositionRepository.save(innerDposition);
                }
            }
        }
    }

    @Transactional
    public void delDposition(List<Integer> dpositionDelList) {
        if (dpositionDelList != null) {
            for (Integer dposition : dpositionDelList) {
                if (detailPositionRepository.existsById(dposition)) {
                    detailPositionRepository.delete(detailPositionRepository.getById(dposition));
                }
            }
        }
    }

    @Transactional
    public void addTechstack(Member member, List<HashMap<String, String>> techList) throws Exception{
        if (techList != null) {
            for (HashMap<String,String> hashmap : techList) {
                for (Map.Entry<String, String> entry : hashmap.entrySet()) {
                    Techstack techstack = techstackRepository.findByName(entry.getKey())
                            .orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                    validLevel(entry.getValue());
                    CompositeMemberTechstack compositeMemberTechstack = CompositeMemberTechstack
                            .builder()
                            .member(member)
                            .techstack(techstack)
                            .build();
                    MemberTechstack memberTechstack = MemberTechstack.builder().compositeMemberTechstack(compositeMemberTechstack).level(Level.from(entry.getValue())).build();
                    memberTechstackRepository.save(memberTechstack);
                }
            }
        }
    }

    @Transactional
    public void delTechstack(Member member, List<HashMap<String, String>> techList) {
        if (techList != null) {
            for (HashMap<String,String> hashmap : techList) {
                for (Map.Entry<String, String> entry : hashmap.entrySet()) {
                    Techstack techstack = techstackRepository.findByName(entry.getKey())
                            .orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
                    Optional<MemberTechstack> memberTechstack = memberTechstackRepository.findByCompositeMemberTechstack_MemberAndCompositeMemberTechstack_Techstack(member, techstack);
                    if (memberTechstack.isPresent()) {
                        memberTechstackRepository.delete(memberTechstack.get());
                    }
                }
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
    public void getCoverPic(MemberInfoDto memberInfoDto, DBFile cover_pic) {
        if (cover_pic != null) {
            memberInfoDto.setCover_pic(cover_pic.getDownload_uri());
        }
    }

    @Transactional(readOnly = true)
    public void getPortfolio(MemberInfoDto memberInfoDto, DBFile portfolio) {
        if (portfolio != null) {
            memberInfoDto.setPortfolio(portfolio.getDownload_uri());
        }
    }
}
