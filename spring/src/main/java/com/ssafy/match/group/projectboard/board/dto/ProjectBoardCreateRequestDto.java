package com.ssafy.match.group.projectboard.board.dto;


import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.group.entity.project.Project;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectBoardCreateRequestDto {

//    @ApiModelProperty(name = "projectId", example = "3")
//    @ApiParam(value = "스터디 id", required = true)
//    @NotEmpty
//    private Long projectId;

    @ApiModelProperty(name = "name", example = "일정게시판")
    @ApiParam(value = "게시판 이름", required = true)
    @NotEmpty
    private String name;

    public ProjectBoard toProjectBoard(Project project) {
        return ProjectBoard.builder()
                .name(name)
                .project(project)
                .build();
    }
}
