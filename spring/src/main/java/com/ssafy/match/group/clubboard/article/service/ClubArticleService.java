package com.ssafy.match.group.clubboard.article.service;


import com.ssafy.match.common.entity.GroupAuthority;
import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.group.club.entity.Club;
import com.ssafy.match.group.club.entity.CompositeMemberClub;
import com.ssafy.match.group.club.entity.MemberClub;
import com.ssafy.match.group.club.repository.MemberClubRepository;
import com.ssafy.match.group.clubboard.article.dto.ClubArticleInfoResponseDto;
import com.ssafy.match.group.clubboard.article.dto.ClubArticleRequestDto;
import com.ssafy.match.group.clubboard.article.dto.ClubArticleSimpleInfoResponseDto;
import com.ssafy.match.group.clubboard.article.entity.ClubArticle;
import com.ssafy.match.group.clubboard.article.entity.ClubArticleTag;
import com.ssafy.match.group.clubboard.article.entity.ClubContent;
import com.ssafy.match.group.clubboard.article.repository.ClubArticleRepository;
import com.ssafy.match.group.clubboard.article.repository.ClubArticleTagRepository;
import com.ssafy.match.group.clubboard.article.repository.ClubContentRepository;
import com.ssafy.match.group.clubboard.board.entity.ClubBoard;
import com.ssafy.match.group.clubboard.board.repository.ClubBoardRepository;
import com.ssafy.match.group.clubboard.comment.repository.ClubArticleCommentRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubArticleService {

    private final ClubBoardRepository clubBoardRepository;
    private final ClubArticleRepository clubArticleRepository;
    private final ClubContentRepository clubContentRepository;
    private final MemberRepository memberRepository;
    private final ClubArticleTagRepository clubArticleTagRepository;
    private final ClubArticleCommentRepository clubArticleCommentRepository;
    private final MemberClubRepository memberClubRepository;

    @Transactional(readOnly = true)
    public ClubArticleInfoResponseDto getClubArticleDetail(Long articleId) {
        ClubArticle clubArticle = findClubArticle(articleId);

        // 권한 체크 (소속원인지 확인)
        Club club = clubArticle.getClubBoard().getClub();
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        checkAuthority(club, member);

        ClubArticleInfoResponseDto clubArticleInfoResponseDto = ClubArticleInfoResponseDto.of(
            clubArticle, getClubArticleTagList(clubArticle));
        ClubContent clubContent = findClubContent(clubArticle);
        clubArticleInfoResponseDto.setContent(clubContent.getContent());
        return clubArticleInfoResponseDto;
    }

    public List<String> getClubArticleTagList(ClubArticle clubArticle) {
        List<ClubArticleTag> pats = clubArticleTagRepository.findAllByClubArticle(
            clubArticle);
        List<String> tags = new ArrayList<>();
        for (ClubArticleTag pat : pats) {
            tags.add(pat.getName());
        }
        return tags;
    }

    @Transactional(readOnly = true)
    public Page<ClubArticleSimpleInfoResponseDto> getClubArticles(Integer boardId,
        Pageable pageable) {

        ClubBoard clubBoard = findClubBoard(boardId);
        // 권한 체크 (소속원인지 확인)
        Club club = clubBoard.getClub();
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        checkAuthority(club, member);

        Page<ClubArticle> clubArticles = clubArticleRepository.findAllByClubBoard(clubBoard,
            pageable);
        return clubArticles.map(
            m -> ClubArticleSimpleInfoResponseDto.of(m, getClubArticleTagList(m)));
    }

    @Transactional
    public ClubArticleInfoResponseDto createArticle(ClubArticleRequestDto dto) {
        ClubBoard clubBoard = findClubBoard(dto.getClubBoardId());
        // 권한 체크 (소속원인지 확인)
        Club club = clubBoard.getClub();
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        checkAuthority(club, member);

        if (dto.getContent() == null) {
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        ClubArticle clubArticle = clubArticleRepository.save(
            ClubArticle.of(dto, clubBoard, member));
        addContent(clubArticle, dto.getContent());
        addTags(clubArticle, dto.getTags());

        ClubArticleInfoResponseDto clubArticleInfoResponseDto = ClubArticleInfoResponseDto.of(clubArticle, dto.getTags());
        clubArticleInfoResponseDto.setContent(dto.getContent());
        return clubArticleInfoResponseDto;
    }

    @Transactional
    public ClubArticleInfoResponseDto updateArticle(Long articleId,
        ClubArticleRequestDto dto) {

        // 게시글
        ClubArticle clubArticle = findClubArticle(articleId);
        // 권한 체크 (소속원인지 확인)
        Club club = clubArticle.getClubBoard().getClub();
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        checkAuthority(club, member);
        // 작성자인지 확인
        if (!clubArticle.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

        if (dto.getContent() == null) {
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        // 게시글 내용
        ClubContent clubContent = findClubContent(clubArticle);
        clubContent.setContent(dto.getContent());

        clubArticle.update(dto, findClubBoard(dto.getClubBoardId()));
        addTags(clubArticle, dto.getTags());

        ClubArticleInfoResponseDto clubArticleInfoResponseDto = ClubArticleInfoResponseDto.of(
            clubArticle, dto.getTags());
        clubArticleInfoResponseDto.setContent(clubContent.getContent());
        return clubArticleInfoResponseDto;
    }

    @Transactional
    public HttpStatus deleteArticle(Long articleId) {
        ClubArticle clubArticle = findClubArticle(articleId);

        Club club = clubArticle.getClubBoard().getClub();
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        // 소속원이고 작성자이거나 소유자, 관리자인지
        checkDeleteAuthority(club, member, clubArticle);

        ClubContent clubContent = findClubContent(clubArticle);
        deleteAllCommentByClubArticle(clubArticle);
        clubArticleTagRepository.deleteAllTagsByClubArticle(clubArticle);
        clubContentRepository.delete(clubContent);
        clubArticleRepository.delete(clubArticle);

        return HttpStatus.OK;
    }

    @Transactional
    public void addContent(ClubArticle clubArticle, String content) {
        clubContentRepository.save(ClubContent.of(clubArticle, content));
    }

    // 클럽 게시글 조회수 증가
    @Transactional
    public HttpStatus plusViewCount(Long clubArticleId) {
        findClubArticle(clubArticleId).plusViewCount();
        return HttpStatus.OK;
    }

    public ClubBoard findClubBoard(int boardId) {
        return clubBoardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    public Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if (!member.getIs_active()) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }

    public ClubArticle findClubArticle(Long clubArticleId) {
        return clubArticleRepository.findById(clubArticleId)
            .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    public ClubContent findClubContent(ClubArticle clubArticle) {
        return clubContentRepository.getByClubArticle(clubArticle)
            .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
    }

    public void deleteAllCommentByClubArticle(ClubArticle clubArticle) {
        clubArticleCommentRepository.deleteAllByClubArticle(clubArticle);
    }

    // 게시글 태그 추가
    @Transactional
    public void addTags(ClubArticle clubArticle, List<String> tags) {
        clubArticleTagRepository.deleteAllTagsByClubArticle(clubArticle);
        if (tags == null) {
            return;
        }

        for (String tag : tags) {
            clubArticleTagRepository.save(ClubArticleTag.of(clubArticle, tag));
        }

    }

    public void checkAuthority(Club club, Member member) {
        CompositeMemberClub compositeMemberClub = new CompositeMemberClub(member, club);
        // 가입 여부 확인
        MemberClub memberClub = memberClubRepository.findById(compositeMemberClub)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND));
        if (Boolean.FALSE.equals(memberClub.getIsActive())) throw new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND);

    }

    public void checkUpdateAuthority(Club club, Member member, ClubArticle clubArticle) {
        CompositeMemberClub compositeMemberClub = new CompositeMemberClub(member, club);
        // 가입 여부 확인
        MemberClub memberClub = memberClubRepository.findById(compositeMemberClub)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND));
        if (!memberClub.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND);
        }
        if (!(memberClub.getAuthority().equals(GroupAuthority.소유자) ||
            memberClub.getAuthority().equals(GroupAuthority.관리자) ||
            !clubArticle.getMember().getId().equals(member.getId()))) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

    }

    public void checkDeleteAuthority(Club club, Member member, ClubArticle clubArticle) {
        CompositeMemberClub compositeMemberClub = new CompositeMemberClub(member, club);
        // 가입 여부 확인
        MemberClub memberClub = memberClubRepository.findById(compositeMemberClub)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND));
        if (!memberClub.getIsActive()) {
            throw new CustomException(ErrorCode.MEMBER_CLUB_NOT_FOUND);
        }
        if (!(memberClub.getAuthority().equals(GroupAuthority.소유자) ||
            memberClub.getAuthority().equals(GroupAuthority.관리자) ||
            !clubArticle.getMember().getId().equals(member.getId()))) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_CHANGE);
        }

    }

}
