package com.ssafy.match.group.controller;

import com.ssafy.match.group.dto.study.request.StudyApplicationRequestDto;
import com.ssafy.match.group.dto.study.response.InfoForApplyStudyFormResponseDto;
import com.ssafy.match.group.dto.study.response.StudyFormInfoResponseDto;
import com.ssafy.match.group.service.StudyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyapplication")
public class StudyApplicationController {

    private final StudyService studyService;

    @GetMapping("/infoforcreate/{studyId}")
    @ApiOperation(value = "신청서 생성을 위한 정보", notes = "<strong>스터디에 가입하기 위한</strong>신청서를 작성하기 위한 정보(전체 기술, 선택할 수 있는 지역 리스트)를 받는다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<InfoForApplyStudyFormResponseDto> getInfoForApply(@PathVariable("studyId") Long studyId) throws Exception {
        return ResponseEntity.ok(studyService.getInfoForApply(studyId));
    }

    @PostMapping("/{studyId}")
    @ApiOperation(value = "스터디 가입 신청", notes = "<strong>받은 신청서 정보로</strong>를 사용해서 스터디에 신청을 한다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> applyStudy(@PathVariable("studyId") Long studyId, @RequestBody StudyApplicationRequestDto dto) throws Exception {
        return ResponseEntity.ok(studyService.applyStudy(studyId, dto));
    }

    @PostMapping("/approval/{studyId}/{memberId}")
    @ApiOperation(value = "스터디 가입 승인", notes = "<strong>받은 신청서 Id</strong>를 사용해서 해당 멤버를 가입 승인한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> approval(@PathVariable("studyId") Long studyId, @PathVariable("memberId") Long memberId) throws Exception {
        return ResponseEntity.ok(studyService.approval(studyId, memberId));
    }

    @DeleteMapping("{studyId}/{memberId}")
    @ApiOperation(value = "신청서 삭제", notes = "<strong>받은 신청서 Id</strong>를 사용해서 해당 신청서를 제거한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> reject(@PathVariable("studyId") Long studyId, @PathVariable("memberId") Long memberId) throws Exception{
        return ResponseEntity.ok(studyService.reject(studyId, memberId));
    }

    @GetMapping("/all/{studyId}/{nickname}")
    @ApiOperation(value = "특정 스터디 신청서 닉네임 포함 모든 신청서 조회", notes = "특정 스터디의 닉네임 포함 모든 신청서 리스트를 작성일 기준 내림차순으로 받는다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<StudyFormInfoResponseDto>> getAllFormByStudyNickname(@PathVariable("studyId") Long studyId,
        @PathVariable("nickname") String nickname) throws Exception {
        return ResponseEntity.ok(studyService.getAllFormByStudyNickname(studyId, nickname));
    }

    @GetMapping("/all/{studyId}")
    @ApiOperation(value = "특정 스터디 모든 신청서 조회", notes = "특정 스터디의 모든 신청서 리스트를 작성일 기준 내림차순으로 받는다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<StudyFormInfoResponseDto>> getAllStudyForm(@PathVariable("studyId") Long studyId) throws Exception {
        return ResponseEntity.ok(studyService.getAllStudyForm(studyId));
    }

    @GetMapping("/one/{studyId}/{memberId}")
    @ApiOperation(value = "특정 신청서 조회", notes = "특정 신청서를 상세 조회한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<StudyFormInfoResponseDto> getOneStudyForm(@PathVariable("studyId") Long studyId, @PathVariable("memberId") Long memberId) throws Exception {
        return ResponseEntity.ok(studyService.getOneStudyForm(studyId, memberId));
    }

}
