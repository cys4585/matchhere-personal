package com.ssafy.match.group.clubboard.article.repository;

import com.ssafy.match.group.clubboard.article.entity.ClubArticle;
import com.ssafy.match.group.clubboard.article.entity.ClubArticleTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClubArticleTagRepository extends JpaRepository<ClubArticleTag, Long> {

    // 현재 클럽의 태그 조회
    List<ClubArticleTag> findAllByClubArticle(ClubArticle clubArticle);

    // 클럽 게시글의 기존 태그 전체 제거
    @Transactional
    @Modifying
    @Query("delete from ClubArticleTag pat where pat.clubArticle = :clubArticle")
    void deleteAllTagsByClubArticle(@Param("clubArticle") ClubArticle clubArticle);

}