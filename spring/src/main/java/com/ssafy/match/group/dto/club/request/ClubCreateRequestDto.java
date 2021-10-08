package com.ssafy.match.group.dto.club.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "클럽 생성 정보", description = "프로젝트명, 주제, 인원, 공개 여부, 지역, 소개, 프로필 사진을 가진 Request Dto Class")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCreateRequestDto {

    @ApiModelProperty(name = "name", example = "매칭 클럽")
    @ApiParam(value = "클럽명", required = true)
    private String name;

    @ApiModelProperty(name = "topic", example = "React에 대한 심도 있는 고찰")
    @ApiParam(value = "주제", required = true)
    private String topic;

    @ApiModelProperty(name = "maxCount", example = "3")
    @ApiParam(value = "클럽 제한 인원", required = true)
    private int maxCount;

    @ApiModelProperty(name = "city", example = "서울")
    @ApiParam(value = "활동지역", required = true)
    private String city;

    @ApiModelProperty(name = "isPublic", example = "true")
    @ApiParam(value = "공개 비공개", required = true)
    private Boolean isPublic;

    @ApiModelProperty(name = "uuid", example = "97534f05-7e7f-425d-ac3e-aae8acee8a42")
    @ApiParam(value = "사진 고유 uuid")
    private String uuid;

    @ApiModelProperty(name = "bio", example = "React 클럽입니다.")
    @ApiParam(value = "클럽 소개", required = true)
    private String bio;

}
