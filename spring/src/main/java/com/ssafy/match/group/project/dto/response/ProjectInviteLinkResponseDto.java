package com.ssafy.match.group.project.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectInviteLinkResponseDto {

    @ApiModelProperty(example = "http://localhost:8080/api/project/4")
    private String inviteLinkUri;

    public static ProjectInviteLinkResponseDto from(String uri){
        return new ProjectInviteLinkResponseDto(uri);
    }
}
