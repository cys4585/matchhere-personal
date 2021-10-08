package com.ssafy.match.group.projectboard.article.service;


import com.ssafy.match.group.projectboard.article.dto.ProjectArticleCreateRequestDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleInfoDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleListDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleUpdateRequestDto;
import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import com.ssafy.match.group.projectboard.article.entity.ProjectContent;
import com.ssafy.match.group.projectboard.article.repository.ProjectArticleRepository;
import com.ssafy.match.group.projectboard.article.repository.ProjectContentRepository;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.group.projectboard.board.repository.ProjectBoardRepository;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleListDto;
import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectArticleService {
    private final ProjectBoardRepository projectBoardRepository;
    private final ProjectArticleRepository projectArticleRepository;
    private final ProjectContentRepository projectContentRepository;
    private final MemberRepository memberRepository;

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
    public Long createArticle(Integer boardId, ProjectArticleCreateRequestDto projectArticleCreateRequestDto) throws Exception {
        if (!projectBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판 입니다!");
        }
        ProjectBoard projectBoard = projectBoardRepository.getById(boardId);
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("존재하지 않는 사용자 입니다."));
        ProjectArticle projectArticle = projectArticleCreateRequestDto.toProjectBoard(member, projectBoard);
        if (projectArticleCreateRequestDto.getContent() == null) {
            throw new RuntimeException("내용이 존재하지 않습니다!");
        }
        ProjectArticle ret = projectArticleRepository.save(projectArticle);
        addContent(projectArticle, projectArticleCreateRequestDto.getContent());
        return ret.getId();
    }

    @Transactional
    public Long updateArticle(Long articleId, ProjectArticleUpdateRequestDto projectArticleUpdateRequestDto) {
        if (!projectArticleRepository.existsById(articleId)) {
            throw new RuntimeException("존재하지 않는 게시물 입니다!");
        }
        ProjectArticle projectArticle = projectArticleRepository.getById(articleId);
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("존재하지 않는 사용자 입니다."));
        if (projectArticleUpdateRequestDto.getContent() == null) {
            throw new RuntimeException("내용이 존재하지 않습니다!");
        }
        Optional<ProjectContent> projectContent = projectContentRepository.getByProjectArticle(projectArticle);
        if (projectContent.isEmpty()) {
            throw new RuntimeException("내용이 존재하지 않습니다");
        }
        updateContent(projectArticle, projectArticleUpdateRequestDto.getContent(), projectContent.get());

        projectArticle.setModifiedDate(LocalDateTime.now());
        projectArticle.setTitle(projectArticleUpdateRequestDto.getTitle());
        return projectArticle.getId();
    }

    @Transactional
    public Boolean deleteArticle(Long articleId) {
        if (!projectArticleRepository.existsById(articleId)) {
            throw new RuntimeException("존재하지 않는 게시물 입니다!");
        }
        ProjectArticle projectArticle = projectArticleRepository.getById(articleId);
        ProjectContent projectContent = projectContentRepository.getByProjectArticle(projectArticle).orElseThrow(()-> new NullPointerException("내용이 존재하지 않습니다."));
        projectContentRepository.delete(projectContent);
        projectArticleRepository.delete(projectArticle);
        return Boolean.TRUE;
    }

    @Transactional
    public void updateContent(ProjectArticle projectArticle, String content, ProjectContent projectContent) {
        projectContent.setContent(content);
        projectContent.setProjectArticle(projectArticle);
    }

    @Transactional
    public void addContent(ProjectArticle projectArticle, String content) {
        ProjectContent projectContent = ProjectContent.builder()
                .projectArticle(projectArticle)
                .content(content)
                .build();
        projectContentRepository.save(projectContent);
    }



}
