package com.ssafy.match.member.controller;

import com.ssafy.match.member.dto.*;
import com.ssafy.match.member.dto.request.*;
import com.ssafy.match.member.dto.response.*;
import com.ssafy.match.member.service.MemberService;
import com.ssafy.match.util.SecurityUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
//@CrossOrigin("*")
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/check/password")
    @ApiOperation(value = "비밀번호 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Boolean> checkPassword(@RequestBody MemberCheckPasswordDto memberCheckPasswordDto) {
        return ResponseEntity.ok(memberService.checkPassword(memberCheckPasswordDto));
    }

    @PutMapping("/password")
    @ApiOperation(value = "비밀번호 변경")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<HttpStatus> updatePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(memberService.updatePassword(changePasswordDto));
    }

    @GetMapping("/mypage/{email}")
    @ApiOperation(value = "다른사람의 마이페이지")
    public ResponseEntity<MypageResponseDto> getMyPage(@PathVariable("email") String email) {
        return ResponseEntity.ok(memberService.getMyPage(email));
    }

    @GetMapping("/mypage")
    @ApiOperation(value = "마이 페이지")
    public ResponseEntity<MypageResponseDto> getMyPage() {
        return ResponseEntity.ok(memberService.getMyPage());
    }

    @GetMapping("/basicinfo")
    @ApiOperation(value = "내 기본정보 Get")
    public ResponseEntity<MemberBasicinfoResponseDto> getMemberBasicinfo() {
        return ResponseEntity.ok(memberService.getMemberBasicinfo());
    }

    @PutMapping("/basicinfo")
    @ApiOperation(value = "내 기본정보 Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<HttpStatus> updateMemberBasicinfo(@RequestBody @Valid MemberBasicInfoRequestDto memberBasicinfoRequestDto) throws Exception {
        return ResponseEntity.ok(memberService.updateMemberBasicInfo(memberBasicinfoRequestDto));
    }

    @GetMapping("/skills")
    @ApiOperation(value = "내 직무/기술스택 Get")
    public ResponseEntity<MemberSkillResponseDto> getMemberSkills() {
        return ResponseEntity.ok(memberService.getMemberSkills());
    }

    @PutMapping("/skills")
    @ApiOperation(value = "내 직무/기술스택 Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<HttpStatus> updateMemberSkills(@RequestBody @Valid MemberSkillRequestDto memberSkillRequestDto) throws Exception {
        return ResponseEntity.ok(memberService.updateMemberSkills(memberSkillRequestDto));
    }

    @GetMapping("/careerall")
    @ApiOperation(value = "내 커리어(경력,자격증,교육) Get")
    public ResponseEntity<MemberCareerAllResponseDto> getMemberCareerList() {
        return ResponseEntity.ok(memberService.getMemberCareerAll());
    }

    @GetMapping("/career/{id}")
    @ApiOperation(value = "id를 기반으로 해당 경력 Get")
    public ResponseEntity<CareerResponseDto> getMemberCareer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.getMemberCareer(id));
    }

    @PostMapping("/career")
    @ApiOperation(value = "내 경력 Create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<HttpStatus> createMemberCareer(@RequestBody @Valid MemberCareerRequestDto memberCareerRequestDto) throws Exception {
        return ResponseEntity.ok(memberService.createMemberCareer(memberCareerRequestDto));
    }

    @DeleteMapping("/career/{id}")
    @ApiOperation(value = "id를 기반으로 해당 경력 Delete")
    public ResponseEntity<HttpStatus> deleteMemberCareer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.deleteMemberCareer(id));
    }

    @GetMapping("/certification/{id}")
    @ApiOperation(value = "id를 기반으로 해당 자격증 Get")
    public ResponseEntity<CertificationResponseDto> getMemberCertification(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.getMemberCertification(id));
    }

    @DeleteMapping("/certification/{id}")
    @ApiOperation(value = "id를 기반으로 해당 자격증 Delete")
    public ResponseEntity<HttpStatus> deleteMemberCertification(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.deleteMemberCertification(id));
    }

    @PostMapping("/certification")
    @ApiOperation(value = "내 자격증 Create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<HttpStatus> createMemberCertification(@RequestBody @Valid MemberCertificationRequestDto memberCertificationRequestDto) throws Exception {
        return ResponseEntity.ok(memberService.createMemberCertification(memberCertificationRequestDto));
    }

    @GetMapping("/education/{id}")
    @ApiOperation(value = "id를 기반으로 해당 교육 Get")
    public ResponseEntity<EducationResponseDto> getMemberEducation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.getMemberEducation(id));
    }

    @DeleteMapping("/education/{id}")
    @ApiOperation(value = "id를 기반으로 해당 교육 Delete")
    public ResponseEntity<HttpStatus> deleteMemberEducation(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.deleteMemberEducation(id));
    }

    @PostMapping("/education")
    @ApiOperation(value = "내 교육 Create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<HttpStatus> createMemberCertification(@RequestBody @Valid MemberEducationRequestDto memberEducationRequestDto) throws Exception {
        return ResponseEntity.ok(memberService.createMemberEducation(memberEducationRequestDto));
    }

    @GetMapping("/portfolio")
    @ApiOperation(value = "내 포트폴리오 Get")
    public ResponseEntity<MemberPortfolioResponseDto> getMemberPortfolio() {
        return ResponseEntity.ok(memberService.getMemberPortfolio());
    }

    @PutMapping("/portfolio")
    @ApiOperation(value = "내 포트폴리오 Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공")
    })
    public ResponseEntity<HttpStatus> updateMemberPortfolio(@RequestBody @Valid MemberPortfolioRequestDto memberPortfolioRequestDto) throws Exception {
        return ResponseEntity.ok(memberService.updateMemberPortfolio(memberPortfolioRequestDto));
    }

    @PutMapping
    @ApiOperation(value = "내 계정 정보 Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "성공"),
            @ApiResponse(code = 406, message = "데이터 에러"),
    })
    public ResponseEntity<?> updateMember(@RequestBody @Valid MemberUpdateRequestDto memberUpdateRequestDto) throws Exception {
        MemberUpdateResponseDto memberUpdateResponseDto = memberService.updateMyInfo(memberUpdateRequestDto);
        if (memberUpdateResponseDto.getId().equals(SecurityUtil.getCurrentMemberId())) {
            return new ResponseEntity<String>("수정사항이 성공적으로 반영되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<String>("수정이 실패했습니다!", HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity<?> deleteMember() {
        memberService.deleteMember();
        return new ResponseEntity<String>("회원탈퇴가 정상적으로 이루어졌습니다.", HttpStatus.OK);
    }
}
