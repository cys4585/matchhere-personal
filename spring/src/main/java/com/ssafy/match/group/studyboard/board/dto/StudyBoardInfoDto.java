package com.ssafy.match.group.studyboard.board.dto;

import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class StudyBoardInfoDto {
    @ApiModelProperty(name="id", example = "3")
    private Integer id;

    @ApiModelProperty(name = "name", example = "일정게시판")
    private String name;

    public static StudyBoardInfoDto of(StudyBoard studyBoard) {
        return StudyBoardInfoDto.builder()
                .id(studyBoard.getId())
                .name(studyBoard.getName())
                .build();
    }

    @Builder
    public StudyBoardInfoDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
