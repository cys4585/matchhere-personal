package com.ssafy.match.db.entity;

import com.ssafy.match.db.entity.embedded.CompositeMemberArticleReaction;
import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.member_article_reaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberArticleReaction {

    @EmbeddedId
    private CompositeMemberArticleReaction compositeMemberArticleReaction;

    private LocalDateTime created_date;

    public MemberArticleReaction(
        CompositeMemberArticleReaction compositeMemberArticleReaction, LocalDateTime created_date) {
        this.compositeMemberArticleReaction = compositeMemberArticleReaction;
        this.created_date = created_date;
    }
}