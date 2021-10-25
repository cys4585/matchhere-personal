package com.ssafy.match.group.study.dto.response;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public interface StudyFormSimpleInfoResponseDto {

    @ApiModelProperty(example = "4")
    @ApiParam(value = "스터디 id")
    Long getStudyId();

    @ApiModelProperty(example = "3")
    @ApiParam(value = "멤버 id")
    Long getMemberId();

    @ApiModelProperty(example = "박범진")
    @ApiParam(value = "신청자 이름")
    String getName();

}
