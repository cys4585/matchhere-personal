package com.ssafy.match.group.dto.study.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "스터디 생성 정보", description = "스터디명, 기술 스택 리스트, 스케쥴, 기간, 최대인원...을 가진 Request Dto Class")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyCreateRequestDto {

    @ApiModelProperty(name = "name", example = "매칭 스터디")
    private String name;

    @ApiModelProperty(name = "techList", example = "[\"java\", \"python\"]")
    private List<String> techList;

    @ApiModelProperty(name = "schedule", example = "매주 화, 수 6시")
    private String schedule;

    @ApiModelProperty(name = "period", example = "7")
    private int period;

    @ApiModelProperty(name = "maxCount", example = "3")
    private int maxCount;

    @ApiModelProperty(name = "city", example = "서울")
    private String city;

    @ApiModelProperty(name = "isPublic", example = "false")
    private Boolean isPublic;

    @ApiModelProperty(name = "club_id", example = "3")
    private Long clubId;

    @ApiModelProperty(name = "coverpic_uuid", example = "3fads23-fdfd13-23d2")
    private String coverpic_uuid;

    @ApiModelProperty(name = "bio", example = "알고리즘 스터디입니다.")
    private String bio;

}
