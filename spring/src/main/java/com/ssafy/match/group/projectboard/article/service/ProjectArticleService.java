package com.ssafy.match.group.projectboard.article.service;


import com.ssafy.match.common.entity.Level;
import com.ssafy.match.common.entity.Techstack;
import com.ssafy.match.common.exception.CustomException;
import com.ssafy.match.common.exception.ErrorCode;
import com.ssafy.match.group.project.entity.CompositeProjectTechstack;
import com.ssafy.match.group.project.entity.Project;
import com.ssafy.match.group.project.entity.ProjectTechstack;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleInfoDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleListDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleRequestDto;
import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import com.ssafy.match.group.projectboard.article.entity.ProjectArticleTag;
import com.ssafy.match.group.projectboard.article.entity.ProjectContent;
import com.ssafy.match.group.projectboard.article.repository.ProjectArticleRepository;
import com.ssafy.match.group.projectboard.article.repository.ProjectArticleTagRepository;
import com.ssafy.match.group.projectboard.article.repository.ProjectContentRepository;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.group.projectboard.board.repository.ProjectBoardRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectArticleService {
    private final ProjectBoardRepository projectBoardRepository;
    private final ProjectArticleRepository projectArticleRepository;
    private final ProjectContentRepository projectContentRepository;
    private final MemberRepository memberRepository;
    private final ProjectArticleTagRepository projectArticleTagRepository;

    @Transactional(readOnly = true)
    public ProjectArticleInfoDto getProjectArticleDetail(Long articleId) {
        ProjectArticleInfoDto projectArticleInfoDto = projectArticleRepository.findById(articleId).map(ProjectArticleInfoDto::of).orElseThrow(() -> new NullPointerException("존재하지 않는 게시물입니다."));
        ProjectArticle projectArticle = projectArticleRepository.getById(articleId);
        Optional<ProjectContent> projectContent =projectContentRepository.getByProjectArticle(projectArticle);
        if (projectContent.isEmpty()) {
            throw new RuntimeException("내용이 존재하지 않습니다");
        }
        String content = projectContent.get().getContent();
        projectArticleInfoDto.setContent(content);
        return projectArticleInfoDto;
    }

    @Transactional(readOnly = true)
    public Page<ProjectArticleListDto> getProjectArticles(Integer boardId, Pageable pageable) throws Exception {
        if (!projectBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다");
        }
        ProjectBoard projectBoard = projectBoardRepository.getById(boardId);
        Page<ProjectArticleListDto> projectArticleListDtos = projectArticleRepository.findAllByProjectBoard(projectBoard ,pageable)
                .map(ProjectArticleListDto::of);
        return projectArticleListDtos;
    }

    @Transactional(readOnly = true)
    public Page<ProjectArticleListDto> getProjectArticlesByTitle(Integer boardId, String title, Pageable pageable) throws Exception {
        if (!projectBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다");
        }
        ProjectBoard projectBoard = projectBoardRepository.getById(boardId);
        Page<ProjectArticleListDto> projectArticleListDtos = projectArticleRepository.findAllByProjectBoardAndTitle(projectBoard, title, pageable)
            .map(ProjectArticleListDto::of);
        return projectArticleListDtos;
    }

    @Transactional(readOnly = true)
    public Page<ProjectArticleListDto> getProjectArticlesByNickname(Integer boardId, String nickname, Pageable pageable) throws Exception {
        if (!projectBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다");
        }
        ProjectBoard projectBoard = projectBoardRepository.getById(boardId);
        Page<ProjectArticleListDto> projectArticleListDtos = projectArticleRepository.findAllByProjectBoardAndNickname(projectBoard, nickname, pageable)
            .map(ProjectArticleListDto::of);
        return projectArticleListDtos;
    }

    @Transactional
    public Long createArticle(ProjectArticleRequestDto dto) {
        ProjectBoard projectBoard = findProjectBoard(dto.getProjectBoardId());
        Member member = findMember(SecurityUtil.getCurrentMemberId());
        if (dto.getContent() == null) {
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        ProjectArticle projectArticle = projectArticleRepository.save(ProjectArticle.of(dto, projectBoard, member));
        addContent(projectArticle, dto.getContent());
        addTags(projectArticle, dto.getTags());
        return projectArticle.getId();
    }

    @Transactional
    public HttpStatus updateArticle(Long articleId, ProjectArticleRequestDto dto) {
        // 게시글
        ProjectArticle projectArticle = findProjectArticle(articleId);
        if(dto.getContent() == null){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        // 게시글 내용
        ProjectContent projectContent = findProjectContent(projectArticle);
        projectContent.setContent(dto.getContent());

        projectArticle.update(dto, findProjectBoard(dto.getProjectBoardId()));
        addTags(projectArticle, dto.getTags());
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus deleteArticle(Long articleId) {
        ProjectArticle projectArticle = findProjectArticle(articleId);
        ProjectContent projectContent = findProjectContent(projectArticle);
        projectContentRepository.delete(projectContent);
        projectArticleRepository.delete(projectArticle);
        projectArticleTagRepository.deleteAllTagsByProjectArticle(projectArticle);
        return HttpStatus.OK;
    }

    @Transactional
    public void addContent(ProjectArticle projectArticle, String content) {
        projectContentRepository.save(ProjectContent.of(projectArticle, content));
    }

    public ProjectBoard findProjectBoard(int boardId){
        return projectBoardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    public Member findMember(Long memberId){
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public ProjectArticle findProjectArticle(Long projectArticleId){
        return projectArticleRepository.findById(projectArticleId)
            .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    public ProjectContent findProjectContent(ProjectArticle projectArticle){
        return projectContentRepository.getByProjectArticle(projectArticle)
            .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
    }

    // 게시글 태그 추가
    @Transactional
    public void addTags(ProjectArticle projectArticle, List<String> tags) {
        projectArticleTagRepository.deleteAllTagsByProjectArticle(projectArticle);
        if (tags == null) {
            return;
        }

        for (String tag: tags) {
            projectArticleTagRepository.save(ProjectArticleTag.of(projectArticle, tag));
        }

    }

}
