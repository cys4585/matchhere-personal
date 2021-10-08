package com.ssafy.match.group.repository.study;

import com.ssafy.match.db.entity.Status;
import com.ssafy.match.group.entity.study.Study;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyRepository extends JpaRepository<Study, Long> {

    @Query("select s from matching.study s where s.isActive = true and s.isPublic = true")
    List<Study> findAllStudy();

    Page<Study> findByIsActiveAndIsPublicAndStatusIsNot(Boolean isActive, Boolean isPublic, Enum<Status> status, Pageable pageable);

}