package com.ssafy.match.group.projectboard.comment.service;

import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import com.ssafy.match.group.projectboard.article.repository.ProjectArticleRepository;
import com.ssafy.match.group.projectboard.comment.dto.ProjectArticleCommentRequestDto;
import com.ssafy.match.group.projectboard.comment.dto.ProjectArticleCommentResponseDto;
import com.ssafy.match.group.projectboard.comment.entity.ProjectArticleComment;
import com.ssafy.match.group.projectboard.comment.repository.ProjectArticleCommentRepository;
import com.ssafy.match.group.projectboard.comment.service.ProjectCommentService;
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
public class ProjectCommentServiceImpl implements ProjectCommentService {

    private final MemberRepository memberRepository;
    private final ProjectArticleRepository projectArticleRepository;
    private final ProjectArticleCommentRepository projectArticleCommentRepository;

    @Transactional
    public Long create(Long articleId, Long parentId, ProjectArticleCommentRequestDto dto){
        if(parentId > 0) {
            ProjectArticleComment parent = projectArticleCommentRepository.findById(parentId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 부모 댓글입니다."));
            parent.addReplyCount();
        }

        ProjectArticleComment projectArticleComment = ProjectArticleComment.builder()
            .member(findMember(SecurityUtil.getCurrentMemberId()))
            .projectArticle(findArticle(articleId))
            .parentId(parentId)
            .projectArticleCommentRequestDto(dto)
            .build();

        projectArticleCommentRepository.save(projectArticleComment);

        if(parentId == 0){
            projectArticleComment.setParentId(projectArticleComment.getId());
        }

        return projectArticleComment.getId();
    }

    public List<ProjectArticleCommentResponseDto> allComment(Long articleId){
        List<ProjectArticleComment> projectArticleComments = projectArticleCommentRepository.allComment(findArticle(articleId));
        List<ProjectArticleCommentResponseDto> result = new ArrayList<>();

        for (ProjectArticleComment sac: projectArticleComments) {
            result.add(ProjectArticleCommentResponseDto.builder()
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
    public HttpStatus update(Long commentId, ProjectArticleCommentRequestDto dto) throws Exception {
        ProjectArticleComment comment = projectArticleCommentRepository.findById(commentId)
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
        ProjectArticleComment comment = projectArticleCommentRepository.findById(commentId)
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

    public ProjectArticle findArticle(Long articleId){
        return projectArticleRepository.findById(articleId)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 게시글입니다."));
    }

}
