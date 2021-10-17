package com.ssafy.match.member.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.match.common.annotation.Enum;
import com.ssafy.match.common.entity.City;
import com.ssafy.match.common.entity.State;
import com.ssafy.match.member.entity.Career;
import com.ssafy.match.member.entity.Education;
import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEducationRequestDto {
    @ApiModelProperty(name = "institution", example = "서울대학교")
    @ApiParam(value = "학교/소속", required = true)
    @NotEmpty
    private String institution;

    @ApiModelProperty(name = "degree", example = "개발자")
    @ApiParam(value = "학위", required = true)
    @NotEmpty
    private String degree;

    @ApiModelProperty(name = "major", example = "컴퓨터과학과")
    @ApiParam(value = "전공", required = false)
    private String major;

    @ApiModelProperty(name = "start_date", example = "2018-05-01")
    @ApiParam(value = "시작일", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate start_date;

    @ApiModelProperty(name = "end_date", example = "2018-05-01")
    @ApiParam(value = "종료일", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate end_date;

    @ApiModelProperty(name = "state", example = "졸업")
    @ApiParam(value = "상태", required = true)
    @Enum(enumClass = State.class, ignoreCase = false)
    private String state;

    @ApiModelProperty(name = "description", example = "서울대학교가 배출한 최고의 인재 ooo")
    @ApiParam(value = "설명", required = false)
    private String description;

    public Education toCareer(Member member) {
        return Education.builder()
                .institution(institution)
                .degree(degree)
                .major(major)
                .start_date(start_date)
                .end_date(end_date)
                .state(state)
                .description(description)
                .member(member)
                .build();
    }
}