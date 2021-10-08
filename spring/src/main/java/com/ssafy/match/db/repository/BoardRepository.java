package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}