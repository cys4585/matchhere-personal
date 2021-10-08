package com.ssafy.match.group.studyboard.board.repository;


import com.ssafy.match.group.entity.study.Study;
import com.ssafy.match.group.studyboard.board.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoard, Integer> {
    List<StudyBoard> findAllByStudy(Study study);
}
