package com.ssafy.match.group.study.controller;

import com.ssafy.match.group.study.dto.request.StudyCreateRequestDto;
import com.ssafy.match.group.study.dto.request.StudyUpdateRequestDto;
import com.ssafy.match.group.study.dto.response.StudyInfoForCreateResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoForUpdateResponseDto;
import com.ssafy.match.group.study.dto.response.StudyInfoResponseDto;
import com.ssafy.match.group.study.service.StudyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/myclublist")
    @ApiOperation(value = "스터디 생성을 위한 정보", notes = "스터디 생성을 위해 사용자의 클럽 정보를 조회")
    @ApiResponses({
        @ApiResponse(code = 200, message = "클럽 정보 조회"),
        @ApiResponse(code = 404, message = "MEMBER_NOT_FOUND"),
    })
    public ResponseEntity<StudyInfoForCreateResponseDto> getInfoForCreate() {
        return ResponseEntity.ok(studyService.getInfoForCreate());
    }

    @GetMapping("/infoforupdate/{studyId}")
    @ApiOperation(value = "스터디 업데이트를 위한 정보",
        notes = "<strong>받은 스터디 id</strong>로 해당 스터디 정보 + 수정을 위한 정보(사용자 클럽 리스트, 지역, 상태 리스트 등")
    @ApiResponses({
        @ApiResponse(code = 200, message = "정보 조회"),
        @ApiResponse(code = 401, message = "UNAUTHORIZED_CHANGE"),
        @ApiResponse(code = 404, message = "MEMBER_NOT_FOUND\nSTUDY_NOT_FOUND"),
    })
    public ResponseEntity<StudyInfoForUpdateResponseDto> getInfoForUpdate(@PathVariable("studyId") Long studyId) {
        return ResponseEntity.ok(studyService.getInfoForUpdateStudy(studyId));
    }

    @PostMapping
    @ApiOperation(value = "스터디 생성", notes = "<strong>받은 스터디 정보</strong>를 사용해서 스터디을 생성한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "생성한 스터디 정보"),
        @ApiResponse(code = 400, message = "MEMBER_COUNT_OVER\nMEMBER_COUNT_BELOW_ZERO"),
        @ApiResponse(code = 404, message = "CITY_NOT_FOUND\nMEMBER_NOT_FOUND\nCLUB_NOT_FOUND\nFILE_NOT_FOUND\nAUTHORITY_NOT_FOUND"),
    })
    public ResponseEntity<StudyInfoResponseDto> create(@Valid @RequestBody StudyCreateRequestDto dto) {
        return ResponseEntity.ok(studyService.create(dto));
    }

    @PutMapping("/{studyId}")
    @ApiOperation(value = "스터디 수정", notes = "<strong>받은 스터디 정보</strong>를 사용해서 스터디를 수정한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "수정된 정보"),
        @ApiResponse(code = 400, message = "MEMBER_COUNT_OVER"),
        @ApiResponse(code = 401, message = "UNAUTHORIZED_CHANGE"),
        @ApiResponse(code = 404, message = "CITY_NOT_FOUND\nMEMBER_NOT_FOUND\nCLUB_NOT_FOUND\nSTUDY_NOT_FOUND\nMEMBER_STUDY_NOT_FOUND")
    })
    public ResponseEntity<StudyInfoResponseDto> update(@PathVariable("studyId") Long studyId,
        @Valid @RequestBody StudyUpdateRequestDto dto) {
        return ResponseEntity.ok(studyService.update(studyId, dto));
    }

    @DeleteMapping("/{studyId}")
    @ApiOperation(value = "스터디 삭제", notes = "<strong>받은 스터디 Id</strong>로 스터디 관련 정보(멤버 관계, 사진, 주제)를 삭제한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "삭제되었습니다."),
        @ApiResponse(code = 401, message = "UNAUTHORIZED_CHANGE"),
        @ApiResponse(code = 404, message = "MEMBER_NOT_FOUND\nSTUDY_NOT_FOUND\nMEMBER_STUDY_NOT_FOUND\nFILE_NOT_FOUND"),
    })
    public ResponseEntity<HttpStatus> delete(@PathVariable("studyId") Long studyId) {
        return ResponseEntity.ok(studyService.delete(studyId));
    }

    @DeleteMapping("/{studyId}/member")
    @ApiOperation(value = "스터디 탈퇴", notes = "<strong>받은 스터디 id</strong>로 스터디에서 탈퇴한다.")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("studyId") Long studyId) throws Exception {
        return ResponseEntity.ok(studyService.removeMe(studyId));
    }

//    @GetMapping
//    @ApiOperation(value = "모든 스터디 조회", notes = "(isPublic :True, isActive:True)를 만족하는 스터디들을 작성일 기준 내림차순으로 받는다")
//    public ResponseEntity<Page<StudyInfoResponseDto>> getAllStudy(@PageableDefault(size = 10) @SortDefault(sort = "createDate", direction= Sort.Direction.DESC) Pageable pageable) throws Exception {
//        return ResponseEntity.ok(studyService.getAllStudy(pageable));
//    }

//    @GetMapping("/recommend")
//    @ApiOperation(value = "모든 스터디 조회", notes = "추천 하는 스터디들을 리턴한다")
//    public ResponseEntity<Page<StudyInfoResponseDto>> getAllStudyWithRecommend(@SortDefault.SortDefaults({
////            @SortDefault(sort = "createDate", direction= Sort.Direction.DESC),
//            @SortDefault(sort = "period", direction = Sort.Direction.DESC),
//            @SortDefault(sort = "maxCount", direction = Sort.Direction.DESC)
//    }) @PageableDefault(size = 10) Pageable pageable) throws Exception {
//        return ResponseEntity.ok(studyService.getAllStudy(pageable));
//    }

    @GetMapping("/{studyId}")
    @ApiOperation(value = "스터디 상세정보 조회",
        notes = "<strong>받은 스터디 id</strong>로 해당 스터디 정보 + 수정을 위한 정보(사용자 클럽 리스트, 지역, 상태 리스트 등")
    @ApiResponses({
        @ApiResponse(code = 200, message = "스터디 상세 정보"),
        @ApiResponse(code = 404, message = "STUDY_NOT_FOUND\nDELETED_STUDY"),
    })
    public ResponseEntity<StudyInfoResponseDto> getOneStudy(@PathVariable("studyId") Long studyId) {
        return ResponseEntity.ok(studyService.getOneStudy(studyId));
    }

}
