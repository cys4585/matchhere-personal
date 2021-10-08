package com.ssafy.match.member.entity;

import com.ssafy.match.db.entity.embedded.CompositeMemberTechstack;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "matching.member_beginner_techstack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBeginnerTechstack {

    @EmbeddedId
    private CompositeMemberTechstack compositeMemberTechstack;

    @Builder
    public MemberBeginnerTechstack(
            CompositeMemberTechstack compositeMemberTechstack) {
        this.compositeMemberTechstack = compositeMemberTechstack;
    }
}
