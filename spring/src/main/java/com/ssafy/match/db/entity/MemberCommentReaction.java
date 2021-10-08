package com.ssafy.match.db.entity;

import com.ssafy.match.db.entity.embedded.CompositeMemberCommentReaction;
import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.member_comment_reaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCommentReaction {

    @EmbeddedId
    private CompositeMemberCommentReaction compositeMemberCommentReaction;

    private LocalDateTime created_date;

    public MemberCommentReaction(
        CompositeMemberCommentReaction compositeMemberCommentReaction, LocalDateTime created_date) {
        this.compositeMemberCommentReaction = compositeMemberCommentReaction;
        this.created_date = created_date;
    }
}