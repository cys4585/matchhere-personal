package com.ssafy.match.group.projectboard.board.repository;


import com.ssafy.match.group.projectboard.board.entity.ProjectBoard;
import com.ssafy.match.group.project.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, Integer> {
    List<ProjectBoard> findAllByProject(Project project);
}
