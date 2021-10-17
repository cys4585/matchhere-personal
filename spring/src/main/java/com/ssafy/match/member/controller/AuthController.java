package com.ssafy.match.member.controller;

import com.ssafy.match.member.dto.*;
import com.ssafy.match.member.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<MemberResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) throws Exception {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/cert/email/{email}")
    @ApiOperation(value = "authcode 이메일 발송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Boolean> certEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(authService.certEmail(email));
    }

    @PostMapping("/cert/authcode")
    public ResponseEntity<?> emailAuthCode(@RequestBody EmailCertRequestDto emailCertRequestDto) {
        Long response = authService.emailAuthCode(emailCertRequestDto);
        if (response == -1L) {
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

//    @GetMapping("/cert/findpassword/{email}")
//    @ApiOperation(value = "비밀번호 찾기를 위한 authcode 이메일 발송")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "성공"),
//    })
//    public ResponseEntity<Boolean> certEmail(@PathVariable("email") String email) {
//        return ResponseEntity.ok(authService.certEmail(email));
//    }

    @GetMapping("/check/nickname/{nickname}")
    @ApiOperation(value = "닉네임 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<Boolean> checkNickname(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(authService.checkNickname(nickname));
    }
}