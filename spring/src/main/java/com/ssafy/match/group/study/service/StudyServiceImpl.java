package com.ssafy.match.group.study.service;

import com.ssafy.match.common.entity.GroupAuthority;
import com.ssafy.match.common.entity.GroupCity;
import com.ssafy.match.common.entity.PublicScope;
import com.ssafy.match.common.entity.RecruitmentState;
import com.ssafy.match.common.entity.StudyProgressState;
import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.group.club.dto.response.ClubSimpleInfoResponseDto;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.club.repository.ClubRepository;
import com.ssafy.match.group.club.repository.MemberClubRepository;
import com.ssafy.match.group.study.dto.request.StudyApplicationRequestDto;
import com.ssafy.match.group.study.dto.request.StudyCreateRequestDto;
import com.ssafy.match.group.study.dto.request.StudyUpdateRequestDto;
import com.ssafy.match.group.study.dto.response.StudyFormInfoResponseDto;
import com.ssafy.match.group.study.dto.response.StudyFormSimpleInfoResponseDto;
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
import com.ssafy.match.member.dto.MemberSimpleInfoResponseDto;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.member.repository.MemberSnsRepository;
import com.ssafy.match.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.List;
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

    // 스터디를 업데이트, 삭제할 권한이 있는지 체크
    public void checkAuthority(Member member, Study study) {
        MemberStudy ms = memberStudyRepository.findById(
                new CompositeMemberStudy(member, study))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND));
        if (!ms.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND);
        }

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
        // 스터디 주제 제거
        studyTopicRepository.deleteAllByStudy(study);
        // 스터디 게시판, 게시글, 댓글 삭제 로직
        study.deActivation();
        return HttpStatus.OK;
    }

//    public Page<StudyInfoResponseDto> getAllStudy(Pageable pageable) {
//        Page<StudyInfoResponseDto> studyInfoResponseDtos = studyRepository.findByIsActiveAndIsPublicAndStatusIsNot(Boolean.TRUE, Boolean.TRUE, StudyProgressState.PROGRESS, pageable)
//                .map(StudyInfoResponseDto::of);
//        for (StudyInfoResponseDto studyInfoResponseDto: studyInfoResponseDtos.getContent()) {
//            studyInfoResponseDto.setMemberSimpleInfoResponseDtos(makeMemberDtos(memberStudyRepository.findMemberByStudyId(studyInfoResponseDto.getId())));
//            studyInfoResponseDto.setTechList(studySubjectRepository.findStudyTechstackNameByStudyId(studyInfoResponseDto.getId()));
//        }
//        return studyInfoResponseDtos;
//    }

    // 스터디 상세 조회
    public StudyInfoResponseDto getOneStudy(Long studyId) {
        Study study = findStudy(studyId);

        // 스터디의 주인은 비공개된 스터디라도 확인 가능
        if (!SecurityUtil.getCurrentMemberId().equals(study.getMember().getId())
            && !study.getPublicScope().equals(PublicScope.PUBLIC)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_SELECT);
        }
        // 삭제된 스터디일 경우
        if (!study.getIsActive()) {
            throw new CustomException(ErrorCode.DELETED_STUDY);
        }
        return StudyInfoResponseDto.of(study, getStudyTopics(study),
            findMemberInStudy(study).stream().map(MemberSimpleInfoResponseDto::from).collect(
                Collectors.toList()));
    }

    @Transactional
    public void addTopics(Study study, List<String> topics) {
        studyTopicRepository.deleteAllByStudy(study);
        if (topics.isEmpty()) {
            return;
        }
//        for (Map.Entry<String, String> entry : topics.entrySet()) {
//            Level level = Level.from(entry.getValue());
//            studyTopicRepository.save(StudyTopic.of(study, entry.getKey(), level));
//        }
        for (String topic : topics) {
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
                .registerDate(LocalDateTime.now())
                .authority(GroupAuthority.팀원)
                .build());

        memberStudy.activation();
        study.addMember();
        memberStudyRepository.save(memberStudy);
    }

    // 조회수 증가
    public HttpStatus plusViewCount(Long studyId) {
        findStudy(studyId).plusViewCount();
        return HttpStatus.OK;
    }

    // 스터디 탈퇴
    @Transactional
    public HttpStatus removeMe(Long studyId) {
        Study study = findStudy(studyId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        // 이미 탈퇴되었는지 여부
        CompositeMemberStudy compositeMemberStudy = new CompositeMemberStudy(member, study);
        MemberStudy memberStudy = memberStudyRepository.findById(compositeMemberStudy)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND));
        if (!memberStudy.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND);
        }

        if (memberStudy.getAuthority().equals(GroupAuthority.소유자)) {
            throw new CustomException(ErrorCode.HOST_CANNOT_LEAVE);
        }

        memberStudy.deActivation();
        study.removeMember();
        return HttpStatus.OK;
    }

    // 스터디 추방
    @Transactional
    public HttpStatus removeMember(Long studyId, Long memberId) {
        Study study = findStudy(studyId);
        Member remover = findMember(SecurityUtil.getCurrentMemberId());
        Member removed = findMember(memberId);

        MemberStudy removerms = memberStudyRepository.findById(
                new CompositeMemberStudy(remover, study))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND));

        MemberStudy removedms = memberStudyRepository.findById(
                new CompositeMemberStudy(removed, study))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND));
        // 소유자와 관리자만이 추방 권한을 가짐
        if (removerms.getAuthority().equals(GroupAuthority.팀원)) {
            throw new CustomException(ErrorCode.COMMON_MEMBER_CANNOT_REMOVE);
        }
        // 소유자와 관리자는 추방될 수 없음
        if (!removedms.getAuthority().equals(GroupAuthority.팀원)) {
            throw new CustomException(ErrorCode.ONLY_CAN_REMOVE_COMMON);
        }

        removedms.deActivation();
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
    public List<Club> findClubInMember(Member member) {
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

    // 신청 버튼 클릭시 신청 가능한 인원인지 확인
    public boolean checkCanApply(Long studyId) {
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Study study = findStudy(studyId);

        // 신청 가능 확인 로직 (스터디 종료, 삭제, 모집, 이미 가입된 멤버인지 여부 확인)
        if (study.getStudyProgressState().equals(StudyProgressState.FINISH)
            || study.getIsActive().equals(Boolean.FALSE) || study.getRecruitmentState()
            .equals(RecruitmentState.FINISH) || !checkAlreadyJoin(study, member)) {
            return false;
        }

        return true;
    }

    // 스터디 가입 여부 확인 로직
    public boolean checkAlreadyJoin(Study study, Member member) {
        Optional<MemberStudy> ms = memberStudyRepository.findById(
            new CompositeMemberStudy(member, study));
        if (ms.isPresent() && ms.get().getIsActive()) {
            throw new CustomException(ErrorCode.ALREADY_JOIN);
        }
        return true;
    }

    @Transactional
    public StudyFormInfoResponseDto applyStudy(Long studyId, StudyApplicationRequestDto dto) {
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Study study = findStudy(studyId);

        CompositeMemberStudy cms = new CompositeMemberStudy(member, study);

        Optional<StudyApplicationForm> form = studyApplicationFormRepository.findById(cms);
        if (form.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_APPLY);
        }

        StudyApplicationForm studyApplicationForm = StudyApplicationForm.of(dto, cms,
            member.getName());

        return StudyFormInfoResponseDto.from(
            studyApplicationFormRepository.save(studyApplicationForm));
    }

    // 모든 신청서 작성일 기준 내림차순 조회
    public List<StudyFormSimpleInfoResponseDto> getAllStudyForm(Long studyId) {
        Study study = findStudy(studyId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());

        // 조회 권한 확인 로직
        MemberStudy ms = memberStudyRepository.findById(
                new CompositeMemberStudy(member, study))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND));
        if (!ms.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND);
        }

        // 팀원은 신청서를 조회할 권한이 없음
        if (ms.getAuthority().equals(GroupAuthority.팀원)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_SELECT);
        }

        return studyApplicationFormRepository.formByStudyId(study);
    }

    // 신청서 목록의 복합 기본키를 가져와 해당 신청서 상세조회
    public StudyFormInfoResponseDto getOneStudyForm(Long studyId, Long memberId) {
        CompositeMemberStudy cms = new CompositeMemberStudy(findMember(memberId),
            findStudy(studyId));

        StudyApplicationForm form = studyApplicationFormRepository.oneFormById(cms)
            .orElseThrow(() -> new CustomException(ErrorCode.APPLIY_FORM_NOT_FOUND));

        return StudyFormInfoResponseDto.from(form);
    }

    // 가입 승인
    @Transactional
    public HttpStatus approval(Long studyId, Long memberId) {
        Study study = findStudy(studyId);
        Member member = findMember(memberId);

        checkAlreadyJoin(study, member);

        MemberStudy ms = memberStudyRepository.findById(
                new CompositeMemberStudy(member, study))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND));
        if (!ms.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_STUDY_NOT_FOUND);
        }
        // 팀원은 신청서를 조회할 권한이 없음
        if (ms.getAuthority().equals(GroupAuthority.팀원)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_SELECT);
        }

        addMember(study, member);
        reject(studyId, memberId);

        return HttpStatus.OK;
    }

    //     신청서 제거
    @Transactional
    public HttpStatus reject(Long studyId, Long memberId) {
        studyApplicationFormRepository.delete(
            validStudyApplicationForm(findMember(memberId), findStudy(studyId)));

        return HttpStatus.OK;
    }

    public StudyApplicationForm validStudyApplicationForm(Member member, Study study) {
        return studyApplicationFormRepository
            .findById(new CompositeMemberStudy(member, study))
            .orElseThrow(() -> new CustomException(ErrorCode.APPLIY_FORM_NOT_FOUND));
    }
}
