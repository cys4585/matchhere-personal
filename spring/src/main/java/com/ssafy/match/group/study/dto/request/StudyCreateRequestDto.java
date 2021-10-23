package com.ssafy.match.group.study.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.HashMap;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "스터디 생성 정보", description = "스터디명, 기술 스택 리스트, 스케쥴, 기간, 최대인원...을 가진 Request Dto Class")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyCreateRequestDto {

    @ApiModelProperty(example = "매칭 스터디")
    @ApiParam(value = "스터디명", required = true)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @ApiModelProperty(example = "스터디 준비 중")
    @ApiParam(value = "스터디 진행 상태", required = true)
    @NotBlank
    private String studyProgressState;

    @ApiModelProperty(example = "3fads23-fdfd13-23d2")
    @ApiParam(value = "사진 고유 uuid")
    private String uuid;

    @ApiModelProperty(example = "{\"OS\":\"중\", \"데이터베이스\":\"상\"}")
    @ApiParam(value = "주제, 난이도")
    private HashMap<String, String> topics;

    @ApiModelProperty(example = "주말 10시 ~ 18시 / 평일 논의")
    @ApiParam(value = "스터디 작업 시간")
    private String schedule;

    @ApiModelProperty(example = "2020-05-22")
    @ApiParam(value = "스터디 마감 예정일")
    private LocalDate period;

    @ApiModelProperty(example = "서울")
    @ApiParam(value = "지역", required = true)
    @NotBlank
    private String city;

    @ApiModelProperty(example = "3")
    @ApiParam(value = "소속된 클럽 id")
    private Long clubId;

    @ApiModelProperty(example = "Git 매칭 스터디입니다.")
    @ApiParam(value = "스터디 소개")
    private String bio;

    @ApiModelProperty(example = "전체 공개")
    @ApiParam(value = "공개 범위", required = true)
    @NotBlank
    private String publicScope;

    @ApiModelProperty(example = "모집 중")
    @ApiParam(value = "모집 상태", required = true)
    @NotBlank
    private String recruitmentState;

    @ApiModelProperty(name = "maxCount", example = "3")
    private int maxCount;
}
