package com.ssafy.match.member.controller;

import com.ssafy.match.member.dto.*;
import com.ssafy.match.member.dto.request.MemberBasicinfoRequestDto;
import com.ssafy.match.member.dto.response.MemberBasicinfoResponseDto;
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
    @ApiOperation(value = "마이 페이지")
    public ResponseEntity<MypageResponseDto> getMyPage(@PathVariable("email") String email) {
        return ResponseEntity.ok(memberService.getMyPage(email));
    }

    @GetMapping("/mypage")
    @ApiOperation(value = "마이 페이지")
    public ResponseEntity<MypageResponseDto> getMyPage() {
        return ResponseEntity.ok(memberService.getMyPage());
    }

    @PutMapping("/basicinfo")
    @ApiOperation(value = "내 기본정보 Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "성공"),
            @ApiResponse(code = 406, message = "데이터 에러"),
    })
    public ResponseEntity<HttpStatus> updateMemberBasicinfo(@RequestBody @Valid MemberBasicinfoRequestDto memberBasicinfoRequestDto) throws Exception {
        return ResponseEntity.ok(memberService.updateMemberBasicinfo(memberBasicinfoRequestDto));
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
