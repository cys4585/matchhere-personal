package com.ssafy.match.group.projectboard.article.dto;

import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectArticleSimpleInfoResponseDto {

    @ApiModelProperty(example = "3")
    private Long articleId;
    @ApiModelProperty(example = "게시물 제목")
    private String title;
    @ApiModelProperty(name = "projectBoard", example = "일정게시판")
    private String projectBoard;
    @ApiModelProperty(example = "박범진")
    private String createdMember;
    @ApiModelProperty(name = "createdDate", example = "2021-10-01T13:09:53.46748")
    private LocalDateTime createdDate;
    @ApiModelProperty(name = "modifiedDate", example = "2021-10-01T13:09:53.46748")
    private LocalDateTime modifiedDate;
    @ApiModelProperty(name = "viewCount", example = "2")
    private Integer viewCount;

    public static ProjectArticleSimpleInfoResponseDto from(ProjectArticle projectArticle) {
        return ProjectArticleSimpleInfoResponseDto.builder()
                .articleId(projectArticle.getId())
                .title(projectArticle.getTitle())
                .projectBoard(projectArticle.getProjectBoard().getName())
                .createdMember(projectArticle.getMember().getName())
                .createdDate(projectArticle.getCreateDate())
                .modifiedDate(projectArticle.getModifiedDate())
                .viewCount(projectArticle.getViewCount())
                .build();
    }

}
