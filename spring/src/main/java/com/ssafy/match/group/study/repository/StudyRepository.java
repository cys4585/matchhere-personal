package com.ssafy.match.group.study.repository;

import com.ssafy.match.common.entity.ProjectProgressState;
import com.ssafy.match.group.study.entity.Study;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyRepository extends JpaRepository<Study, Long> {

    @Query("select s from matching.study s where s.isActive = true and s.isPublic = true")
    List<Study> findAllStudy();

    Page<Study> findByIsActiveAndIsPublicAndStatusIsNot(Boolean isActive, Boolean isPublic, Enum<ProjectProgressState> status, Pageable pageable);

}