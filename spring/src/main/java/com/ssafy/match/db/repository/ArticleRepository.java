package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}