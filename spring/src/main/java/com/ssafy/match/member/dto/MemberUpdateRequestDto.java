package com.ssafy.match.member.dto;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateRequestDto {

    @ApiModelProperty(name = "email", example = "my_email@gmail.com")
    @ApiParam(value = "이메일", required = true)
    private String email;

    @ApiModelProperty(name = "password", example = "mypassword")
    @ApiParam(value = "비밀번호", required = true)
    private String password;

    @ApiModelProperty(name = "name", example = "문일민")
    @ApiParam(value = "이름", required = true)
    private String name;

    @ApiModelProperty(name = "nickname", example = "별명")
    @ApiParam(value = "별명", required = true)
    private String nickname;

    @ApiModelProperty(name = "tel", example = "010-1234-4567")
    @ApiParam(value = "전화번호", required = false)
    private String tel;

    @ApiModelProperty(name = "bio", example = "let me introduce")
    @ApiParam(value = "자기소개", required = false)
    private String bio;

    @ApiModelProperty(name = "city", example = "부산")
    @ApiParam(value = "도시", required = false)
    private String city;

    @ApiModelProperty(name = "position", example = "개발자")
    @ApiParam(value = "역할", required = false)
    private String position;

    @ApiModelProperty(name = "cover_pic", example = "uuid")
    @ApiParam(value = "사용자 커버 사진", required = false)
//    private DBFile cover_pic;
    private String cover_pic;

    @ApiModelProperty(name = "poltfolio_uuid", example = "uuid")
    @ApiParam(value = "포트폴리오", required = false)
    private String portfolio_uuid;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    @ApiParam(value = "포트폴리오 uri", required = false)
    private String portfolio_uri;

    @ApiModelProperty(name = "expDelTechList", example = "[\"python\",\"java\"]")
    @ApiParam(value = "experienced 기술 리스트(del)", required = false)
    private List<String> expDelTechList;

    @ApiModelProperty(name = "expAddTechList", example = "[\"python\",\"java\"]")
    @ApiParam(value = "experienced 기술 리스트(add)", required = false)
    private List<String> expAddTechList;

    @ApiModelProperty(name = "beginDelTechList", example = "[\"python\",\"java\"]")
    @ApiParam(value = "beginner 기술 리스트(del)", required = false)
    private List<String> beginDelTechList;

    @ApiModelProperty(name = "beginAddTechList", example = "[\"python\",\"java\"]")
    @ApiParam(value = "beginner 기술 리스트(add)", required = false)
    private List<String> beginAddTechList;

    @ApiModelProperty(name = "snsHashMap", example = "{\"github\":\"github id\",\"twitter\":\"twitter id\"}")
    @ApiParam(value = "sns 종류/계정", required = false)
    private HashMap<String, String> snsHashMap;

    @ApiModelProperty(name = "dpositionDelList", example = "[1,2]")
    @ApiParam(value = "세부 포지션(삭제)", required = false)
    private List<Integer> dpositionDelList;

    @ApiModelProperty(name = "dpositionAddList", example = "[\"frontend\",\"devops\"]")
    @ApiParam(value = "세부 포지션(추가)", required = false)
    private List<String> dpositionAddList;

//    public Member toMember(PasswordEncoder passwordEncoder) {
//        return Member.builder()
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .nickname(nickname)
//                .tel(tel)
//                .bio(bio)
//                .city(city)
//                .position(position)
//                .portfolio_uri(portfolio_uri)
//                .build();
//    }

}
