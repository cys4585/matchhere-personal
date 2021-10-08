package com.ssafy.match.group.studyboard.comment.service;

import com.ssafy.match.group.studyboard.comment.dto.StudyArticleCommentRequestDto;
import com.ssafy.match.group.studyboard.comment.dto.StudyArticleCommentResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;

public interface StudyCommentService {

    Long create(Long articleId, Long parentId, StudyArticleCommentRequestDto dto);

    List<StudyArticleCommentResponseDto> allComment(Long articleId);

    HttpStatus update(Long commentId, StudyArticleCommentRequestDto dto) throws Exception;

    HttpStatus delete(Long commentId) throws Exception;
}
