package com.ssafy.match.group.projectboard.article.dto;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectArticleRequestDto {

    @ApiModelProperty(example = "게시글 제목")
    @ApiParam(value = "게시글 제목")
    private String title;

    @ApiModelProperty(example = "21")
    @ApiParam(value = "게시판 id")
    private Integer projectBoardId;

    @ApiModelProperty(example = "내용~~~~")
    @ApiParam(value = "게시글 내용")
    private String content;

    @ApiModelProperty(example = "[\"태그1\", \"태그2\"]")
    @ApiParam(value = "게시글 태그")
    private List<String> tags;

}
