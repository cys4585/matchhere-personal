package com.ssafy.match.group.service;

import com.ssafy.match.db.entity.City;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import com.ssafy.match.group.studyboard.board.repository.StudyBoardRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import com.ssafy.match.db.entity.Status;
import com.ssafy.match.db.entity.Techstack;
import com.ssafy.match.member.repository.MemberBeginnerTechstackRepository;
import com.ssafy.match.group.repository.club.MemberClubRepository;
import com.ssafy.match.member.repository.MemberExperiencedTechstackRepository;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.member.repository.MemberSnsRepository;
import com.ssafy.match.db.repository.TechstackRepository;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.group.dto.MemberDto;
import com.ssafy.match.group.dto.club.ClubDto;
import com.ssafy.match.group.dto.study.request.StudyApplicationRequestDto;
import com.ssafy.match.group.dto.study.request.StudyCreateRequestDto;
import com.ssafy.match.group.dto.study.request.StudyUpdateRequestDto;
import com.ssafy.match.group.dto.study.response.InfoForApplyStudyFormResponseDto;
import com.ssafy.match.group.dto.study.response.StudyFormInfoResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoForCreateResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoForUpdateResponseDto;
import com.ssafy.match.group.dto.study.response.StudyInfoResponseDto;
import com.ssafy.match.group.entity.study.CompositeMemberStudy;
import com.ssafy.match.group.entity.study.CompositeStudyTechstack;
import com.ssafy.match.group.entity.study.MemberStudy;
import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.group.entity.study.StudyApplicationForm;
import com.ssafy.match.group.entity.study.StudyTechstack;
import com.ssafy.match.group.repository.club.ClubRepository;
import com.ssafy.match.group.repository.study.MemberStudyRepository;
import com.ssafy.match.group.repository.study.StudyApplicationFormRepository;
import com.ssafy.match.group.repository.study.StudyRepository;
import com.ssafy.match.group.repository.study.StudyTechstackRepository;
import com.ssafy.match.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
// https://velog.io/@kdhyo/JavaTransactional-Annotation-%EC%95%8C%EA%B3%A0-%EC%93%B0%EC%9E%90-26her30h
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final ClubRepository clubRepository;
    private final MemberStudyRepository memberStudyRepository;
    private final MemberClubRepository memberClubRepository;
    private final StudyApplicationFormRepository studyApplicationFormRepository;
    private final TechstackRepository techstackRepository;
    private final StudyTechstackRepository studyTechstackRepository;
    private final DBFileRepository dbFileRepository;
    private final MemberExperiencedTechstackRepository memberExperiencedTechstackRepository;
    private final MemberBeginnerTechstackRepository memberBeginnerTechstackRepository;
    private final MemberSnsRepository memberSnsRepository;
    private final StudyBoardRepository studyBoardRepository;

    // 스터디 생성을 위한 정보(호스트의 클럽 정보)
    public StudyInfoForCreateResponseDto getInfoForCreate() throws Exception {
        return StudyInfoForCreateResponseDto.builder()
            .hostClub(makeClubDtos(memberClubRepository
                .findClubByMember(findMember(SecurityUtil.getCurrentMemberId()))))
            .build();
    }

    @Transactional
    public Long create(StudyCreateRequestDto dto) throws Exception {
        validCity(dto.getCity());
        validTechstack(dto.getTechList());

        Study study = new Study(dto);
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        study.setMember(member);
        study.setClub(findClub(dto.getClubId()));
        study.setDBFile(findDBFile(dto.getCoverpic_uuid()));

        studyRepository.save(study);
        makeBasicBoards(study);
        addTechstack(study, dto.getTechList());
        addMember(study, member);

        return study.getId();
    }

    @Transactional
    public HttpStatus update(Long studyId, StudyUpdateRequestDto dto) throws Exception {
        validCity(dto.getCity());
        validTechstack(dto.getAddStackList());
        validTechstack(dto.getRemoveStackList());
        validStatus(dto.getStatus());

        Study study = findStudy(studyId);
        Long memberId = SecurityUtil.getCurrentMemberId();
        if (!study.getMember().getId().equals(memberId)) {
            throw new Exception("권한이 없습니다.");
        }

        study.update(dto);
        study.setMember(findMember(dto.getHostId()));
        study.setClub(findClub(dto.getClubId()));
        study.setDBFile(findDBFile(dto.getUuid()));
        addTechstack(study, dto.getAddStackList());
        removeTechstack(study, dto.getRemoveStackList());

        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus delete(Long studyId) throws Exception {
        Study study = findStudy(studyId);

        if (!study.getMember().getId().equals(SecurityUtil.getCurrentMemberId())) {
            throw new Exception("권한이 없습니다.");
        }

        List<MemberStudy> memberStudys = memberStudyRepository.findMemberRelationInStudy(study);
        for (MemberStudy mem : memberStudys) {
            mem.deActivation();
        }

        List<StudyTechstack> sts = studyTechstackRepository.findStudyTechstackByStudy(study);
        for (StudyTechstack st: sts) {
            studyTechstackRepository.delete(st);
        }

        study.setIsActive(false);

        return HttpStatus.OK;
    }

    public Page<StudyInfoResponseDto> getAllStudy(Pageable pageable) {
        Page<StudyInfoResponseDto> studyInfoResponseDtos = studyRepository.findByIsActiveAndIsPublicAndStatusIsNot(Boolean.TRUE, Boolean.TRUE, Status.종료, pageable)
                .map(StudyInfoResponseDto::of);
        for (StudyInfoResponseDto studyInfoResponseDto: studyInfoResponseDtos.getContent()) {
            studyInfoResponseDto.setMemberDtos(makeMemberDtos(memberStudyRepository.findMemberByStudyId(studyInfoResponseDto.getId())));
            studyInfoResponseDto.setTechList(studyTechstackRepository.findStudyTechstackNameByStudyId(studyInfoResponseDto.getId()));
        }
        return studyInfoResponseDtos;
    }

    // 현재 스터디 정보 리턴
    public StudyInfoResponseDto getOneStudy(Long studyId) throws Exception {
        Study study = findStudy(studyId);

        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())
            && !study.getIsPublic()) {
            throw new Exception("비공개된 스터디입니다.");
        }
        StudyInfoResponseDto studyInfoResponseDto = studyRepository.findById(studyId).map(StudyInfoResponseDto::of).orElseThrow(() -> new NullPointerException("스터디가 없습니다."));
        studyInfoResponseDto.setMemberDtos(makeMemberDtos(memberStudyRepository.findMemberByStudyId(studyInfoResponseDto.getId())));
        studyInfoResponseDto.setTechList(studyTechstackRepository.findStudyTechstackNameByStudyId(studyInfoResponseDto.getId()));
        return studyInfoResponseDto;
    }

    // 스터디 업데이트를 위한 정보(기존 정보 + 기술 스택 리스트, 호스트의 클럽 정보, 지역 리스트)
    public StudyInfoForUpdateResponseDto getInfoForUpdateStudy(Long studyId) throws Exception {
        Study study = findStudy(studyId);
        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())) {
            throw new Exception("권한이 없습니다");
        }

        StudyInfoForUpdateResponseDto dto = new StudyInfoForUpdateResponseDto(study);
        dto.setStudyTechstack(studyTechstackName(study));
        dto.setClubList(makeClubDtos(memberClubRepository.findClubByMember(study.getMember())));
        dto.setMemberDtos(makeMemberDtos(findMemberInStudy(study)));
        dto.setHost(new MemberDto(study.getMember()));

        return dto;
    }

    // 모든 기술스택의 이름 리스트
    public List<String> allTechstackName() {
        return techstackRepository.findAllName();
    }

    // 현재 스터디 기술 스택의 이름 리스트
    public List<String> studyTechstackName(Study study) {
        return studyTechstackRepository.findByStudyTechstackName(study);
    }

    public Techstack findTechstack(String techName) {
        return techstackRepository.findByName(techName)
            .orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
    }

    @Transactional
    public void addTechstack(Study study, List<String> techName) {
        for (String name : techName) {
            Techstack techstack = findTechstack(name);
            CompositeStudyTechstack compositeStudyTechstack = new CompositeStudyTechstack(techstack,
                study);

            studyTechstackRepository.save(new StudyTechstack(compositeStudyTechstack));
        }
    }

    @Transactional
    public void removeTechstack(Study study, List<String> techName) {
        for (String name : techName) {
            Techstack techstack = findTechstack(name);
            CompositeStudyTechstack compositeStudyTechstack = new CompositeStudyTechstack(techstack,
                study);

            StudyTechstack studyTechstack = studyTechstackRepository
                .findById(compositeStudyTechstack)
                .orElseThrow(() -> new NullPointerException("제거할 기술 스택이 존재하지 않습니다."));

            studyTechstackRepository.delete(studyTechstack);
        }
    }

    @Transactional
    public void addMember(Study study, Member member) {
        CompositeMemberStudy compositeMemberStudy = new CompositeMemberStudy(member, study);

        // DB에 해당 멤버 기록이 없다면 새로 생성
        MemberStudy memberStudy = memberStudyRepository
            .findById(compositeMemberStudy)
            .orElseGet(() -> MemberStudy.builder()
                .compositeMemberStudy(compositeMemberStudy)
                .registerDate(LocalDateTime.now())
                .build());

        memberStudy.activation();
        study.addMember();

        memberStudyRepository.save(memberStudy);
    }

    @Transactional
    public HttpStatus removeMember(Long studyId) throws Exception {
        Study study = findStudy(studyId);
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("잘못된 사용자 입니다.(사용자 없음)"));
        if (study.getMember().getId().equals(member.getId())) {
            throw new Exception("스터디장은 탈퇴할 수 없습니다.");
        }
        CompositeMemberStudy compositeMemberStudy = new CompositeMemberStudy(member, study);
        MemberStudy memberStudy = memberStudyRepository.findById(compositeMemberStudy)
            .orElseThrow(() -> new NullPointerException("가입 기록이 없습니다."));
        memberStudy.deActivation();
        study.removeMember();
        return HttpStatus.OK;
    }

    @Transactional
    public void makeBasicBoards(Study study){
        studyBoardRepository.save(new StudyBoard("공지사항", study));
        studyBoardRepository.save(new StudyBoard("게시판", study));
    }

    public Study findStudy(Long studyId) throws Exception {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NullPointerException("스터디 정보가 없습니다."));

        if (!study.getIsActive()) {
            throw new Exception("삭제된 스터디입니다.");
        }

        return study;
    }

    public Member findMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NullPointerException("회원 정보가 없습니다."));

        if (!member.getIs_active()) {
            throw new Exception("삭제된 멤버입니다.");
        }

        return member;
    }

    public Club findClub(Long clubId) {
        if (clubId == null) {
            return null;
        }
        return clubRepository.findById(clubId)
            .orElseThrow(() -> new NullPointerException("클럽 정보가 없습니다."));
    }

    public DBFile findDBFile(String uuid) {
        if (uuid == null) {
            return null;
        }
        return dbFileRepository.findById(uuid)
            .orElseThrow(() -> new NullPointerException("파일 정보가 없습니다."));
    }

    // 현재 스터디에 속한 멤버 리스트
    public List<Member> findMemberInStudy(Study study) {
        return memberStudyRepository.findMemberInStudy(study);
    }

    // 특정 멤버의 활성화 스터디 리스트
    public List<Study> studyInMember(Member member) {
        return memberStudyRepository.studyInMember(member);
    }

    public void validCity(String city) throws Exception {
        if(!Stream.of(City.values()).map(Enum::name)
            .collect(Collectors.toList()).contains(city)){
            throw new Exception("존재하지 않는 지역입니다");
        }
    }
    public void validStatus(String status) throws Exception {
        if(!Stream.of(Status.values()).map(Enum::name)
            .collect(Collectors.toList()).contains(status)){
            throw new Exception("존재하지 않는 상태입니다");
        }
    }
    public void validTechstack(List<String> techstacks) throws Exception {
        for (String inTech: techstacks) {
            if(!techstackRepository.findAllName().contains(inTech)){
                throw new Exception("존재하지 않는 기술 스택입니다");
            }
        }
    }

    public List<ClubDto> makeClubDtos(List<Club> hostClub) {
        List<ClubDto> clubDtos = new ArrayList<>();

        for (Club club : hostClub) {
            clubDtos.add(new ClubDto(club));
        }

        return clubDtos;
    }


    public List<MemberDto> makeMemberDtos(List<Member> members) {
        List<MemberDto> memberDtos = new ArrayList<>();

        for (Member member : members) {
            memberDtos.add(new MemberDto(member));
        }

        return memberDtos;
    }

    // 신청 버튼 클릭시 관련 정보 및 권한 체크
    public InfoForApplyStudyFormResponseDto getInfoForApply(Long studyId) throws Exception {
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Study study = findStudy(studyId);

        List<Member> memberList = findMemberInStudy(study);
        for (Member mem : memberList) {
            if (SecurityUtil.getCurrentMemberId().equals(mem.getId())) {
                throw new Exception("이미 가입한 멤버입니다.");
            }
        }

        if (!study.getIsParticipate()) {
            throw new Exception("참여 불가능한 스터디입니다.");
        }

        InfoForApplyStudyFormResponseDto dto = InfoForApplyStudyFormResponseDto.builder()
            .nickname(member.getNickname())
            .strong(memberExperiencedTechstackRepository.findTechstackByMemberName(member))
            .knowledgeable(memberBeginnerTechstackRepository.findTechstackByMemberName(member))
            .build();

        Optional<MemberSns> git = memberSnsRepository.findByMemberAndSnsName(member, "github");
        Optional<MemberSns> twitter = memberSnsRepository.findByMemberAndSnsName(member, "twitter");
        Optional<MemberSns> facebook = memberSnsRepository
            .findByMemberAndSnsName(member, "facebook");
        Optional<MemberSns> backjoon = memberSnsRepository
            .findByMemberAndSnsName(member, "backjoon");

        if (git.isPresent()) {
            dto.setGit(git.get().getSnsAccount());
        }
        if (twitter.isPresent()) {
            dto.setTwitter(twitter.get().getSnsAccount());
        }
        if (facebook.isPresent()) {
            dto.setFacebook(facebook.get().getSnsAccount());
        }
        if (backjoon.isPresent()) {
            dto.setBackjoon(backjoon.get().getSnsAccount());
        }

        return dto;
    }

    @Transactional
    public HttpStatus applyStudy(Long studyId, StudyApplicationRequestDto dto) throws Exception {
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Study study = findStudy(studyId);

        CompositeMemberStudy cmp = new CompositeMemberStudy(member, study);

        Optional<StudyApplicationForm> form = studyApplicationFormRepository.findById(cmp);
        if (form.isPresent()) {
            throw new Exception("신청한 내역이 존재합니다.");
        }

        StudyApplicationForm studyApplicationForm = new StudyApplicationForm(cmp, dto);

        if (dto.getGit() != null) {
            studyApplicationForm.setGit(dto.getGit());
        }
        if (dto.getTwitter() != null) {
            studyApplicationForm.setTwitter(dto.getTwitter());
        }
        if (dto.getFacebook() != null) {
            studyApplicationForm.setFacebook(dto.getFacebook());
        }
        if(dto.getBackjoon() != null){
            studyApplicationForm.setBackjoon(dto.getBackjoon());
        }

        studyApplicationForm.setDbFile(findDBFile(dto.getUuid()));

        studyApplicationFormRepository.save(studyApplicationForm);
        return HttpStatus.OK;
    }

    // 모든 신청서 작성일 기준 내림차순 조회
    public List<StudyFormInfoResponseDto> getAllStudyForm(Long studyId) throws Exception {
        Study study = findStudy(studyId);

        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())) {
            throw new Exception("조회 권한이 없습니다.");
        }

        List<StudyApplicationForm> forms = studyApplicationFormRepository.formByStudyId(study);
        List<StudyFormInfoResponseDto> studyFormInfoResponseDtos = new ArrayList<>();

        for (StudyApplicationForm form : forms) {
            studyFormInfoResponseDtos.add(StudyFormInfoResponseDto.builder()
                .form(form)
                .strong(memberExperiencedTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
                .knowledgeable(memberBeginnerTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
                .build());
        }

        return studyFormInfoResponseDtos;
    }

    // 닉네임으로 신청서 검색
    public List<StudyFormInfoResponseDto> getAllFormByStudyNickname(Long studyId, String nickname)
        throws Exception {
        Study study = findStudy(studyId);

        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())) {
            throw new Exception("조회 권한이 없습니다.");
        }

        List<StudyApplicationForm> forms = studyApplicationFormRepository
            .allFormByStudyNickname(study, nickname);
        List<StudyFormInfoResponseDto> studyFormInfoResponseDtos = new ArrayList<>();

        for (StudyApplicationForm form : forms) {
            studyFormInfoResponseDtos.add(StudyFormInfoResponseDto.builder()
                .form(form)
                .strong(memberExperiencedTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
                .knowledgeable(memberBeginnerTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
                .build());
        }

        return studyFormInfoResponseDtos;
    }

    // 신청서 목록의 복합 기본키를 가져와 해당 신청서 상세조회
    public StudyFormInfoResponseDto getOneStudyForm(Long studyId, Long memberId) throws Exception {
        CompositeMemberStudy cms = new CompositeMemberStudy(findMember(memberId),
            findStudy(studyId));

        StudyApplicationForm form = studyApplicationFormRepository.oneFormById(cms)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 신청서입니다"));

        return StudyFormInfoResponseDto.builder()
            .form(form)
            .strong(memberExperiencedTechstackRepository
                .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
            .knowledgeable(memberBeginnerTechstackRepository
                .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
            .build();
    }

    // 가입 승인
    @Transactional
    public HttpStatus approval(Long studyId, Long memberId) throws Exception {
        Study study = findStudy(studyId);
        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())) {
            throw new Exception("승인 권한이 없습니다");
        }
        List<Member> members = findMemberInStudy(study);
        Member member = findMember(memberId);

        for (Member mem : members) {
            if (mem.equals(member)) {
                throw new Exception("이미 가입되어있는 회원입니다.");
            }
        }

        addMember(study, member);
        reject(studyId, memberId);

        return HttpStatus.OK;
    }

    //     신청서 제거
    @Transactional
    public HttpStatus reject(Long studyId, Long memberId) throws Exception {
        Study study = findStudy(studyId);

        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())
            && !SecurityUtil.getCurrentMemberId().equals(memberId)) {
            throw new Exception("승인 권한이 없습니다");
        }

        studyApplicationFormRepository.delete(studyApplicationFormRepository
            .findById(new CompositeMemberStudy(findMember(memberId), study))
            .orElseThrow(() -> new NullPointerException("존재하지 않는 신청서입니다.")));

        return HttpStatus.OK;
    }
}
