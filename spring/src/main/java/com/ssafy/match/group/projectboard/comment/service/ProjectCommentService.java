package com.ssafy.match.group.projectboard.comment.service;

import com.ssafy.match.group.projectboard.comment.dto.ProjectArticleCommentRequestDto;
import com.ssafy.match.group.projectboard.comment.dto.ProjectArticleCommentResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;

public interface ProjectCommentService {

    Long create(Long articleId, Long parentId, ProjectArticleCommentRequestDto dto);

    List<ProjectArticleCommentResponseDto> allComment(Long articleId);

    HttpStatus update(Long commentId, ProjectArticleCommentRequestDto dto) throws Exception;

    HttpStatus delete(Long commentId) throws Exception;
}
