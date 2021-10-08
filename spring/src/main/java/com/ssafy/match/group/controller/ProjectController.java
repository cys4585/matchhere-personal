package com.ssafy.match.group.controller;

import com.ssafy.match.group.dto.project.request.ProjectCreateRequestDto;
import com.ssafy.match.group.dto.project.request.ProjectUpdateRequestDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoForCreateResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoForUpdateResponseDto;
import com.ssafy.match.group.dto.project.response.ProjectInfoResponseDto;
import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/myclublist")
    @ApiOperation(value = "프로젝트 생성을 위한 정보", notes = "<strong>프로젝트를 생성하기 위한</strong> 생성할 멤버의 클럽을 받는다")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<ProjectInfoForCreateResponseDto> getInfoForCreate() throws Exception {
        return ResponseEntity.ok(projectService.getInfoForCreate());
    }

    @GetMapping("/infoforupdate/{projectId}")
    @ApiOperation(value = "프로젝트 업데이트를 위한 정보",
        notes = "<strong>받은 프로젝트 id</strong>로 해당 프로젝트 정보 + 수정을 위한 정보(사용자 클럽 리스트, 지역, 상태 리스트 등")
    public ResponseEntity<ProjectInfoForUpdateResponseDto> getInfoForUpdate(@PathVariable("projectId") Long projectId)
        throws Exception {
        return ResponseEntity.ok(projectService.getInfoForUpdateProject(projectId));
    }

    @PostMapping
    @ApiOperation(value = "프로젝트 생성", notes = "<strong>받은 프로젝트 정보</strong>를 사용해서 프로젝트을 생성한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Long> createProject(@RequestBody ProjectCreateRequestDto dto) throws Exception {
        return ResponseEntity.ok(projectService.create(dto));
    }

    @PatchMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 수정", notes = "<strong>받은 프로젝트 정보</strong>를 사용해서 프로젝트를 수정한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> updateProject(@RequestBody ProjectUpdateRequestDto dto,
        @PathVariable("projectId") Long projectId) throws Exception {
        return ResponseEntity.ok(projectService.update(projectId, dto));
    }

    @DeleteMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 삭제", notes = "<strong>받은 프로젝트 Id</strong>로 프로젝트와 포함된 멤버관계를 삭제한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("projectId") Long projectId)
        throws Exception {
        return ResponseEntity.ok(projectService.delete(projectId));
    }

    @DeleteMapping("/{projectId}/member")
    @ApiOperation(value = "프로젝트 탈퇴", notes = "<strong>받은 프로젝트 id</strong>로 프로젝트에서 탈퇴한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public void deleteMember(@PathVariable("projectId") Long projectId)
        throws Exception {
        projectService.removeMember(projectId);
    }

    @GetMapping("/{projectId}")
    @ApiOperation(value = "프로젝트 상세정보 조회",
        notes = "<strong>받은 프로젝트 Id</strong>로 해당 프로젝트를 조회 + 전체 기술스택, 역할별 인원 닉네임, 전체 지역 정보, 포함 인원 등")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<ProjectInfoResponseDto> getOneProject(@PathVariable("projectId") Long projectId)
        throws Exception {
        return ResponseEntity.ok(projectService.getOneProject(projectId));
    }

    @GetMapping("/info/{memberId}")
    @ApiOperation(value = "특정 멤버가 속한 프로젝트 조회", notes = "<strong>받은 멤버 Id</strong>로 해당 멤버가 속한 프로젝트 정보 조회")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<ProjectInfoResponseDto>> projectInMember(@PathVariable("memberId") Long memberId)
        throws Exception {
        return ResponseEntity.ok(projectService.projectInMember(memberId));
    }

    @GetMapping
    @ApiOperation(value = "모든 프로젝트 조회", notes = "(isPublic :True, isActive:True)를 만족하는 프로젝트들을 작성일 기준 내림차순으로 받는다")
    public ResponseEntity<Page<ProjectInfoResponseDto>> getAllProject(@PageableDefault(size = 10) @SortDefault(sort = "createDate", direction= Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(projectService.getAllProject(pageable));
    }
}
