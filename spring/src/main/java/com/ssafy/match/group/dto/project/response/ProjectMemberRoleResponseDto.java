package com.ssafy.match.group.dto.project.response;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProjectMemberRoleResponseDto {

    @ApiModelProperty(name = "id", example = "3")
    @ApiParam(value = "멤버 ID", required = true)
    private Long id;

    @ApiModelProperty(name = "name", example = "박범진")
    @ApiParam(value = "멤버 이름", required = true)
    private String name;

    @ApiModelProperty(name = "nickname", example = "히므드러")
    @ApiParam(value = "멤버 닉네임", required = true)
    private String nickname;

    @Builder
    public ProjectMemberRoleResponseDto(Long id, String name, String nickname) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }
}
