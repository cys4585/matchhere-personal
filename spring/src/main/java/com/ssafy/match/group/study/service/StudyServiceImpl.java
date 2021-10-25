package com.ssafy.match.group.study.service;

import com.ssafy.match.common.entity.GroupAuthority;
import com.ssafy.match.common.entity.GroupCity;
import com.ssafy.match.common.entity.Level;
import com.ssafy.match.common.entity.PublicScope;
import com.ssafy.match.common.entity.Techstack;
import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.common.repository.TechstackRepository;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.group.club.dto.response.ClubSimpleInfoResponseDto;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.club.repository.ClubRepository;
import com.ssafy.match.group.club.repository.MemberClubRepository;
import com.ssafy.match.group.project.entity.CompositeMemberProject;
import com.ssafy.match.group.project.entity.MemberProject;
import com.ssafy.match.group.study.dto.request.StudyApplicationRequestDto;
import com.ssafy.match.group.study.dto.request.StudyCreateRequestDto;
import com.ssafy.match.group.study.dto.request.StudyUpdateRequestDto;
import com.ssafy.match.group.study.dto.response.InfoForApplyStudyFormResponseDto;
import com.ssafy.match.group.study.dto.response.StudyFormInfoResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoForCreateResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoForUpdateResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoResponseDto;
import com.ssafy.match.group.study.dto.response.StudyTopicResponseDto;
import com.ssafy.match.group.study.entity.CompositeMemberStudy;
import com.ssafy.match.group.study.entity.MemberStudy;
import com.ssafy.match.group.study.entity.Study;
import com.ssafy.match.group.study.entity.StudyApplicationForm;
import com.ssafy.match.group.study.entity.StudyTopic;
import com.ssafy.match.group.study.repository.MemberStudyRepository;
import com.ssafy.match.group.study.repository.StudyApplicationFormRepository;
import com.ssafy.match.group.study.repository.StudyRepository;
import com.ssafy.match.group.study.repository.StudyTopicRepository;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import com.ssafy.match.group.studyboard.board.repository.StudyBoardRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.member.repository.MemberSnsRepository;
import com.ssafy.match.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final ClubRepository clubRepository;
    private final MemberStudyRepository memberStudyRepository;
    private final MemberClubRepository memberClubRepository;
    private final StudyApplicationFormRepository studyApplicationFormRepository;
    private final DBFileRepository dbFileRepository;
    private final MemberSnsRepository memberSnsRepository;
    private final StudyBoardRepository studyBoardRepository;
    private final StudyTopicRepository studyTopicRepository;

    // 스터디 생성을 위한 정보(호스트의 클럽 정보)
    public StudyInfoForCreateResponseDto getInfoForCreate() {
        return StudyInfoForCreateResponseDto.from(makeClubSimpleInfoResponseDtos(
            findClubInMember(findMember(SecurityUtil.getCurrentMemberId()))));
    }

    // 스터디 생성
    @Transactional
    public StudyInfoResponseDto create(StudyCreateRequestDto dto) {
        validCity(dto.getCity());
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Study study = Study.of(dto, findClub(dto.getClubId()), findDBFile(dto.getUuid()), member);
        studyRepository.save(study);

        makeBasicBoards(study);
        addTopics(study, dto.getTopics());

        CompositeMemberStudy compositeMemberStudy = new CompositeMemberStudy(member, study);
        MemberStudy memberStudy = memberStudyRepository
            .findById(compositeMemberStudy)
            .orElseGet(() -> MemberStudy.builder()
                .compositeMemberStudy(compositeMemberStudy)
                .isActive(true)
                .registerDate(LocalDateTime.now())
                .authority(GroupAuthority.소유자)
                .build());

        study.addMember();
        memberStudyRepository.save(memberStudy);

        return getOneStudy(study.getId());
    }

    // 스터디 업데이트를 위한 정보
    public StudyInfoForUpdateResponseDto getInfoForUpdateStudy(Long studyId) {
        Study study = findStudy(studyId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

        return StudyInfoForUpdateResponseDto.of(study, getStudyTopics(study),
            makeClubSimpleInfoResponseDtos(findClubInMember(member)));
    }

    // 스터디의 주제 리스트
    private List<StudyTopicResponseDto> getStudyTopics(Study study) {
        return studyTopicRepository.findAllByStudy(study)
            .stream().map(StudyTopicResponseDto::from).collect(Collectors.toList());
    }

    // 스터디 업데이트
    @Transactional
    public StudyInfoResponseDto update(Long studyId, StudyUpdateRequestDto dto) {
        validCity(dto.getCity());
        Study study = findStudy(studyId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());

        // 권한 체크
        checkAuthority(member, study);

        study.update(dto, findClub(dto.getClubId()), findDBFile(dto.getUuid()));
        addTopics(study, dto.getTopics());

        return getOneStudy(studyId);
    }

    // 권한 체크
    public void checkAuthority(Member member, Study study){
        MemberStudy ms = memberStudyRepository.findById(
                new CompositeMemberStudy(member, study))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND));

        if (!ms.getAuthority().equals(GroupAuthority.소유자)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }
    }

    @Transactional
    public HttpStatus delete(Long studyId) {
        Study study = findStudy(studyId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        checkAuthority(member, study);
        // 스터디 멤버 비활성화
        List<MemberStudy> memberStudies = memberStudyRepository.findMemberRelationInStudy(study);
        for (MemberStudy mem : memberStudies) {
            mem.deActivation();
        }
        // 스터디 Cover 제거
        if (study.getCoverPic().getId() != null) {
            dbFileRepository.delete(study.getCoverPic());
        }
        studyTopicRepository.deleteAllByStudy(study);

        study.deActivation();
        return HttpStatus.OK;
    }

//    public Page<StudyInfoResponseDto> getAllStudy(Pageable pageable) {
//        Page<StudyInfoResponseDto> studyInfoResponseDtos = studyRepository.findByIsActiveAndIsPublicAndStatusIsNot(Boolean.TRUE, Boolean.TRUE, ProjectProgressState.PROGRESS, pageable)
//                .map(StudyInfoResponseDto::of);
//        for (StudyInfoResponseDto studyInfoResponseDto: studyInfoResponseDtos.getContent()) {
//            studyInfoResponseDto.setMemberSimpleInfoResponseDtos(makeMemberDtos(memberStudyRepository.findMemberByStudyId(studyInfoResponseDto.getId())));
//            studyInfoResponseDto.setTechList(studySubjectRepository.findStudyTechstackNameByStudyId(studyInfoResponseDto.getId()));
//        }
//        return studyInfoResponseDtos;
//    }

    // 현재 스터디 정보 조회
    public StudyInfoResponseDto getOneStudy(Long studyId){
        Study study = findStudy(studyId);

        // 스터디의 주인은 비공개된 스터디라도 확인 가능
        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())
            && !study.getPublicScope().equals(PublicScope.PUBLIC)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_SELECT);
        }
        StudyInfoResponseDto studyInfoResponseDto = studyRepository.findById(studyId)
            .map(StudyInfoResponseDto::of)
            .orElseThrow(() -> new NullPointerException("스터디가 없습니다."));
        studyInfoResponseDto.setMemberSimpleInfoResponseDtos(makeMemberDtos(
            memberStudyRepository.findMemberByStudyId(studyInfoResponseDto.getId())));
//        studyInfoResponseDto.setTechList(studySubjectRepository.findStudyTechstackNameByStudyId(studyInfoResponseDto.getId()));
        return studyInfoResponseDto;
    }

    @Transactional
    public void addTopics(Study study, List<String> topics) {
        studyTopicRepository.deleteAllByStudy(study);
        if(topics.isEmpty()){
            return;
        }
//        for (Map.Entry<String, String> entry : topics.entrySet()) {
//            Level level = Level.from(entry.getValue());
//            studyTopicRepository.save(StudyTopic.of(study, entry.getKey(), level));
//        }
        for (String topic: topics) {
            studyTopicRepository.save(StudyTopic.of(study, topic));
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
                .isActive(true)
                .registerDate(LocalDateTime.now())
                .authority(GroupAuthority.팀원)
                .build());

        study.addMember();
        memberStudyRepository.save(memberStudy);
    }

    @Transactional
    public HttpStatus removeMember(Long studyId) {
        Study study = findStudy(studyId);
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new NullPointerException("잘못된 사용자 입니다.(사용자 없음)"));
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
    public void makeBasicBoards(Study study) {
        studyBoardRepository.save(new StudyBoard("공지사항", study));
        studyBoardRepository.save(new StudyBoard("게시판", study));
    }

    public Study findStudy(Long studyId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        if (Boolean.FALSE.equals(study.getIsActive())) {
            throw new CustomException(ErrorCode.DELETED_STUDY);
        }

        return study;
    }

    public Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (Boolean.FALSE.equals(member.getIs_active())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return member;
    }

    public Club findClub(Long clubId) {
        if (clubId == null) {
            return null;
        }
        return clubRepository.findById(clubId)
            .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
    }

    public DBFile findDBFile(String uuid) {
        if (uuid == null) {
            return null;
        }
        return dbFileRepository.findById(uuid)
            .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));
    }

    // 멤버가 가진 클럽 리스트
    public List<Club> findClubInMember(Member member){
        return memberClubRepository.findClubByMember(member);
    }

    // 현재 스터디에 속한 멤버 리스트
    public List<Member> findMemberInStudy(Study study) {
        return memberStudyRepository.findMemberInStudy(study);
    }

    // 특정 멤버의 활성화 스터디 리스트
    public List<Study> studyInMember(Member member) {
        return memberStudyRepository.studyInMember(member);
    }

    public void validCity(String city) {
        if (!Stream.of(GroupCity.values()).map(Enum::name)
            .collect(Collectors.toList()).contains(city)) {
            throw new CustomException(ErrorCode.CITY_NOT_FOUND);
        }
    }

    // 클럽 정보 요약
    public List<ClubSimpleInfoResponseDto> makeClubSimpleInfoResponseDtos(List<Club> clubs) {
        return clubs.stream()
            .map(ClubSimpleInfoResponseDto::from)
            .collect(Collectors.toList());
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
//            .strong(memberExperiencedTechstackRepository.findTechstackByMemberName(member))
//            .knowledgeable(memberBeginnerTechstackRepository.findTechstackByMemberName(member))
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
        if (dto.getBackjoon() != null) {
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
//                .strong(memberExperiencedTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
//                .knowledgeable(memberBeginnerTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
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
//                .strong(memberExperiencedTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
//                .knowledgeable(memberBeginnerTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
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
//            .strong(memberExperiencedTechstackRepository
//                .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
//            .knowledgeable(memberBeginnerTechstackRepository
//                .findTechstackByMemberName(form.getCompositeMemberStudy().getMember()))
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
