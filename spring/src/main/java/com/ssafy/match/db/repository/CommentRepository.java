package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.Comment;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}