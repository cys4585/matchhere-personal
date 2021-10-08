package com.ssafy.match.group.repository.project;

import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.entity.project.ProjectTechstack;
import com.ssafy.match.group.entity.project.CompositeProjectTechstack;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectTechstackRepository extends JpaRepository<ProjectTechstack, CompositeProjectTechstack> {
    // 현재 프로젝트의 기술 스택 조회
    @Query("select pt from matching.project_techstack pt "
        + "where pt.compositeProjectTechstack.project = :project")
    List<ProjectTechstack> findProjectTechstackByProject(@Param("project") Project project);

    // 현재 프로젝트의 기술 스택명 조회
    @Query("select pt.compositeProjectTechstack.techstack.name from matching.project_techstack pt "
        + "where pt.compositeProjectTechstack.project = :project")
    List<String> findByProjectTechstackName(@Param("project") Project project);
}