package com.ssafy.match.group.projectboard.article.dto;

import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectArticleInfoResponseDto {

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

    @ApiModelProperty(example = "[\"태그\", \"질문\"]")
    private List<String> tags;

    public void setContent(String content) {
        this.content = content;
    }

    public static ProjectArticleInfoResponseDto of(ProjectArticle projectArticle, List<String> tags) {
        return ProjectArticleInfoResponseDto.builder()
                .articleId(projectArticle.getId())
                .title(projectArticle.getTitle())
                .projectBoard(projectArticle.getProjectBoard().getName())
                .createdMember(projectArticle.getMember().getNickname())
                .createdDate(projectArticle.getCreateDate())
                .modifiedDate(projectArticle.getModifiedDate())
                .viewCount(projectArticle.getViewCount())
                .tags(tags)
                .build();
    }

}
