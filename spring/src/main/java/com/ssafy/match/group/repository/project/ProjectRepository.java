package com.ssafy.match.group.repository.project;

import com.ssafy.match.db.entity.Status;
import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.entity.study.Study;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from matching.project p where p.isActive = true and p.isPublic = true")
    List<Project> findAllProject();

    Page<Project> findByIsActiveAndIsPublicAndStatusIsNot(Boolean isActive, Boolean isPublic, Enum<Status> status, Pageable pageable);
}