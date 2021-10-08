package com.ssafy.match.group.studyboard.article.dto;


import com.ssafy.match.group.studyboard.article.entity.StudyArticle;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import com.ssafy.match.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyArticleCreateRequestDto {
//    @ApiModelProperty(name = "articleid", example = "3")
//    @ApiParam(value = "게시물 id", required = true)
//    private Integer articleId;

    @ApiModelProperty(name = "title", example = "게시물 제목")
    @ApiParam(value = "게시물 제목", required = true)
    private String title;

    @ApiModelProperty(name = "content", example = "내용~~~~")
    @ApiParam(value = "게시물내용")
    private String content;

    public StudyArticle toStudyBoard(Member member, StudyBoard studyBoard) {
        return StudyArticle.builder()
                .title(title)
                .studyBoard(studyBoard)
                .viewCount(0)
                .member(member)
                .createDate(LocalDateTime.now())
//                .modifiedDate(LocalDateTime.now())
                .build();
    }
}
