package com.ssafy.match.group.studyboard.article.dto;

import com.ssafy.match.group.studyboard.article.entity.StudyArticle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyArticleListDto {
    @ApiModelProperty(name = "articleId", example = "3")
    private Long articleId;

    @ApiModelProperty(name = "title", example = "게시물 제목")
    private String title;

    @ApiModelProperty(name = "studyBoard", example = "일정게시판")
    private String studyBoard;

    @ApiModelProperty(name = "createdMember", example = "nickname")
    private String createdMember;

    @ApiModelProperty(name = "createdDate", example = "2021-10-01T13:09:53.46748")
    private LocalDateTime createdDate;

    @ApiModelProperty(name = "modifiedDate", example = "2021-10-01T13:09:53.46748")
    private LocalDateTime modifiedDate;

    @ApiModelProperty(name = "viewCount", example = "2")
    private Integer viewCount;

    public static StudyArticleListDto of(StudyArticle studyArticle) {
        return StudyArticleListDto.builder()
                .articleId(studyArticle.getId())
                .title(studyArticle.getTitle())
                .studyBoard(studyArticle.getStudyBoard().getName())
                .createdMember(studyArticle.getMember().getNickname())
                .createdDate(studyArticle.getCreateDate())
                .modifiedDate(studyArticle.getModifiedDate())
                .viewCount(studyArticle.getViewCount())
                .build();
    }

    @Builder
    public StudyArticleListDto(Long articleId, String title, String studyBoard, String createdMember, LocalDateTime createdDate, LocalDateTime modifiedDate, Integer viewCount) {
        this.articleId = articleId;
        this.title = title;
        this.studyBoard = studyBoard;
        this.createdMember = createdMember;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.viewCount = viewCount;

    }
}
