package com.ssafy.match.group.project.dto.response;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public interface ProjectFormSimpleInfoResponseDto {

    @ApiModelProperty(example = "4")
    @ApiParam(value = "프로젝트 id")
    Long getProjectId();
    @ApiModelProperty(example = "3")
    @ApiParam(value = "멤버 id")
    Long getMemberId();
    @ApiModelProperty(example = "박범진")
    @ApiParam(value = "신청자 이름")
    String getName();
    @ApiModelProperty(example = "개발자")
    @ApiParam(value = "신청 역할")
    String getRole();

}
