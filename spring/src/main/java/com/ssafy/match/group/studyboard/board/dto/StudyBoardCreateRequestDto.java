package com.ssafy.match.group.studyboard.board.dto;


import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyBoardCreateRequestDto {

//    @ApiModelProperty(name = "studyId", example = "3")
//    @ApiParam(value = "스터디 id", required = true)
//    @NotEmpty
//    private Long studyId;

    @ApiModelProperty(name = "name", example = "일정게시판")
    @ApiParam(value = "게시판 이름", required = true)
    @NotEmpty
    private String name;

    public StudyBoard toStudyBoard(Study study) {
        return StudyBoard.builder()
                .name(name)
                .study(study)
                .build();
    }
}
