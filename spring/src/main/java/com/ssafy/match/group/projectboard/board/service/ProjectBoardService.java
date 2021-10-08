package com.ssafy.match.group.projectboard.board.service;

import com.ssafy.match.group.projectboard.board.dto.ProjectBoardCreateRequestDto;
import com.ssafy.match.group.projectboard.board.dto.ProjectBoardInfoDto;
import com.ssafy.match.group.projectboard.board.dto.ProjectBoardUpdateDto;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.group.projectboard.board.repository.ProjectBoardRepository;
import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.repository.project.ProjectRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectBoardService {
    private final MemberRepository memberRepository;
    private final ProjectBoardRepository projectBoardRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<ProjectBoardInfoDto> getProjectBoards(Long projectId) throws Exception {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("존재하지 않는 project입니다.");
        }
        Project project = projectRepository.getById(projectId);
        List<ProjectBoardInfoDto> projectBoardInfoDtos = projectBoardRepository.findAllByProject(project).stream()
                .map(ProjectBoardInfoDto::of)
                .collect(Collectors.toList());
        return projectBoardInfoDtos;
    }

    @Transactional
    public Integer createBoard(Long projectId, ProjectBoardCreateRequestDto projectBoardCreateRequestDto) throws Exception {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("존재하지 않는 project입니다.");
        }
        Project project = projectRepository.getById(projectId);
        ProjectBoard projectBoard = projectBoardCreateRequestDto.toProjectBoard(project);
        ProjectBoard ret = projectBoardRepository.save(projectBoard);
        return ret.getId();
    }

    @Transactional
    public Boolean deleteBoard(Integer boardId) throws Exception {
        if (!projectBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다.");
        }
        projectBoardRepository.delete(projectBoardRepository.getById(boardId));
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean updateBoard(Integer boardId, ProjectBoardUpdateDto projectBoardUpdateDto) throws Exception {
        if (!projectBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다.");
        }
        ProjectBoard projectBoard = projectBoardRepository.getById(boardId);
        projectBoard.setName(projectBoardUpdateDto.getName());
        return Boolean.TRUE;
    }

    public Member findMember(Long memberId) throws Exception {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NullPointerException("회원 정보가 없습니다."));
        if (!member.getIs_active()) {
            throw new Exception("삭제된 멤버입니다.");
        }
        return member;
    }
}
