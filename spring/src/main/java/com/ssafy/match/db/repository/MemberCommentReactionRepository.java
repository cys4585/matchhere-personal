package com.ssafy.match.db.repository;

import com.ssafy.match.db.entity.MemberCommentReaction;
import com.ssafy.match.db.entity.embedded.CompositeMemberCommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCommentReactionRepository extends
    JpaRepository<MemberCommentReaction, CompositeMemberCommentReaction> {

}