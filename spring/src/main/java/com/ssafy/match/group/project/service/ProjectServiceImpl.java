package com.ssafy.match.group.project.service;

import com.ssafy.match.common.entity.City;
import com.ssafy.match.common.entity.GroupAuthority;
import com.ssafy.match.common.entity.Level;
import com.ssafy.match.common.entity.PublicScope;
import com.ssafy.match.common.entity.RecruitmentState;
import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.project.dto.response.ProjectSimpleInfoResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectTechstackResponseDto;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.group.projectboard.board.repository.ProjectBoardRepository;
import com.ssafy.match.group.study.entity.Study;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.entity.MemberSns;
import com.ssafy.match.common.entity.ProjectProgressState;
import com.ssafy.match.common.entity.Techstack;
import com.ssafy.match.group.club.repository.MemberClubRepository;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.member.repository.MemberSnsRepository;
import com.ssafy.match.common.repository.TechstackRepository;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.repository.DBFileRepository;
import com.ssafy.match.member.dto.MemberSimpleInfoResponseDto;
import com.ssafy.match.group.club.dto.response.ClubSimpleInfoResponseDto;
import com.ssafy.match.group.project.dto.request.ProjectApplicationRequestDto;
import com.ssafy.match.group.project.dto.request.ProjectCreateRequestDto;
import com.ssafy.match.group.project.dto.request.ProjectUpdateRequestDto;
import com.ssafy.match.group.project.dto.response.InfoForApplyProjectFormResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectFormInfoResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectInfoForCreateResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectInfoForUpdateResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectInfoResponseDto;
import com.ssafy.match.group.project.entity.CompositeMemberProject;
import com.ssafy.match.group.project.entity.CompositeProjectTechstack;
import com.ssafy.match.group.project.entity.MemberProject;
import com.ssafy.match.group.project.entity.Project;
import com.ssafy.match.group.project.entity.ProjectApplicationForm;
import com.ssafy.match.group.project.entity.ProjectTechstack;
import com.ssafy.match.group.club.repository.ClubRepository;
import com.ssafy.match.group.project.repository.MemberProjectRepository;
import com.ssafy.match.group.project.repository.ProjectApplicationFormRepository;
import com.ssafy.match.group.project.repository.ProjectRepository;
import com.ssafy.match.group.project.repository.ProjectTechstackRepository;
import com.ssafy.match.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ClubRepository clubRepository;
    private final MemberClubRepository memberClubRepository;
    private final MemberProjectRepository memberProjectRepository;
    private final ProjectApplicationFormRepository projectApplicationFormRepository;
    private final TechstackRepository techstackRepository;
    private final ProjectTechstackRepository projectTechstackRepository;
    private final DBFileRepository dbFileRepository;
    private final MemberSnsRepository memberSnsRepository;
    private final ProjectBoardRepository projectBoardRepository;

    // 프로젝트 생성을 위한 정보(회원의 클럽 리스트)
    public ProjectInfoForCreateResponseDto getInfoForCreate() {
        return ProjectInfoForCreateResponseDto.from(makeClubSimpleInfoResponseDtos(
            memberClubRepository.findClubByMember(findMember(SecurityUtil.getCurrentMemberId()))));
    }

    // 프로젝트 생성
    @Transactional
    public Long create(ProjectCreateRequestDto dto) {
        validCity(dto.getCity());
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Project project = new Project(dto, findClub(dto.getClubId()), findDBFile(dto.getUuid()),
            member);
        projectRepository.save(project);

        makeBasicBoards(project); // 기본 공지사항, 게시판 생성
        addTechstack(project, dto.getTechstacks());

        // 프로젝트에 소유자 정보 등록
        CompositeMemberProject compositeMemberProject = new CompositeMemberProject(member, project);
        MemberProject memberProject = MemberProject.builder()
            .compositeMemberProject(compositeMemberProject)
            .role(dto.getHostPosition())
            .registerDate(LocalDateTime.now())
            .authority(GroupAuthority.소유자)
            .isActive(true)
            .build();
        project.addRole(dto.getHostPosition());
        memberProjectRepository.save(memberProject);

        return project.getId();
    }

    // 프로젝트 업데이트를 위한 정보
    public ProjectInfoForUpdateResponseDto getInfoForUpdateProject(Long projectId) {
        Project project = findProject(projectId);
        if (!SecurityUtil.getCurrentMemberId().equals(project.getMember().getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

        return ProjectInfoForUpdateResponseDto.of(project,
            projectTechstackSimple(project), makeClubSimpleInfoResponseDtos(
                memberClubRepository.findClubByMember(
                    findMember(SecurityUtil.getCurrentMemberId()))));
    }

    // 프로젝트 업데이트
    @Transactional
    public HttpStatus update(Long projectId, ProjectUpdateRequestDto dto) {
        validCity(dto.getCity());
        Project project = findProject(projectId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        // 권한 체크
        MemberProject mp = memberProjectRepository.findById(
                new CompositeMemberProject(member, project))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_PROJECT_NOT_FOUND));
        if (!mp.getAuthority().equals(GroupAuthority.소유자)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

        project.update(dto, findClub(dto.getClubId()), findDBFile(dto.getUuid()));
        addTechstack(project, dto.getTechstacks());

        return HttpStatus.OK;
    }

    // 프로젝트 삭제
    @Transactional
    public HttpStatus delete(Long projectId) {
        Project project = findProject(projectId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        // 권한 체크
        MemberProject mp = memberProjectRepository.findById(
                new CompositeMemberProject(member, project))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_PROJECT_NOT_FOUND));
        if (!mp.getAuthority().equals(GroupAuthority.소유자)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }
        // 프로젝트 멤버 비활성화
        List<MemberProject> memberProjects = memberProjectRepository.findMemberRelationInProject(
            project);
        for (MemberProject mem : memberProjects) {
            mem.deactivation();
        }
        // 프로젝트 기술 스택 제거 (안지워도 될수도?)
        projectTechstackRepository.deleteAllByProject(project);
        // 프로젝트 비활성화
        project.setIsActive(Boolean.FALSE);

        return HttpStatus.OK;
    }

    // 모든 프로젝트 간단 조회 (List 변환말고 Page로 한번에 변환할 수 있는 방법 찾기) + (공개 범위 고려)
    public List<ProjectSimpleInfoResponseDto> getAllProject(Pageable pageable) {
        Page<Project> projects = projectRepository.findAllProject(
            ProjectProgressState.FINISH, RecruitmentState.RECRUITMENT, PublicScope.Public,
            pageable);
        List<ProjectSimpleInfoResponseDto> projectInfoResponseDtos = new ArrayList<>();
        for (Project project : projects) {
            projectInfoResponseDtos.add(
                ProjectSimpleInfoResponseDto.of(project, projectTechstackSimple(project)));
        }
        return projectInfoResponseDtos;
    }
    // 추천 프로젝트 조회
    public List<ProjectSimpleInfoResponseDto> getRecommendationProject(Pageable pageable) {
//        Pageable limit = PageRequest.of(0, 10);
        Page<Project> projects = projectRepository.findRecommendationProject(pageable);
        List<ProjectSimpleInfoResponseDto> projectInfoResponseDtos = new ArrayList<>();
        for (Project project : projects) {
            projectInfoResponseDtos.add(
                ProjectSimpleInfoResponseDto.of(project, projectTechstackSimple(project)));
        }
        return projectInfoResponseDtos;
    }

    // 현재 프로젝트 정보 리턴
    public ProjectInfoResponseDto getOneProject(Long projectId) {
        Project project = findProject(projectId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        List<MemberProject> mps = memberProjectRepository.findMemberRelationInProject(project);

        String authority = "게스트";
        for (MemberProject mp : mps) {
            if (mp.getCompositeMemberProject().getMember().getId().equals(member.getId())) {
                authority = mp.getAuthority().toString();
                break;
            }
        }
        return ProjectInfoResponseDto.of(project, projectTechstackFull(project),
            memberRole(project, "개발자"), memberRole(project, "기획자"), memberRole(project, "디자이너"),
            authority);
    }

    // 프로젝트 기술 스택 간단한 정보
    public List<ProjectTechstackResponseDto> projectTechstackSimple(Project project) {
        return projectTechstackRepository.findProjectTechstackByProject(project)
            .stream()
            .map(ProjectTechstackResponseDto::simple)
            .collect(Collectors.toList());
    }

    // 프로젝트 기술 스택 전체 정보
    public List<ProjectTechstackResponseDto> projectTechstackFull(Project project) {
        return projectTechstackRepository.findProjectTechstackByProject(project)
            .stream()
            .map(ProjectTechstackResponseDto::full)
            .collect(Collectors.toList());
    }

    // 현재 프로젝트에 어떤 멤버가 속해있는지 멤버 리스트 리턴
    public List<Member> memberInProject(Project project) {
        return memberProjectRepository.findMemberInProject(project);
    }

    // 특정 프로젝트의 특정 역할인 멤버의 닉네임 리스트
    public List<MemberSimpleInfoResponseDto> memberRole(Project project, String role) {
        return memberProjectRepository.findMemberRole(project, role)
            .stream()
            .map(MemberSimpleInfoResponseDto::from)
            .collect(Collectors.toList());
    }

    // 기술 스택 추가
    @Transactional
    public void addTechstack(Project project, HashMap<String, String> techstacks) {
        projectTechstackRepository.deleteAllByProject(project);

        for (String tech : techstacks.keySet()) {
            Techstack techstack = findTechstack(tech);
            Level level = Level.from(techstacks.get(tech));

            CompositeProjectTechstack compositeProjectTechstack = new CompositeProjectTechstack(
                techstack, project);

            projectTechstackRepository.save(new ProjectTechstack(compositeProjectTechstack, level));
        }
    }

    // 멤버 추가
    @Transactional
    public void addMember(Project project, Member member, String role) {
        CompositeMemberProject compositeMemberProject = new CompositeMemberProject(member, project);
        // DB에 해당 멤버 기록이 없다면 새로 생성
        MemberProject memberProject = memberProjectRepository
            .findById(compositeMemberProject)
            .orElseGet(() -> MemberProject.builder()
                .compositeMemberProject(compositeMemberProject)
                .build());

        project.addRole(role);
        memberProject.setRole(role);
        memberProject.setRegisterDate(LocalDateTime.now());
        memberProject.setAuthority(GroupAuthority.팀원);
        memberProject.activation();
        memberProjectRepository.save(memberProject);
    }

    // 멤버 탈퇴
    @Transactional
    public void removeMe(Long projectId) {
        Project project = findProject(projectId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());

        MemberProject mp = memberProjectRepository.findById(
                new CompositeMemberProject(member, project))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_PROJECT_NOT_FOUND));

        if (mp.getAuthority().equals(GroupAuthority.소유자)) {
            throw new CustomException(ErrorCode.HOST_CANNOT_LEAVE);
        }

        CompositeMemberProject compositeMemberProject = new CompositeMemberProject(member, project);
        // DB에 해당 멤버 기록이 없다면 새로 생성
        MemberProject memberProject = memberProjectRepository.findById(compositeMemberProject)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        project.removeRole(memberProject.getRole());
        memberProject.deactivation();
    }

    // 멤버 추방
    @Transactional
    public void removeMember(Long projectId, Long memberId) {
        Project project = findProject(projectId);
        Member remover = findMember(SecurityUtil.getCurrentMemberId());
        Member removed = findMember(memberId);

        MemberProject removermp = memberProjectRepository.findById(
                new CompositeMemberProject(remover, project))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_PROJECT_NOT_FOUND));

        MemberProject removedmp = memberProjectRepository.findById(
                new CompositeMemberProject(removed, project))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_PROJECT_NOT_FOUND));

        if (removermp.getAuthority().equals(GroupAuthority.팀원)) { // 소유자와 관리자만이 추방 권한을 가짐
            throw new CustomException(ErrorCode.COMMON_MEMBER_CANNOT_REMOVE);
        }

        if (!removedmp.getAuthority().equals(GroupAuthority.팀원)) { // 소유자와 관리자는 추방될 수 없음
            throw new CustomException(ErrorCode.ONLY_CAN_REMOVE_COMMON);
        }

        project.removeRole(removedmp.getRole()); // 추방되는 사람의 역할 수 감소
        removedmp.deactivation(); // 비활성화
    }

    // 역할 변경
    @Transactional
    public void changeRole(Long projectId, Long memberId, String role) {
        Project project = findProject(projectId);
        Member member = findMember(memberId);

        MemberProject mp = memberProjectRepository.findById(
                new CompositeMemberProject(member, project))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_PROJECT_NOT_FOUND));

        // 역할 변경 권한에 관한 로직
        // 본인 권한 이하? 미만? 의 인원 역할 변경이 가능하다
        // 본인의 역할 변경이 가능하다?

        project.removeRole(mp.getRole());
        project.addRole((role));
    }

    // 권한 변경
    @Transactional
    public void changeAuthority(Long projectId, Long memberId, String authority) {
        Project project = findProject(projectId);
        Member member = findMember(memberId);

        MemberProject mp = memberProjectRepository.findById(
                new CompositeMemberProject(member, project))
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_PROJECT_NOT_FOUND));

        // 권한 변경 권한에 관한 로직

        mp.setAuthority(GroupAuthority.from(authority));
    }

    // 프로젝트 찾기
    public Project findProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        if (Boolean.FALSE.equals(project.getIsActive())) {
            throw new CustomException(ErrorCode.DELETED_PROJECT);
        }

        return project;
    }

    // 멤버 찾기
    public Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (Boolean.FALSE.equals(member.getIs_active())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return member;
    }

    // 기술 스택 찾기
    public Techstack findTechstack(String techName) {
        return techstackRepository.findByName(techName)
            .orElseThrow(() -> new CustomException(ErrorCode.TECHSTACK_NOT_FOUND));
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

    public void validCity(String city) {
        if (!Stream.of(City.values()).map(Enum::name)
            .collect(Collectors.toList()).contains(city)) {
            throw new CustomException(ErrorCode.CITY_NOT_FOUND);
        }
    }

    public List<ClubSimpleInfoResponseDto> makeClubSimpleInfoResponseDtos(List<Club> clubs) {
        return clubs.stream()
            .map(ClubSimpleInfoResponseDto::from)
            .collect(Collectors.toList());
    }

    public List<MemberSimpleInfoResponseDto> makeMemberSimpleInfoResponseDtos(
        List<Member> members) {
        return members.stream()
            .map(MemberSimpleInfoResponseDto::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void makeBasicBoards(Project project) {
        projectBoardRepository.save(new ProjectBoard("공지사항", project));
        projectBoardRepository.save(new ProjectBoard("게시판", project));
    }

    // 신청 버튼 클릭시 관련 정보 및 권한 체크
    public InfoForApplyProjectFormResponseDto getInfoForApply(Long projectId) throws Exception {
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Project project = findProject(projectId);

        List<Member> memberList = memberInProject(project);
        for (Member mem : memberList) {
            if (SecurityUtil.getCurrentMemberId() == mem.getId()) {
                throw new Exception("이미 가입한 멤버입니다.");
            }
        }
//
//        if (!project.getIsParticipate()) {
//            throw new Exception("참여 불가능한 프로젝트입니다.");
//        }

        InfoForApplyProjectFormResponseDto dto = InfoForApplyProjectFormResponseDto.builder()
            .nickname(member.getNickname())
            .position(member.getPosition()) // 역할도 받아와야할수도
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
    public HttpStatus applyProject(Long projectId, ProjectApplicationRequestDto dto)
        throws Exception {
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Project project = findProject(projectId);

        CompositeMemberProject cmp = new CompositeMemberProject(member, project);

        Optional<ProjectApplicationForm> form = projectApplicationFormRepository.findById(cmp);
        if (form.isPresent()) {
            throw new Exception("신청한 내역이 존재합니다.");
        }

        ProjectApplicationForm projectApplicationForm = new ProjectApplicationForm(cmp, dto);

        projectApplicationForm.setDbFile(findDBFile(dto.getUuid()));

        projectApplicationFormRepository.save(projectApplicationForm);
        return HttpStatus.OK;
    }

    // 모든 신청서 작성일 기준 내림차순 조회
    public List<ProjectFormInfoResponseDto> allProjectForm(Long projectId) throws Exception {
        Project project = findProject(projectId);

        if (SecurityUtil.getCurrentMemberId() != project.getMember().getId()) {
            throw new Exception("조회 권한이 없습니다.");
        }

        List<ProjectApplicationForm> forms = projectApplicationFormRepository.formByProjectId(
            project);
        List<ProjectFormInfoResponseDto> projectFormInfoResponseDtos = new ArrayList<>();

        for (ProjectApplicationForm form : forms) {
            projectFormInfoResponseDtos.add(ProjectFormInfoResponseDto.builder()
                .form(form)
//                .strong(memberExperiencedTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
//                .knowledgeable(memberBeginnerTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
                .build());
        }

        return projectFormInfoResponseDtos;
    }

    // 닉네임으로 신청서 검색
    public List<ProjectFormInfoResponseDto> allFormByProjectNickname(Long projectId,
        String nickname) throws Exception {
        Project project = findProject(projectId);

        if (SecurityUtil.getCurrentMemberId() != project.getMember().getId()) {
            throw new Exception("조회 권한이 없습니다.");
        }

        List<ProjectApplicationForm> forms = projectApplicationFormRepository.formByProjectId(
            project);
        List<ProjectFormInfoResponseDto> projectFormInfoResponseDtos = new ArrayList<>();

        for (ProjectApplicationForm form : forms) {
            projectFormInfoResponseDtos.add(ProjectFormInfoResponseDto.builder()
                .form(form)
//                .strong(memberExperiencedTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
//                .knowledgeable(memberBeginnerTechstackRepository
//                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
                .build());
        }

        return projectFormInfoResponseDtos;
    }

    // 신청서 목록의 복합 기본키를 가져와 해당 신청서 상세조회 (프론트 방식에 따라 불필요할 수 있음)
    public ProjectFormInfoResponseDto oneProjectForm(Long projectId, Long memberId)
        throws Exception {
        CompositeMemberProject cmp = new CompositeMemberProject(findMember(memberId),
            findProject(projectId));

        ProjectApplicationForm form = projectApplicationFormRepository.oneFormById(cmp)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 신청서입니다"));

        return ProjectFormInfoResponseDto.builder()
            .form(form)
//            .strong(memberExperiencedTechstackRepository
//                .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
//            .knowledgeable(memberBeginnerTechstackRepository
//                .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
            .build();
    }

    // 가입 승인
    @Transactional
    public HttpStatus approval(Long projectId, Long memberId) throws Exception {
        Project project = findProject(projectId);
        List<Member> members = memberInProject(project);
        Member member = findMember(memberId);

        for (Member mem : members) {
            if (mem.equals(member)) {
                throw new Exception("이미 가입되어있는 회원입니다.");
            }
        }

        ProjectApplicationForm form = projectApplicationFormRepository
            .findById(new CompositeMemberProject(findMember(memberId), findProject(projectId)))
            .orElseThrow(() -> new NullPointerException("존재하지 않는 신청서입니다."));

        addMember(project, member, form.getRole());
        reject(projectId, memberId);

        return HttpStatus.OK;
    }

    // 신청서 제거
    @Transactional
    public HttpStatus reject(Long projectId, Long memberId) throws Exception {
        projectApplicationFormRepository.delete(projectApplicationFormRepository
            .findById(new CompositeMemberProject(findMember(memberId), findProject(projectId)))
            .orElseThrow(() -> new NullPointerException("존재하지 않는 신청서입니다.")));

        return HttpStatus.OK;
    }
}
