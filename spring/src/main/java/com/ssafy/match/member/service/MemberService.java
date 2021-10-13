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
import com.ssafy.match.member.dto.request.MemberBasicInfoRequestDto;
import com.ssafy.match.member.dto.request.MemberSkillRequestDto;
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
        MypageResponseDto mypageResponseDto = memberRepository.findByEmail(email)
                .map(MypageResponseDto::of)
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

        getCoverPic(mypageResponseDto, member.getCover_pic());
        getPortfolio(mypageResponseDto, member.getPortfolio());

        mypageResponseDto.setMyStudyList(myStudyList);
        mypageResponseDto.setMyProjectList(myProjectList);
        mypageResponseDto.setMyClubList(myClubList);
        mypageResponseDto.setTechList(techList);
        mypageResponseDto.setSnsList(snsList);
        mypageResponseDto.setDpositionList(dpositionList);
        return mypageResponseDto;
    }

    @Transactional(readOnly = true)
    public MypageResponseDto getMyPage() {
        MypageResponseDto mypageResponseDto = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MypageResponseDto::of)
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

        getCoverPic(mypageResponseDto, member.getCover_pic());
        getPortfolio(mypageResponseDto, member.getPortfolio());

        mypageResponseDto.setMyStudyList(myStudyList);
        mypageResponseDto.setMyProjectList(myProjectList);
        mypageResponseDto.setMyClubList(myClubList);
        mypageResponseDto.setTechList(techList);
        mypageResponseDto.setSnsList(snsList);
        mypageResponseDto.setDpositionList(dpositionList);
        return mypageResponseDto;
    }

    @Transactional
    public HttpStatus updateMemberBasicInfo(MemberBasicInfoRequestDto memberBasicinfoRequestDto) throws Exception {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
        validNickname(member, memberBasicinfoRequestDto.getNickname());
        updateBasicinfo(member, memberBasicinfoRequestDto.getNickname(), memberBasicinfoRequestDto.getName(), memberBasicinfoRequestDto.getCity(), memberBasicinfoRequestDto.getBio());
        setCoverPic(member, memberBasicinfoRequestDto.getCoverpic_uuid());
        return HttpStatus.OK;
    }

//    @Transactional
//    public HttpStatus updateMemberSkill(MemberSkillRequestDto memberSkillRequestDto) {
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("토큰이 잘못되었거나 존재하지 않는 사용자입니다."));
//        validPosition();
//        validDPosition();
//        validTechstack();
//
//    }

    @Transactional
    public MemberUpdateResponseDto updateMyInfo(MemberUpdateRequestDto memberUpdateRequestDto) throws Exception {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("존재하지 않는 사용자 입니다."));

        updateMember(member, memberUpdateRequestDto);
        updateSns(member, memberUpdateRequestDto.getSnsHashMap());
        addTechstack(member, memberUpdateRequestDto.getAddTechList());
        delTechstack(member, memberUpdateRequestDto.getDelTechList());
        addDposition(member, memberUpdateRequestDto.getAddDpositionList());
        delDposition(member, memberUpdateRequestDto.getDelDpositionList());
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
    public void setCoverPic(Member member, String uuid) throws Exception {
        if (uuid == null || uuid.equals("")) {
            if (member.getCover_pic() != null) {
                dbFileRepository.delete(member.getCover_pic());
                member.setCover_pic(null);
            }
            return;
        } else {
            if (member.getCover_pic() != null) {
                dbFileRepository.delete(member.getCover_pic());
            }
            DBFile dbFile = dbFileRepository.findById(uuid).orElseThrow(() -> new NullPointerException("존재하지 않는 파일입니다."));
            member.setCover_pic(dbFile);
        }
    }

    @Transactional
    public void setPortfolioUuid(Member member, String uuid) throws Exception{
        if(uuid == null && member.getPortfolio() != null) {
            dbFileRepository.delete(member.getPortfolio());
            member.setPortfolio(null);
            return;
        } else {
            if (member.getPortfolio() != null) {
                dbFileRepository.delete(member.getPortfolio());
            }
            DBFile dbFile = dbFileRepository.findById(uuid).orElseThrow(() -> new NullPointerException("존재하지 않는 파일입니다."));
            member.setPortfolio(dbFile);
        }
    }

    @Transactional
    public void deleteMem(Member member) {
        member.setIs_active(Boolean.FALSE);
    }

    @Transactional
    public void updateMember(Member member, MemberUpdateRequestDto memberUpdateRequestDto) throws Exception {
        member.setNickname(memberUpdateRequestDto.getNickname());
        if (memberUpdateRequestDto.getNickname() == null) {
            throw new Exception("nickname이 비어있습니다!");
        } else {
            if (!memberRepository.existsByNickname(memberUpdateRequestDto.getNickname())) {
                member.setNickname(memberUpdateRequestDto.getNickname());
            }
        }
        if (memberUpdateRequestDto.getName() == null) {
            throw new Exception("name이 비어있습니다!");
        } else {
            member.setName(memberUpdateRequestDto.getName());
        }
        member.setBio(memberUpdateRequestDto.getBio());
        member.setCity(memberUpdateRequestDto.getCity());
        member.setPosition(memberUpdateRequestDto.getPosition());
        member.setPortfolio_uri(memberUpdateRequestDto.getPortfolio_uri());
    }

    @Transactional
    public void updateBasicinfo(Member member, String nickname, String name, String city, String bio) throws Exception {
        if (nickname == null) {
            throw new Exception("nickname이 비어있습니다!");
        } else {
            if (!memberRepository.existsByNickname(nickname)) {
                member.setNickname(nickname);
            }
        }
        if (name == null) {
            throw new Exception("name이 비어있습니다!");
        } else {
            member.setName(name);
        }
        member.setCity(city);
        member.setBio(bio);
    }

    @Transactional
    public void updateSns(Member member, HashMap<String, String> snsList) {
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
    public void addDposition(Member member, List<String> addDpositionList) {
        if (addDpositionList != null) {
            for (String dposition : addDpositionList) {
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
    public void delDposition(Member member, List<String> delDpositionList) throws Exception {
        if (delDpositionList != null) {
            for (String dposition : delDpositionList) {
                Optional<DetailPosition> detailPosition = detailPositionRepository.findByMemberAndName(member, dposition);
                if (detailPosition.isEmpty()) {
                    throw new Exception("잘못된 접근입니다. 세부포지션이 저장되어있지 않습니다!");
                } else {
                    detailPositionRepository.delete(detailPosition.get());
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
    public void validNickname(Member member, String nickname) throws Exception {
        if (member.getNickname().equals(nickname)) {
            return;
        } else if (memberRepository.existsByNickname(nickname)) {
            throw new Exception("닉네임이 존재합니다!");
        }
    }

    @Transactional(readOnly = true)
    public void getCoverPic(MypageResponseDto mypageResponseDto, DBFile cover_pic) {
        if (cover_pic != null) {
            mypageResponseDto.setCover_pic(cover_pic.getDownload_uri());
        }
    }

    @Transactional(readOnly = true)
    public void getPortfolio(MypageResponseDto mypageResponseDto, DBFile portfolio) {
        if (portfolio != null) {
            mypageResponseDto.setPortfolio(portfolio.getDownload_uri());
        }
    }

    @Transactional
    public void changePassword(Member member, ChangePasswordDto changePasswordDto) {
        member.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
    }

}
