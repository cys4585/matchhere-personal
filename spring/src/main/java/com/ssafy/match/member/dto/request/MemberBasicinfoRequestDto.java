package com.ssafy.match.member.dto.request;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberBasicinfoRequestDto {

    @ApiModelProperty(name = "coverpic_uuid", example = "uuid")
    @ApiParam(value = "사용자 커버 사진 uuid", required = false)
    private String coverpic_uuid;

    @ApiModelProperty(name = "nickname", example = "별명")
    @ApiParam(value = "별명", required = true)
    private String nickname;

    @ApiModelProperty(name = "name", example = "문일민")
    @ApiParam(value = "이름", required = true)
    private String name;

    @ApiModelProperty(name = "city", example = "부산")
    @ApiParam(value = "지역", required = true)
    private String city;

    @ApiModelProperty(name = "bio", example = "let me introduce")
    @ApiParam(value = "자기소개", required = false)
    private String bio;

}
