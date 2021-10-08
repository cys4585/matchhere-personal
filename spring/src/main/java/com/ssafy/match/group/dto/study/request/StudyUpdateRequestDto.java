package com.ssafy.match.group.dto.study.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "스터디 업데이트 정보", description = "추가, 제거 기술 스택 리스트, 프로젝트명, 스케쥴, 기간, 최대인원...을 가진 Request Dto Class")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyUpdateRequestDto {

    @ApiModelProperty(name = "techstack", example = "[\"java\", \"python\"]")
    @ApiParam(value = "추가된 기술 스택 리스트", required = true)
    private List<String> addStackList;

    @ApiModelProperty(name = "techstack", example = "[\"java\", \"python\"]")
    @ApiParam(value = "제거된 기술 스택 리스트", required = true)
    private List<String> removeStackList;

    @ApiModelProperty(name = "name", example = "매칭 스터디")
    @ApiParam(value = "스터디명", required = true)
    private String name;

    @ApiModelProperty(name = "schedule", example = "매주 화, 수 6시")
    @ApiParam(value = "스터디 작업 시간", required = true)
    private String schedule;

    @ApiModelProperty(name = "period", example = "7")
    @ApiParam(value = "스터디 기간(주 단위)", required = true)
    private int period;

    @ApiModelProperty(name = "maxCount", example = "3")
    @ApiParam(value = "스터디 제한 인원", required = true)
    private int maxCount;

    @ApiModelProperty(name = "city", example = "구미")
    @ApiParam(value = "활동지역", required = true)
    private String city;

    @ApiModelProperty(name = "status", example = "모집, 진행, 종료")
    @ApiParam(value = "스터디 상태", required = true)
    private String status;

    @ApiModelProperty(name = "isPublic", example = "false")
    @ApiParam(value = "공개 비공개", required = true)
    private Boolean isPublic;

    @ApiModelProperty(name = "isParticipate", example = "false")
    @ApiParam(value = "참여 가능 여부", required = true)
    private Boolean isParticipate;

    @ApiModelProperty(name = "clubId", example = "3")
    @ApiParam(value = "소속된 클럽 id")
    private Long clubId;

    @ApiModelProperty(name = "uuid", example = "3fads23-fdfd13-23d2")
    @ApiParam(value = "사진 고유 uuid")
    private String uuid;

    @ApiModelProperty(name = "bio", example = "알고리즘입니다.")
    @ApiParam(value = "스터디 소개", required = true)
    private String bio;

    @ApiModelProperty(name = "hostId", example = "5")
    @ApiParam(value = "스터디장 Id", required = true)
    private Long hostId;

}
