package com.ssafy.match.group.project.controller;

import com.ssafy.match.group.project.dto.request.ProjectApplicationRequestDto;
import com.ssafy.match.group.project.dto.response.InfoForApplyProjectFormResponseDto;
import com.ssafy.match.group.project.dto.response.ProjectFormInfoResponseDto;
import com.ssafy.match.group.project.service.ProjectService;
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
@RequestMapping("/projectapplication")
public class ProjectApplicationController {

    private final ProjectService projectService;

    @GetMapping("/check/{projectId}")
    @ApiOperation(value = "신청서 생성 가능 여부", notes = "멤버가 프로젝트에 신청 가능한지 여부")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "CANNOT_APPLY\nALREADY_JOIN"),
        @ApiResponse(code = 404, message = "PROJECT_NOT_FOUND\nMEMBER_NOT_FOUND"),
    })
    public ResponseEntity<String> checkForRegister(@PathVariable("projectId") Long projectId) {
        return new ResponseEntity<>("신청 가능합니다", projectService.checkCanApply(projectId));
    }

    @PostMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 가입 신청", notes = "<strong>받은 신청서 정보로</strong>를 사용해서 프로젝트에 신청을 한다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> createForm(@PathVariable("projectId") Long projectId, @RequestBody ProjectApplicationRequestDto dto) throws Exception {
        return ResponseEntity.ok(projectService.applyProject(projectId, dto));
    }

    @PostMapping("/approval/{projectId}/{memberId}")
    @ApiOperation(value = "프로젝트 가입 승인", notes = "<strong>받은 신청서 Id</strong>를 사용해서 해당 멤버를 가입 승인한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> approval(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) throws Exception {
        return ResponseEntity.ok(projectService.approval(projectId, memberId));
    }

    @DeleteMapping("{projectId}/{memberId}")
    @ApiOperation(value = "신청서 삭제", notes = "<strong>받은 신청서 Id</strong>를 사용해서 해당 신청서를 제거한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> reject(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) throws Exception{
        return ResponseEntity.ok(projectService.reject(projectId, memberId));
    }

    @GetMapping("/all/{projectId}/{nickname}")
    @ApiOperation(value = "특정 프로젝트 닉네임 포함 모든 신청서 조회", notes = "특정 프로젝트의 닉네임 포함 모든 신청서 리스트를 작성일 기준 내림차순으로 받는다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ProjectFormInfoResponseDto>> allFormByProjectNickname(@PathVariable("projectId") Long projectId,
        @PathVariable("nickname") String nickname) throws Exception {
        return ResponseEntity.ok(projectService.allFormByProjectNickname(projectId, nickname));
    }

    @GetMapping("/all/{projectId}")
    @ApiOperation(value = "특정 프로젝트 모든 신청서 조회", notes = "특정 프로젝트의 모든 신청서 리스트를 작성일 기준 내림차순으로 받는다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ProjectFormInfoResponseDto>> allProjectForm(@PathVariable("projectId") Long projectId) throws Exception {
        return ResponseEntity.ok(projectService.allProjectForm(projectId));
    }

    @GetMapping("/one/{projectId}/{memberId}")
    @ApiOperation(value = "특정 신청서 조회", notes = "특정 신청서를 상세 조회한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<ProjectFormInfoResponseDto> oneProjectForm(@PathVariable("projectId") Long projectId, @PathVariable("memberId") Long memberId) throws Exception {
        return ResponseEntity.ok(projectService.oneProjectForm(projectId, memberId));
    }

}
