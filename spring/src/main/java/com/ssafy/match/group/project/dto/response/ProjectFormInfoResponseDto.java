package com.ssafy.match.group.project.dto.response;

import com.ssafy.match.group.project.entity.ProjectApplicationForm;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProjectFormInfoResponseDto {

    @ApiModelProperty(example = "4")
    @ApiParam(value = "프로젝트 id")
    private Long projectId;
    @ApiModelProperty(example = "3")
    @ApiParam(value = "멤버 id")
    private Long memberId;
    @ApiModelProperty(example = "박범진")
    @ApiParam(value = "신청자 이름")
    private String name;
    @ApiModelProperty(example = "qjafd@naver.com")
    @ApiParam(value = "신청자 이메일")
    private String email;
    @ApiModelProperty(example = "개발자")
    @ApiParam(value = "신청 역할")
    private String role;
    @ApiModelProperty(example = "설명란")
    @ApiParam(value = "자기 소개")
    private String bio;

    public static ProjectFormInfoResponseDto from(ProjectApplicationForm form){
        return ProjectFormInfoResponseDto.builder()
            .projectId(form.getCompositeMemberProject().getProject().getId())
            .memberId(form.getCompositeMemberProject().getMember().getId())
            .name(form.getName())
            .email(form.getCompositeMemberProject().getMember().getEmail())
            .role(form.getRole())
            .bio(form.getBio())
            .build();
    }
}
