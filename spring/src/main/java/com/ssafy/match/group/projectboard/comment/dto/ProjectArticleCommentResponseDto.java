package com.ssafy.match.group.projectboard.comment.dto;

import com.ssafy.match.group.projectboard.comment.entity.ProjectArticleComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProjectArticleCommentResponseDto {

    @ApiModelProperty(name = "id", notes = "댓글의 id")
    private Long id;
    @ApiModelProperty(notes = "댓글 작성자 이름")
    private String name;
    @ApiModelProperty(name = "content", notes = "댓글 내용")
    private String content;
    @ApiModelProperty(name = "isModified", notes = "수정 여부(수정되었으면 댓글에 수정됨 표시)")
    private Boolean isModified;
    @ApiModelProperty(name = "isDeleted", notes = "삭제 여부(삭제되었으면 삭제된 메세지입니다 표시)")
    private Boolean isDeleted;
    @ApiModelProperty(name = "parentId", notes = "부모의 id(id끼리 묶을 수 있음)")
    private Long parentId;
    @ApiModelProperty(name = "depth", notes = "댓글의 뎁스 표시(1이면 대댓글을 의미)")
    private int depth;
    @ApiModelProperty(name = "replyCount", notes = "대댓글 수")
    private int replyCount;

    public static ProjectArticleCommentResponseDto from(ProjectArticleComment pac){
        return ProjectArticleCommentResponseDto.builder()
            .id(pac.getId())
            .name(pac.getMember().getName())
            .content(pac.getContent())
            .isModified(pac.getIsModified())
            .isDeleted(pac.getIsDeleted())
            .parentId(pac.getParentId())
            .depth(pac.getDepth())
            .replyCount(pac.getReplyCount())
            .build();
    }
}
