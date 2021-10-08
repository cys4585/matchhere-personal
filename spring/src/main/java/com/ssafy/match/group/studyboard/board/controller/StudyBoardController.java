package com.ssafy.match.group.studyboard.board.controller;


import com.ssafy.match.group.studyboard.board.dto.StudyBoardCreateRequestDto;
import com.ssafy.match.group.studyboard.board.dto.StudyBoardInfoDto;
import com.ssafy.match.group.studyboard.board.dto.StudyBoardUpdateDto;
import com.ssafy.match.group.studyboard.board.service.StudyBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyBoardController {
    private final StudyBoardService studyBoardService;

    @GetMapping("/{studyid}/boards")
    @ApiOperation(value = "(스터디)게시판 리스트 조회", notes = "<strong>받은 스터디 id</strong>를 사용해서 게시판들을 조회한다.")
    public ResponseEntity<List<StudyBoardInfoDto>> getBoards(@PathVariable("studyid") Long studyid) throws Exception {
        return ResponseEntity.ok(studyBoardService.getStudyBoards(studyid));
    }

    @PostMapping("/{studyid}/boards")
    @ApiOperation(value = "(스터디)게시판 생성", notes = "<strong>받은 스터디 id</strong>를 사용해서 게시판을 생성한다.")
    public ResponseEntity<Integer> create(@PathVariable("studyid") Long studyid, @RequestBody StudyBoardCreateRequestDto studyBoardCreateRequestDto) throws Exception {
        return ResponseEntity.ok(studyBoardService.createBoard(studyid, studyBoardCreateRequestDto));
    }

    @DeleteMapping("/{studyid}/boards/{boardid}")
    @ApiOperation(value = "(스터디)게시판 삭제", notes = "<strong>받은 게시판 id</strong>를 사용해서 게시판을 삭제한다.")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable("studyid") Long studyid, @PathVariable("boardid") Integer boardid) throws Exception {
        return ResponseEntity.ok(studyBoardService.deleteBoard(boardid));
    }

    @PutMapping("/{studyid}/boards/{boardid}")
    @ApiOperation(value = "(스터디)게시판 수정", notes = "<strong>받은 게시판 id</strong>를 사용해서 게시판을 수정한다.")
    public ResponseEntity<Boolean> updateBoard(@PathVariable("studyid") Long studyid, @PathVariable("boardid") Integer boardid, @RequestBody StudyBoardUpdateDto studyBoardUpdateDto) throws Exception {
        return ResponseEntity.ok(studyBoardService.updateBoard(boardid, studyBoardUpdateDto));
    }
}
