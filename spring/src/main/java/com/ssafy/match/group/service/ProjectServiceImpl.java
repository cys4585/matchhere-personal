package com.ssafy.match.group.service;

import com.ssafy.match.db.entity.City;
import com.ssafy.match.group.clubboard.board.repository.ClubBoardRepository;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.entity.club.Club;
import com.ssafy.match.group.clubboard.board.entity.ClubBoard;
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
import com.ssafy.match.group.dto.project.request.ProjectApplicationRequestDto;
import com.ssafy.match.group.dto.project.request.ProjectCreateRequestDto;
import com.ssafy.match.group.dto.project.request.ProjectUpdateRequestDto;
import com.ssafy.match.group.dto.project.response.InfoForApplyProjectFormResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectFormInfoResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoForCreateResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoForUpdateResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoResponseDto;
import com.ssafy.match.group.entity.project.CompositeMemberProject;
import com.ssafy.match.group.entity.project.CompositeProjectTechstack;
import com.ssafy.match.group.entity.project.MemberProject;
import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.entity.project.ProjectApplicationForm;
import com.ssafy.match.group.entity.project.ProjectTechstack;
import com.ssafy.match.group.repository.club.ClubRepository;
import com.ssafy.match.group.repository.project.MemberProjectRepository;
import com.ssafy.match.group.repository.project.ProjectApplicationFormRepository;
import com.ssafy.match.group.repository.project.ProjectRepository;
import com.ssafy.match.group.repository.project.ProjectTechstackRepository;
import com.ssafy.match.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final MemberExperiencedTechstackRepository memberExperiencedTechstackRepository;
    private final MemberBeginnerTechstackRepository memberBeginnerTechstackRepository;
    private final MemberSnsRepository memberSnsRepository;
//    private final ProjectBoardRepository projectBoardRepository;

    public ProjectInfoForCreateResponseDto getInfoForCreate() throws Exception {
        return ProjectInfoForCreateResponseDto.builder()
            .hostClub(makeClubDtos(memberClubRepository.
                findClubByMember(findMember(SecurityUtil.getCurrentMemberId()))))
            .build();
    }

    @Transactional
    public Long create(ProjectCreateRequestDto dto) throws Exception {
        validCity(dto.getCity());
        validTechstack(dto.getTechList());

        Project project = new Project(dto, findClub(dto.getClubId()), findDBFile(dto.getUuid()));
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        project.setMember(member);

        projectRepository.save(project);

//        makeBasicBoards(project);
        addTechstack(project, dto.getTechList());
        addMember(project, member, dto.getHostRole());

        return project.getId();
    }

    @Transactional
    public HttpStatus update(Long projectId, ProjectUpdateRequestDto dto) throws Exception {
        validCity(dto.getCity());
        validTechstack(dto.getAddStackList());
        validTechstack(dto.getRemoveStackList());
        validStatus(dto.getStatus());

        Project project = findProject(projectId);
        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        if (!project.getMember().getId().equals(currentMemberId)) {
            throw new Exception("권한이 없습니다.");
        }

        project.update(dto, findClub(dto.getClubId()), findDBFile(dto.getUuid()));
        project.setMember(findMember(dto.getHostId()));
        addTechstack(project, dto.getAddStackList());
        removeTechstack(project, dto.getRemoveStackList());

        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus delete(Long projectId) throws Exception {
        Project project = findProject(projectId);

        if (project.getMember().getId() != SecurityUtil.getCurrentMemberId()) {
            throw new Exception("권한이 없습니다.");
        }

        List<MemberProject> memberProjects = memberProjectRepository.findMemberRelationInProject(project);
        for (MemberProject mem : memberProjects) {
            mem.deactivation();
        }

        List<ProjectTechstack> pts = projectTechstackRepository.findProjectTechstackByProject(project);
        for (ProjectTechstack pt: pts) {
            projectTechstackRepository.delete(pt);
        }

        project.setIsActive(false);

        return HttpStatus.OK;
    }

    public Page<ProjectInfoResponseDto> getAllProject(Pageable pageable) {
        Page<ProjectInfoResponseDto> projectInfoResponseDtos = projectRepository.findByIsActiveAndIsPublicAndStatusIsNot(Boolean.TRUE, Boolean.TRUE, Status.종료, pageable)
                .map(ProjectInfoResponseDto::of);
        for (ProjectInfoResponseDto projectInfoResponseDto: projectInfoResponseDtos.getContent()) {
            projectInfoResponseDto.setMemberDtos(makeMemberDtos(memberProjectRepository.findMemberByProjectId(projectInfoResponseDto.getId())));
        }
        return projectInfoResponseDtos;
    }

    // 현재 스터디에 속한 멤버 리스트
    public List<Member> findMemberInProject(Project project) {
        return memberProjectRepository.findMemberInProject(project);
    }

    // 현재 스터디 기술 스택의 이름 리스트
    public List<String> projectTechstackName(Project project) {
        return projectTechstackRepository.findByProjectTechstackName(project);
    }

    // 현재 프로젝트 정보 리턴
    public ProjectInfoResponseDto getOneProject(Long projectId) throws Exception {
        Project project = findProject(projectId);
        if (!SecurityUtil.getCurrentMemberId().equals(project.getMember().getId())
            && !project.getIsPublic()) {
            throw new Exception("비공개된 프로젝트입니다.");
        }
        ProjectInfoResponseDto projectInfoResponseDto = projectRepository.findById(projectId).map(ProjectInfoResponseDto::of).orElseThrow(() -> new NullPointerException("프로젝트가 존재하지 않습니다."));
        projectInfoResponseDto.setMemberDtos(makeMemberDtos(memberProjectRepository.findMemberByProjectId(projectId)));
        projectInfoResponseDto.setDeveloperNicknames(memberNicknames(projectId, "개발자"));
        projectInfoResponseDto.setDesignerNicknames(memberNicknames(projectId, "디자이너"));
        projectInfoResponseDto.setPlannerNicknames(memberNicknames(projectId, "기획자"));
        projectInfoResponseDto.setTechList(projectTechstackName(project));

        return projectInfoResponseDto;
    }

    public ProjectInfoForUpdateResponseDto getInfoForUpdateProject(Long projectId) throws Exception {
        Project project = findProject(projectId);
        if (!SecurityUtil.getCurrentMemberId().equals(project.getMember().getId())) {
            throw new Exception("권한이 없습니다");
        }

        ProjectInfoForUpdateResponseDto dto = new ProjectInfoForUpdateResponseDto(project);
        dto.setHost(new MemberDto(project.getMember()));
        dto.setMemberDtos(makeMemberDtos(findMemberInProject(project)));
        dto.setProjectTechstack(projectTechstackName(project));
        dto.setClubList(makeClubDtos(memberClubRepository.findClubByMember(project.getMember())));
        dto.setMemberDtos(makeMemberDtos(findMemberInProject(project)));

        return dto;
    }

    // 현재 프로젝트에 어떤 멤버가 속해있는지 멤버 리스트 리턴
    public List<Member> memberInProject(Project project) {
        return memberProjectRepository.findMemberInProject(project);
    }

    // 특정 프로젝트의 특정 역할인 멤버의 닉네임 리스트
    public List<String> memberNicknames(Long projectId, String role) throws Exception {
        return memberProjectRepository.findMemberNickname(findProject(projectId), role);
    }

    // 특정 멤버의 활성화 프로젝트 리스트
    public List<ProjectInfoResponseDto> projectInMember(Long memberId) throws Exception {
//        List<ProjectInfoResponseDto> projectInfoResponseDtos = new ArrayList<>();
        System.out.println(memberProjectRepository.getProjectsByMemberAndStatus(findMember(memberId), Status.종료));
        List<Project> projects = memberProjectRepository.getProjectsByMemberAndStatus(findMember(memberId), Status.종료);
        System.out.println("!!!!!!!!!!!!");
        System.out.println(projects);
//        projects.ma
        List<ProjectInfoResponseDto> projectInfoResponseDtos = projects.stream().map(ProjectInfoResponseDto::of).collect(Collectors.toList());
        System.out.println("here!!!!!!!!!!!!!!!");
        System.out.println(projectInfoResponseDtos);
        for (ProjectInfoResponseDto projectInfoResponseDto: projectInfoResponseDtos) {
            projectInfoResponseDto.setMemberDtos(makeMemberDtos(memberProjectRepository.findMemberByProjectId(projectInfoResponseDto.getId())));
        }
//        for (Project project: memberProjectRepository.projectInMember(findMember(memberId))) {
//            if(project.getStatus().equals(Status.종료)) continue;
//            ProjectInfoResponseDto dto = new ProjectInfoResponseDto(project);
//            dto.setHost(new MemberDto(project.getMember()));
//            dto.setMemberDtos(makeMemberDtos(findMemberInProject(project)));
//            projectInfoResponseDtos.add(dto);
//        }
        return projectInfoResponseDtos;
    }

    // 모든 기술스택의 이름 리스트
    public List<String> allTechstackName() {
        return techstackRepository.findAllName();
    }

    @Transactional
    public void addTechstack(Project project, List<String> techName) {
        for (String name : techName) {
            Techstack techstack = findTechstack(name);
            CompositeProjectTechstack compositeProjectTechstack = new CompositeProjectTechstack(
                techstack, project);

            projectTechstackRepository.save(new ProjectTechstack(compositeProjectTechstack));

        }
    }

    @Transactional
    public void removeTechstack(Project project, List<String> techName) {
        for (String name : techName) {
            Techstack techstack = findTechstack(name);
            CompositeProjectTechstack compositeProjectTechstack = new CompositeProjectTechstack(
                techstack, project);

            ProjectTechstack projectTechstack = projectTechstackRepository
                .findById(compositeProjectTechstack)
                .orElseThrow(() -> new NullPointerException("해당 기술 스택이 존재하지않습니다."));

            projectTechstackRepository.delete(projectTechstack);
        }
    }

    @Transactional
    public void addMember(Project project, Member member, String role) {
        CompositeMemberProject compositeMemberProject = new CompositeMemberProject(member, project);
        // DB에 해당 멤버 기록이 없다면 새로 생성
        MemberProject memberProject = memberProjectRepository
            .findById(compositeMemberProject)
            .orElseGet(() -> MemberProject.builder()
                .compositeMemberProject(compositeMemberProject)
                .registerDate(LocalDateTime.now())
                .build());

        memberProject.activation();
        memberProjectRepository.save(memberProject);

        changeRole(project, member, role);
    }

    @Transactional
    public void removeMember(Long projectId) throws Exception {
        Project project = findProject(projectId);
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("잘못된 사용자 입니다.(사용자 없음)"));
        if(project.getMember().getId().equals(member.getId())){
            throw new Exception("프로젝트장은 탈퇴할 수 없습니다.");
        }
        CompositeMemberProject compositeMemberProject = new CompositeMemberProject(member, project);
        // DB에 해당 멤버 기록이 없다면 새로 생성
        MemberProject memberProject = memberProjectRepository.findById(compositeMemberProject)
            .orElseThrow(() -> new NullPointerException("이미 탈퇴된 멤버입니다."));
        memberProject.deactivation();
        changeRole(project, member, "");
    }

//    @Transactional
//    public void makeBasicBoards(Club club){
//        projectBoardRepository.save(new ProjectBoard("공지사항", project));
//        projectBoardRepository.save(new ProjectBoard("게시판", project));
//    }

    public Project findProject(Long projectId) throws Exception {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new NullPointerException("프로젝트 정보가 없습니다."));

        if (!project.getIsActive()) {
            throw new Exception("삭제된 프로젝트입니다.");
        }

        return project;
    }

    public Member findMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NullPointerException("회원 정보가 없습니다."));

        if (member.getIs_active() == false) {
            throw new Exception("삭제된 멤버입니다.");
        }

        return member;
    }

    public Techstack findTechstack(String techName) {
        return techstackRepository.findByName(techName)
            .orElseThrow(() -> new NullPointerException("기술 스택 정보가 없습니다."));
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

    // (아이디어가 생각이 안나서 임시로 If문 사용 조언 구함)
    @Transactional
    public void changeRole(Project project, Member member, String role) {
        MemberProject memberProject = memberProjectRepository.findMemberProject(project, member);

        String now = memberProject.getRole();

        if (now == null) { // 이제 생성된 프로젝트이거나 이제 가입한 경우
            if (role.equals("디자이너")) {
                project.plusDesigner();
                memberProject.setRole("디자이너");
            } else if (role.equals("개발자")) {
                project.plusDeveloper();
                memberProject.setRole("개발자");
            } else if (role.equals("기획자")) {
                project.plusPlanner();
                memberProject.setRole("기획자");
            }
        } else if (now.equals(role)) { // 변경사항이 없는 경우
            return;
        } else {
            if (now.equals("디자이너")) {
                project.minusDesigner();

                if (role.equals("개발자")) {
                    project.plusDeveloper();
                    memberProject.setRole("개발자");
                } else if (role.equals("기획자")) {
                    project.plusPlanner();
                    memberProject.setRole("기획자");
                }
            } else if (now.equals("개발자")) {
                project.minusDeveloper();

                if (role.equals("기획자")) {
                    project.plusPlanner();
                    memberProject.setRole("기획자");
                } else if (role.equals("디자이너")) {
                    project.plusDesigner();
                    memberProject.setRole("디자이너");
                }
            } else if (now.equals("기획자")) {
                project.minusPlanner();

                if (role.equals("개발자")) {
                    project.plusDeveloper();
                    memberProject.setRole("개발자");
                } else if (role.equals("디자이너")) {
                    project.plusDesigner();
                    memberProject.setRole("디자이너");
                }
            }
        }

        if (project.getMember().getId().equals(member.getId())) {
            project.setHostRole(role);
        }

    }

    // 신청 버튼 클릭시 관련 정보 및 권한 체크
    public InfoForApplyProjectFormResponseDto getInfoForApply(Long projectId) throws Exception {
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        Project project = findProject(projectId);

        List<Member> memberList = memberInProject(project);
        for (Member mem: memberList) {
            if(SecurityUtil.getCurrentMemberId() == mem.getId()){
                throw new Exception("이미 가입한 멤버입니다.");
            }
        }

        if(!project.getIsParticipate()){
            throw new Exception("참여 불가능한 프로젝트입니다.");
        }

        InfoForApplyProjectFormResponseDto dto = InfoForApplyProjectFormResponseDto.builder()
            .nickname(member.getNickname())
            .position(member.getPosition()) // 역할도 받아와야할수도
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
    public HttpStatus applyProject(Long projectId, ProjectApplicationRequestDto dto) throws Exception {
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

        if(SecurityUtil.getCurrentMemberId() != project.getMember().getId()){
            throw new Exception("조회 권한이 없습니다.");
        }

        List<ProjectApplicationForm> forms = projectApplicationFormRepository.formByProjectId(project);
        List<ProjectFormInfoResponseDto> projectFormInfoResponseDtos = new ArrayList<>();

        for (ProjectApplicationForm form: forms) {
            projectFormInfoResponseDtos.add(ProjectFormInfoResponseDto.builder()
                .form(form)
                .strong(memberExperiencedTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
                .knowledgeable(memberBeginnerTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
                .build());
        }

        return projectFormInfoResponseDtos;
    }

    // 닉네임으로 신청서 검색
    public List<ProjectFormInfoResponseDto> allFormByProjectNickname(Long projectId, String nickname) throws Exception{
        Project project = findProject(projectId);

        if(SecurityUtil.getCurrentMemberId() != project.getMember().getId()){
            throw new Exception("조회 권한이 없습니다.");
        }

        List<ProjectApplicationForm> forms = projectApplicationFormRepository.formByProjectId(project);
        List<ProjectFormInfoResponseDto> projectFormInfoResponseDtos = new ArrayList<>();

        for (ProjectApplicationForm form: forms) {
            projectFormInfoResponseDtos.add(ProjectFormInfoResponseDto.builder()
                .form(form)
                .strong(memberExperiencedTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
                .knowledgeable(memberBeginnerTechstackRepository
                    .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
                .build());
        }

        return projectFormInfoResponseDtos;
    }

    // 신청서 목록의 복합 기본키를 가져와 해당 신청서 상세조회 (프론트 방식에 따라 불필요할 수 있음)
    public ProjectFormInfoResponseDto oneProjectForm(Long projectId, Long memberId) throws Exception {
        CompositeMemberProject cmp = new CompositeMemberProject(findMember(memberId), findProject(projectId));

        ProjectApplicationForm form = projectApplicationFormRepository.oneFormById(cmp)
            .orElseThrow(()-> new NullPointerException("존재하지 않는 신청서입니다"));

        return ProjectFormInfoResponseDto.builder()
            .form(form)
            .strong(memberExperiencedTechstackRepository
                .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
            .knowledgeable(memberBeginnerTechstackRepository
                .findTechstackByMemberName(form.getCompositeMemberProject().getMember()))
            .build();
    }

    // 가입 승인
    @Transactional
    public HttpStatus approval(Long projectId, Long memberId) throws Exception {
        Project project = findProject(projectId);
        List<Member> members = memberInProject(project);
        Member member = findMember(memberId);

        for (Member mem: members) {
            if(mem.equals(member)){
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
