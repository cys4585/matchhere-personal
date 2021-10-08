package com.ssafy.match.group.repository.study;

import com.ssafy.match.group.entity.study.CompositeStudyTechstack;
import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.group.entity.study.StudyTechstack;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyTechstackRepository extends
    JpaRepository<StudyTechstack, CompositeStudyTechstack> {

    // 현재 스터디의 기술 스택명 조회
    @Query("select st.compositeStudyTechstack.techstack.name from matching.study_techstack st "
        + "where st.compositeStudyTechstack.study = :study")
    List<String> findByStudyTechstackName(@Param("study") Study study);

    @Query("select st from matching.study_techstack st where st.compositeStudyTechstack.study = :study")
    List<StudyTechstack> findStudyTechstackByStudy(@Param("study") Study study);

    @Query("select st.compositeStudyTechstack.techstack.name from matching.study_techstack st "
            + "where st.compositeStudyTechstack.study.id = :id")
    List<String> findStudyTechstackNameByStudyId(@Param("id") Long id);

}