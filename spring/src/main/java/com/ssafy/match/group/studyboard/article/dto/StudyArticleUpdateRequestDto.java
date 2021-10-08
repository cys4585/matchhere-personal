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
public class StudyArticleUpdateRequestDto {

    @ApiModelProperty(name = "title", example = "게시물 제목")
    @ApiParam(value = "게시물 제목", required = true)
    private String title;

    @ApiModelProperty(name = "content", example = "내용~~~~")
    @ApiParam(value = "게시물내용")
    private String content;

}
