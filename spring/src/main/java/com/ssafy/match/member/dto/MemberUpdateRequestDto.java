package com.ssafy.match.member.dto;


import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateRequestDto {

    @ApiModelProperty(name = "name", example = "문일민")
    @ApiParam(value = "이름", required = true)
    private String name;

    @ApiModelProperty(name = "nickname", example = "별명")
    @ApiParam(value = "별명", required = true)
    private String nickname;

//    @ApiModelProperty(name = "tel", example = "010-1234-4567")
//    @ApiParam(value = "전화번호", required = false)
//    private String tel;

    @ApiModelProperty(name = "bio", example = "let me introduce")
    @ApiParam(value = "자기소개", required = false)
    private String bio;

    @ApiModelProperty(name = "city", example = "부산")
    @ApiParam(value = "도시", required = false)
    private String city;

    @ApiModelProperty(name = "position", example = "개발자")
    @ApiParam(value = "포지션", required = false)
    private String position;

    @ApiModelProperty(name = "cover_pic", example = "uuid")
    @ApiParam(value = "사용자 커버 사진 uuid", required = false)
    private String cover_pic;

    @ApiModelProperty(name = "poltfolio_uuid", example = "uuid")
    @ApiParam(value = "포트폴리오 uuid", required = false)
    private String portfolio_uuid;

    @ApiModelProperty(name = "portfolio_uri", example = "https://naver.com")
    @ApiParam(value = "포트폴리오 uri", required = false)
    private String portfolio_uri;

    @ApiModelProperty(name = "addTechList", example = "[{\"python\":\"중\"}, {\"java\":\"상\"}]")
    @ApiParam(value = "기술스택 리스트(add)", required = false)
    private List<HashMap<String,String>> addTechList;

    @ApiModelProperty(name = "delTechList", example = "[{\"python\":\"중\"}, {\"java\":\"상\"}]")
    @ApiParam(value = "기술스택 리스트(del)", required = false)
    private List<HashMap<String,String>> delTechList;

    @ApiModelProperty(name = "snsHashMap", example = "{\"github\":\"github id\",\"twitter\":\"twitter id\"}")
    @ApiParam(value = "sns 종류/계정", required = false)
    private HashMap<String, String> snsHashMap;

    @ApiModelProperty(name = "DelDpositionList", example = "[\"frontend\",\"devops\"]")
    @ApiParam(value = "세부 포지션(삭제)", required = false)
    private List<String> DelDpositionList;

    @ApiModelProperty(name = "AddDpositionList", example = "[\"frontend\",\"devops\"]")
    @ApiParam(value = "세부 포지션(추가)", required = false)
    private List<String> AddDpositionList;

    public Member toMember() {
        return Member.builder()
                .name(name)
                .nickname(nickname)
//                .tel(tel)
                .bio(bio)
                .city(city)
                .position(position)
                .portfolio_uri(portfolio_uri)
                .build();
    }

}