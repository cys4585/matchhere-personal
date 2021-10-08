package com.ssafy.match.group.studyboard.article.service;


import com.ssafy.match.group.studyboard.article.dto.StudyArticleListDto;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import com.ssafy.match.group.studyboard.article.dto.StudyArticleCreateRequestDto;
import com.ssafy.match.group.studyboard.article.dto.StudyArticleInfoDto;
import com.ssafy.match.group.studyboard.article.dto.StudyArticleListDto;
import com.ssafy.match.group.studyboard.article.dto.StudyArticleUpdateRequestDto;
import com.ssafy.match.group.studyboard.article.entity.StudyArticle;
import com.ssafy.match.group.studyboard.article.entity.StudyContent;
import com.ssafy.match.group.studyboard.article.repository.StudyArticleRepository;
import com.ssafy.match.group.studyboard.article.repository.StudyContentRepository;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import com.ssafy.match.group.studyboard.board.repository.StudyBoardRepository;
import com.ssafy.match.member.entity.Member;
import com.ssafy.match.member.repository.MemberRepository;
import com.ssafy.match.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyArticleService {
    private final StudyBoardRepository studyBoardRepository;
    private final StudyArticleRepository studyArticleRepository;
    private final StudyContentRepository studyContentRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public StudyArticleInfoDto getStudyArticleDetail(Long articleId) {
        StudyArticleInfoDto studyArticleInfoDto = studyArticleRepository.findById(articleId).map(StudyArticleInfoDto::of).orElseThrow(() -> new NullPointerException("존재하지 않는 게시물입니다."));
        StudyArticle studyArticle = studyArticleRepository.getById(articleId);
        Optional<StudyContent> studyContent =studyContentRepository.getByStudyArticle(studyArticle);
        if (studyContent.isEmpty()) {
            throw new RuntimeException("내용이 존재하지 않습니다");
        }
        String content = studyContent.get().getContent();
        studyArticleInfoDto.setContent(content);
        return studyArticleInfoDto;
    }

    @Transactional(readOnly = true)
    public Page<StudyArticleListDto> getStudyArticles(Integer boardId, Pageable pageable) throws Exception {
        if (!studyBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다");
        }
        StudyBoard studyBoard = studyBoardRepository.getById(boardId);
        Page<StudyArticleListDto> studyArticleListDtos = studyArticleRepository.findAllByStudyBoard(studyBoard, pageable)
                .map(StudyArticleListDto::of);
        return studyArticleListDtos;
    }

    @Transactional(readOnly = true)
    public Page<StudyArticleListDto> getStudyArticlesByTitle(Integer boardId, String title, Pageable pageable) throws Exception {
        if (!studyBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다");
        }
        StudyBoard studyBoard = studyBoardRepository.getById(boardId);
        Page<StudyArticleListDto> studyArticleListDtos = studyArticleRepository.findAllByStudyBoardAndTitle(studyBoard, title, pageable)
                .map(StudyArticleListDto::of);
        return studyArticleListDtos;
    }

    @Transactional(readOnly = true)
    public Page<StudyArticleListDto> getStudyArticlesByNickname(Integer boardId, String nickname, Pageable pageable) throws Exception {
        if (!studyBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판입니다");
        }
        StudyBoard studyBoard = studyBoardRepository.getById(boardId);
        Page<StudyArticleListDto> studyArticleListDtos = studyArticleRepository.findAllByStudyBoardAndNickname(studyBoard, nickname, pageable)
            .map(StudyArticleListDto::of);
        return studyArticleListDtos;
    }

    @Transactional
    public Long createArticle(Integer boardId, StudyArticleCreateRequestDto studyArticleCreateRequestDto) throws Exception {
        if (!studyBoardRepository.existsById(boardId)) {
            throw new RuntimeException("존재하지 않는 게시판 입니다!");
        }
        StudyBoard studyBoard = studyBoardRepository.getById(boardId);
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("존재하지 않는 사용자 입니다."));
        StudyArticle studyArticle = studyArticleCreateRequestDto.toStudyBoard(member, studyBoard);
        if (studyArticleCreateRequestDto.getContent() == null) {
            throw new RuntimeException("내용이 존재하지 않습니다!");
        }
        StudyArticle ret = studyArticleRepository.save(studyArticle);
        addContent(studyArticle, studyArticleCreateRequestDto.getContent());
        return ret.getId();
    }

    @Transactional
    public Long updateArticle(Long articleId, StudyArticleUpdateRequestDto studyArticleUpdateRequestDto) {
        if (!studyArticleRepository.existsById(articleId)) {
            throw new RuntimeException("존재하지 않는 게시물 입니다!");
        }
        StudyArticle studyArticle = studyArticleRepository.getById(articleId);
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new NullPointerException("존재하지 않는 사용자 입니다."));
        if (studyArticleUpdateRequestDto.getContent() == null) {
            throw new RuntimeException("내용이 존재하지 않습니다!");
        }
        Optional<StudyContent> studyContent = studyContentRepository.getByStudyArticle(studyArticle);
        if (studyContent.isEmpty()) {
            throw new RuntimeException("내용이 존재하지 않습니다");
        }
        updateContent(studyArticle, studyArticleUpdateRequestDto.getContent(), studyContent.get());

        studyArticle.setModifiedDate(LocalDateTime.now());
        studyArticle.setTitle(studyArticleUpdateRequestDto.getTitle());
        return studyArticle.getId();
    }

    @Transactional
    public Boolean deleteArticle(Long articleId) {
        if (!studyArticleRepository.existsById(articleId)) {
            throw new RuntimeException("존재하지 않는 게시물 입니다!");
        }
        StudyArticle studyArticle = studyArticleRepository.getById(articleId);
        StudyContent studyContent = studyContentRepository.getByStudyArticle(studyArticle).orElseThrow(()-> new NullPointerException("내용이 존재하지 않습니다."));
        studyContentRepository.delete(studyContent);
        studyArticleRepository.delete(studyArticle);
        return Boolean.TRUE;
    }

    @Transactional
    public void updateContent(StudyArticle studyArticle, String content, StudyContent studyContent) {
        studyContent.setContent(content);
        studyContent.setStudyArticle(studyArticle);
    }

    @Transactional
    public void addContent(StudyArticle studyArticle, String content) {
        StudyContent studyContent = StudyContent.builder()
                .studyArticle(studyArticle)
                .content(content)
                .build();
        studyContentRepository.save(studyContent);
    }



}
