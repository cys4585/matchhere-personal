package com.ssafy.match.group.projectboard.article.dto;

import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class ProjectArticleInfoDto {
    @ApiModelProperty(name = "articleId", example = "3")
    private Long articleId;

    @ApiModelProperty(name = "title", example = "게시물 제목")
    private String title;

    @ApiModelProperty(name = "content", example = "내용")
    private String content;

    @ApiModelProperty(name = "projectBoard", example = "일정게시판")
    private String projectBoard;

    @ApiModelProperty(name = "createdMember", example = "nickname")
    private String createdMember;

    @ApiModelProperty(name = "createdDate", example = "2021-10-01T13:09:53.46748")
    private LocalDateTime createdDate;

    @ApiModelProperty(name = "modifiedDate", example = "2021-10-01T13:09:53.46748")
    private LocalDateTime modifiedDate;

    @ApiModelProperty(name = "viewCount", example = "2")
    private Integer viewCount;

    public static ProjectArticleInfoDto of(ProjectArticle projectArticle) {
        return ProjectArticleInfoDto.builder()
                .articleId(projectArticle.getId())
                .title(projectArticle.getTitle())
                .projectBoard(projectArticle.getProjectBoard().getName())
                .createdMember(projectArticle.getMember().getNickname())
                .createdDate(projectArticle.getCreateDate())
                .modifiedDate(projectArticle.getModifiedDate())
                .viewCount(projectArticle.getViewCount())
                .build();
    }

    @Builder
    public ProjectArticleInfoDto(Long articleId, String title, String projectBoard, String createdMember, LocalDateTime createdDate, LocalDateTime modifiedDate, Integer viewCount) {
        this.articleId = articleId;
        this.title = title;
        this.projectBoard = projectBoard;
        this.createdMember = createdMember;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.viewCount = viewCount;

    }
}
