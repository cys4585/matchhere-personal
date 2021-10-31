package com.ssafy.match.group.clubboard.comment.service;

import com.ssafy.match.common.entity.GroupAuthority;
import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.club.entity.CompositeMemberClub;
import com.ssafy.match.group.club.entity.MemberClub;
import com.ssafy.match.group.club.repository.MemberClubRepository;
import com.ssafy.match.group.clubboard.article.entity.ClubArticle;
import com.ssafy.match.group.clubboard.article.repository.ClubArticleRepository;
import com.ssafy.match.group.clubboard.comment.dto.ClubArticleCommentRequestDto;
import com.ssafy.match.group.clubboard.comment.dto.ClubArticleCommentResponseDto;
import com.ssafy.match.group.clubboard.comment.entity.ClubArticleComment;
import com.ssafy.match.group.clubboard.comment.repository.ClubArticleCommentRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ClubCommentServiceImpl implements ClubCommentService {

    private final MemberRepository memberRepository;
    private final ClubArticleRepository clubArticleRepository;
    private final ClubArticleCommentRepository clubArticleCommentRepository;
    private final MemberClubRepository memberClubRepository;

    @Transactional
    public ClubArticleCommentResponseDto create(Long articleId, Long parentId, ClubArticleCommentRequestDto dto) {
        if (parentId > 0) {
            ClubArticleComment parent = findClubArticleComment(parentId);
            parent.addReplyCount();
        }

        ClubArticle clubArticle = findArticle(articleId);
        // 권한 체크 (소속원인지 확인)
        Club club = clubArticle.getClubBoard().getClub();
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        checkAuthority(club, member);


        ClubArticleComment clubArticleComment = ClubArticleComment.of(dto, member, clubArticle);
        clubArticleComment.setDepth(parentId);
        clubArticleCommentRepository.save(clubArticleComment);

        if (parentId == 0) {
            clubArticleComment.setParentId(clubArticleComment.getId());
        }

        return ClubArticleCommentResponseDto.from(clubArticleComment);
    }

    public List<ClubArticleCommentResponseDto> allComment(Long articleId) {
        return clubArticleCommentRepository.allComment(findArticle(articleId))
            .stream()
            .map(ClubArticleCommentResponseDto::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public ClubArticleCommentResponseDto update(Long commentId, ClubArticleCommentRequestDto dto) {
        ClubArticleComment comment = findClubArticleComment(commentId);

        if (!comment.getMember().getId().equals(SecurityUtil.getCurrentMemberId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

        comment.setContent(dto.getContent());
        comment.setIsModified(true);

        return ClubArticleCommentResponseDto.from(comment);
    }

    @Transactional
    public HttpStatus delete(Long commentId) {
        ClubArticleComment comment = findClubArticleComment(commentId);
        Member member = findMember(SecurityUtil.getCurrentMemberId());

        checkDeleteAuthority(comment.getClubArticle().getClubBoard().getClub(), member, comment);
        // 현재 댓글이 부모댓글이 아니라면 부모 댓글의 대댓글 수 감소
        if(comment.getParentId() != comment.getId()) {
            findClubArticleComment(comment.getParentId()).removeReplyCount();
        }
        comment.setIsDeleted(true);
        return HttpStatus.OK;
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public ClubArticle findArticle(Long articleId) {
        return clubArticleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    public ClubArticleComment findClubArticleComment(Long commentId) {
        return clubArticleCommentRepository.findById(commentId)
            .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public void checkAuthority(Club club, Member member) {
        CompositeMemberClub compositeMemberClub = new CompositeMemberClub(member, club);
        // 가입 여부 확인
        MemberClub memberClub = memberClubRepository.findById(compositeMemberClub)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND));
        if (!memberClub.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND);
        }

    }

    public void checkDeleteAuthority(Club club, Member member, ClubArticleComment comment) {
        CompositeMemberClub compositeMemberClub = new CompositeMemberClub(member, club);
        // 가입 여부 확인
        MemberClub memberClub = memberClubRepository.findById(compositeMemberClub)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND));
        if (!memberClub.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND);
        }
        // 삭제 권한
        if (!(memberClub.getAuthority().equals(GroupAuthority.소유자) ||
            memberClub.getAuthority().equals(GroupAuthority.관리자) ||
            !comment.getMember().getId().equals(member.getId()))) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

    }

}
