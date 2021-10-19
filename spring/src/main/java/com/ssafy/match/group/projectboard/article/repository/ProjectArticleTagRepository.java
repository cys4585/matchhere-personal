package com.ssafy.match.group.projectboard.article.repository;

import com.ssafy.match.group.project.entity.Project;
import com.ssafy.match.group.project.entity.ProjectTechstack;
import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import com.ssafy.match.group.projectboard.article.entity.ProjectArticleTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectArticleTagRepository extends JpaRepository<ProjectArticleTag, Long> {

//    // 현재 프로젝트의 기술 스택 조회
//    @Query("select pt from matching.project_techstack pt "
//        + "where pt.compositeProjectTechstack.project = :project")
//    List<ProjectTechstack> findProjectTechstackByProject(@Param("project") Project project);

    // 프로젝트 게시글의 기존 태그 전체 제거
    @Transactional
    @Modifying
    @Query("delete from ProjectArticleTag pat where pat.projectArticle = :projectArticle")
    void deleteAllTagsByProjectArticle(@Param("projectArticle") ProjectArticle projectArticle);

}