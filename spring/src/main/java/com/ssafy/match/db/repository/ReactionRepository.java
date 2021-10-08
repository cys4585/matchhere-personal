package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {

}