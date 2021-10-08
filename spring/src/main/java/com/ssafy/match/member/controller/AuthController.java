package com.ssafy.match.member.controller;

import com.ssafy.match.member.dto.*;
import com.ssafy.match.member.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody @Valid MemberRequestDto memberRequestDto) throws Exception {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<?> login(@RequestBody MemberRequestDto memberRequestDto) throws Exception {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/check/email/{email}")
    @ApiOperation(value = "이메일 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Boolean> checkEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(authService.checkEmail(email));
    }

    @GetMapping("/check/nickname/{nickname}")
    @ApiOperation(value = "닉네임 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Boolean> checkNickname(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(authService.checkNickname(nickname));
    }
}