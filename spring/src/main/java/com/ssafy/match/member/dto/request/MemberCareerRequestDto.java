package com.ssafy.match.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.match.member.entity.Career;
import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCareerRequestDto {
    @ApiModelProperty(name = "company", example = "Google")
    @ApiParam(value = "회사", required = true)
    @NotEmpty
    private String company;

    @ApiModelProperty(name = "role", example = "개발자")
    @ApiParam(value = "역할", required = true)
    @NotEmpty
    private String role;

    @ApiModelProperty(name = "start_date", example = "2018-05-01 00:00:00")
    @ApiParam(value = "시작일", required = true)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
//    @NotEmpty
    private LocalDateTime start_date;

    @ApiModelProperty(name = "end_date", example = "2018-05-01 00:00:00")
    @ApiParam(value = "종료일", required = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime end_date;

    @ApiModelProperty(name = "is_incumbent", example = "false")
    @ApiParam(value = "재직중", required = true)
//    @NotEmpty
    private Boolean is_incumbent;

    @ApiModelProperty(name = "description", example = "Google의 핵심 인재")
    @ApiParam(value = "설명", required = false)
    private String description;

    public Career toCareer(Member member) {
        return Career.builder()
                .company(company)
                .role(role)
                .start_date(start_date)
                .end_date((is_incumbent == Boolean.TRUE) ? null : end_date)
                .is_incumbent(is_incumbent)
                .description(description)
                .member(member)
                .build();
    }
}