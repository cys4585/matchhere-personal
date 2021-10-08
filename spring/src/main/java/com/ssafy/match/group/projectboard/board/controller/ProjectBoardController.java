package com.ssafy.match.group.projectboard.board.controller;


import com.ssafy.match.group.projectboard.board.dto.ProjectBoardCreateRequestDto;
import com.ssafy.match.group.projectboard.board.dto.ProjectBoardInfoDto;
import com.ssafy.match.group.projectboard.board.dto.ProjectBoardUpdateDto;
import com.ssafy.match.group.projectboard.board.service.ProjectBoardService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectBoardController {
    private final ProjectBoardService projectBoardService;

    @GetMapping("/{projectid}/boards")
    @ApiOperation(value = "(프로젝트)게시판 리스트 조회", notes = "<strong>받은 프로젝트 id</strong>를 사용해서 게시판들을 조회한다.")
    public ResponseEntity<List<ProjectBoardInfoDto>> getBoards(@PathVariable("projectid") Long projectid) throws Exception {
        return ResponseEntity.ok(projectBoardService.getProjectBoards(projectid));
    }

    @PostMapping("/{projectid}/boards")
    @ApiOperation(value = "(프로젝트)게시판 생성", notes = "<strong>받은 프로젝트 id</strong>를 사용해서 게시판을 생성한다.")
    public ResponseEntity<Integer> create(@PathVariable("projectid") Long projectid, @RequestBody ProjectBoardCreateRequestDto projectBoardCreateRequestDto) throws Exception {
        return ResponseEntity.ok(projectBoardService.createBoard(projectid, projectBoardCreateRequestDto));
    }

    @DeleteMapping("/{projectid}/boards/{boardid}")
    @ApiOperation(value = "(프로젝트)게시판 삭제", notes = "<strong>받은 게시판 id</strong>를 사용해서 게시판을 삭제한다.")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable("projectid") Long projectid, @PathVariable("boardid") Integer boardid) throws Exception {
        return ResponseEntity.ok(projectBoardService.deleteBoard(boardid));
    }

    @PutMapping("/{projectid}/boards/{boardid}")
    @ApiOperation(value = "(프로젝트)게시판 수정", notes = "<strong>받은 게시판 id</strong>를 사용해서 게시판을 수정한다.")
    public ResponseEntity<Boolean> updateBoard(@PathVariable("projectid") Long projectid, @PathVariable("boardid") Integer boardid, @RequestBody ProjectBoardUpdateDto projectBoardUpdateDto) throws Exception {
        return ResponseEntity.ok(projectBoardService.updateBoard(boardid, projectBoardUpdateDto));
    }
}
