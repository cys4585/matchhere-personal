package com.ssafy.match.group.studyboard.comment.service;

import com.ssafy.match.group.studyboard.article.entity.StudyArticle;
import com.ssafy.match.group.studyboard.article.repository.StudyArticleRepository;
import com.ssafy.match.group.studyboard.comment.dto.StudyArticleCommentRequestDto;
import com.ssafy.match.group.studyboard.comment.dto.StudyArticleCommentResponseDto;
import com.ssafy.match.group.studyboard.comment.entity.StudyArticleComment;
import com.ssafy.match.group.studyboard.comment.repository.StudyArticleCommentRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class StudyCommentServiceImpl implements StudyCommentService{

    private final MemberRepository memberRepository;
    private final StudyArticleRepository studyArticleRepository;
    private final StudyArticleCommentRepository studyArticleCommentRepository;

    @Transactional
    public Long create(Long articleId, Long parentId, StudyArticleCommentRequestDto dto){
        if(parentId > 0) {
            StudyArticleComment parent = studyArticleCommentRepository.findById(parentId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 부모 댓글입니다."));
            parent.addReplyCount();
        }

        StudyArticleComment studyArticleComment = StudyArticleComment.builder()
            .member(findMember(SecurityUtil.getCurrentMemberId()))
            .studyArticle(findArticle(articleId))
            .parentId(parentId)
            .studyArticleCommentRequestDto(dto)
            .build();

        studyArticleCommentRepository.save(studyArticleComment);

        if(parentId == 0){
            studyArticleComment.setParentId(studyArticleComment.getId());
        }

        return studyArticleComment.getId();
    }

    public List<StudyArticleCommentResponseDto> allComment(Long articleId){
        List<StudyArticleComment> studyArticleComments = studyArticleCommentRepository.allComment(findArticle(articleId));
        List<StudyArticleCommentResponseDto> result = new ArrayList<>();

        for (StudyArticleComment sac: studyArticleComments) {
            result.add(StudyArticleCommentResponseDto.builder()
                .id(sac.getId())
                .content(sac.getContent())
                .nickname(sac.getMember().getNickname())
                .isModified(sac.getIsModified())
                .isDeleted(sac.getIsDeleted())
                .parentId(sac.getParentId())
                .depth(sac.getDepth())
                .replyCount(sac.getReplyCount())
                .build());
        }

        return result;
    }

    @Transactional
    public HttpStatus update(Long commentId, StudyArticleCommentRequestDto dto) throws Exception {
        StudyArticleComment comment = studyArticleCommentRepository.findById(commentId)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 댓글입니다."));

        if(!comment.getMember().getId().equals(SecurityUtil.getCurrentMemberId())){
            throw new Exception("권한이 없습니다.");
        }

        comment.setContent(dto.getContent());
        comment.setIsModified(true);

        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus delete(Long commentId) throws Exception {
        StudyArticleComment comment = studyArticleCommentRepository.findById(commentId)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 댓글입니다."));

        if(!comment.getMember().getId().equals(SecurityUtil.getCurrentMemberId())){
            throw new Exception("권한이 없습니다.");
        }

        comment.setIsDeleted(true);
        return HttpStatus.OK;
    }
    public Member findMember(Long memberId){
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 멤버입니다."));
    }

    public StudyArticle findArticle(Long articleId){
        return studyArticleRepository.findById(articleId)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 게시글입니다."));
    }

}
