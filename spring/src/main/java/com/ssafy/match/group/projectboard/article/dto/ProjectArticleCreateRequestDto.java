package com.ssafy.match.group.projectboard.article.dto;


import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectArticleCreateRequestDto {
//    @ApiModelProperty(name = "articleid", example = "3")
//    @ApiParam(value = "게시물 id", required = true)
//    private Integer articleId;

    @ApiModelProperty(name = "title", example = "게시물 제목")
    @ApiParam(value = "게시물 제목", required = true)
    private String title;

    @ApiModelProperty(name = "content", example = "내용~~~~")
    @ApiParam(value = "게시물내용")
    private String content;

    public ProjectArticle toProjectBoard(Member member, ProjectBoard projectBoard) {
        return ProjectArticle.builder()
                .title(title)
                .projectBoard(projectBoard)
                .viewCount(0)
                .member(member)
                .createDate(LocalDateTime.now())
//                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
