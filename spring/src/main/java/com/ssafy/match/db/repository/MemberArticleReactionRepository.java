package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.MemberArticleReaction;
import com.ssafy.match.db.entity.embedded.CompositeMemberArticleReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberArticleReactionRepository extends
    JpaRepository<MemberArticleReaction, CompositeMemberArticleReaction> {

}