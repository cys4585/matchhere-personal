package com.ssafy.match.group.repository.project;

import com.ssafy.match.group.entity.project.CompositeMemberProject;
import com.ssafy.match.group.entity.project.Project;
import com.ssafy.match.group.entity.project.ProjectApplicationForm;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectApplicationFormRepository extends
    JpaRepository<ProjectApplicationForm, CompositeMemberProject> {

    // 모든 신청서 날짜 내림차순 조회
    @Query("select p from ProjectApplicationForm p where p.compositeMemberProject.project = :project order by p.createDate desc")
    List<ProjectApplicationForm> formByProjectId(@Param("project") Project project);

    // 닉네임으로 신청서 조회
    @Query("select p from ProjectApplicationForm p "
        + "where p.compositeMemberProject.project = :project "
        + "and p.nickname like %:nickname% order by p.createDate desc")
    List<ProjectApplicationForm> allFormByProjectNickname(@Param("project") Project project, @Param("nickname") String nickname);

    // 신청서 조회
    @Query("select p from ProjectApplicationForm p where p.compositeMemberProject = :cmp")
    Optional<ProjectApplicationForm> oneFormById(@Param("cmp") CompositeMemberProject cmp);


}