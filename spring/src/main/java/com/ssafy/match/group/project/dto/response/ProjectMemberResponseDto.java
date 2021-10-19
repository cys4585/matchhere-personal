package com.ssafy.match.group.project.dto.response;

import com.ssafy.match.group.project.entity.MemberProject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberResponseDto {

    @ApiModelProperty(example = "3")
    @ApiParam(value = "멤버 ID")
    private Long id;

    @ApiModelProperty(example = "박범진")
    @ApiParam(value = "멤버 이름")
    private String name;

    @ApiModelProperty(example = "qjawlsqjacks@gmail.com")
    @ApiParam(value = "멤버 닉네임")
    private String email;

    @ApiModelProperty(example = "팀원")
    @ApiParam(value = "권한")
    private String authority;

    @ApiModelProperty(example = "개발자")
    @ApiParam(value = "역할")
    private String role;


    public static ProjectMemberResponseDto from(MemberProject mp) {
        return ProjectMemberResponseDto.builder()
            .id(mp.getCompositeMemberProject().getMember().getId())
            .name(mp.getCompositeMemberProject().getMember().getName())
            .email(mp.getCompositeMemberProject().getMember().getEmail())
            .authority(mp.getAuthority().toString())
            .role(mp.getRole())
            .build();
    }
}
