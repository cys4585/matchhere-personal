package com.ssafy.match.group.study.dto.response;

import com.ssafy.match.group.study.entity.StudyTopic;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StudyTopicResponseDto {

    @ApiModelProperty(example = "OS")
    @ApiParam(value = "주제명")
    private String name;

    @ApiModelProperty(example = "상")
    @ApiParam(value = "수준")
    private String level;

    public static StudyTopicResponseDto from(StudyTopic studyTopic){
        return StudyTopicResponseDto.builder()
            .name(studyTopic.getName())
            .level(studyTopic.getLevel().toString())
            .build();
    }

}
