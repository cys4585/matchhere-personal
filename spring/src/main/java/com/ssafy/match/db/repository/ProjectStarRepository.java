package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.ProjectStar;
import com.ssafy.match.group.entity.project.CompositeMemberProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStarRepository extends JpaRepository<ProjectStar, CompositeMemberProject> {

}