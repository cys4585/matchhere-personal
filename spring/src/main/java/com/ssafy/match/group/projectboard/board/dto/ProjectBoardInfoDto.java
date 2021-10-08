package com.ssafy.match.group.projectboard.board.dto;

import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class ProjectBoardInfoDto {
    @ApiModelProperty(name="id", example = "3")
    private Integer id;

    @ApiModelProperty(name = "name", example = "일정게시판")
    private String name;

    public static ProjectBoardInfoDto of(ProjectBoard projectBoard) {
        return ProjectBoardInfoDto.builder()
                .id(projectBoard.getId())
                .name(projectBoard.getName())
                .build();
    }

    @Builder
    public ProjectBoardInfoDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
