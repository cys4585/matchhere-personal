package com.ssafy.match.db.entity.embedded;

import com.ssafy.match.db.entity.Comment;
import com.ssafy.match.db.entity.Reaction;
import com.ssafy.match.member.entity.Member;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompositeMemberCommentReaction implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reaction_id")
    private Reaction reaction;

    public CompositeMemberCommentReaction(Member member, Comment comment,
        Reaction reaction) {
        this.member = member;
        this.comment = comment;
        this.reaction = reaction;
    }
}