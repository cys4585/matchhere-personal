package com.ssafy.match.group.projectboard.board.service;

import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.group.projectboard.board.dto.ProjectBoardCreateRequestDto;
import com.ssafy.match.group.projectboard.board.dto.ProjectBoardInfoDto;
import com.ssafy.match.group.projectboard.board.dto.ProjectBoardUpdateDto;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.group.projectboard.board.repository.ProjectBoardRepository;
import com.ssafy.match.group.project.entity.Project;
import com.ssafy.match.group.project.repository.ProjectRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectBoardService {

    private final ProjectBoardRepository projectBoardRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<ProjectBoardInfoDto> getProjectBoards(Long projectId) {
        return projectBoardRepository.findAllByProject(findProject(projectId)).stream()
            .map(ProjectBoardInfoDto::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public Integer createBoard(Long projectId, ProjectBoardCreateRequestDto dto) {
        return projectBoardRepository.save(ProjectBoard.of(dto, findProject(projectId))).getId();
    }

    @Transactional
    public HttpStatus deleteBoard(Integer boardId) {
        projectBoardRepository.delete(projectBoardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND)));
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus updateBoard(Integer boardId, ProjectBoardUpdateDto projectBoardUpdateDto) {
        ProjectBoard projectBoard = findBoard(boardId);
        projectBoard.setName(projectBoardUpdateDto.getName());
        return HttpStatus.OK;
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

    // 게시판 찾기
    public ProjectBoard findBoard(int boardId) {
        return projectBoardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }
}
