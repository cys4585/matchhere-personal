package com.ssafy.match.member.entity;

import com.ssafy.match.common.entity.Level;
import com.ssafy.match.member.entity.composite.CompositeMemberTechstack;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.*;

@Getter
//@Setter
@Entity(name = "matching.member_techstack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTechstack {

    @EmbeddedId
    private CompositeMemberTechstack compositeMemberTechstack;

    private Level level;

    @Builder
    public MemberTechstack(
        CompositeMemberTechstack compositeMemberTechstack, Level level) {
        this.compositeMemberTechstack = compositeMemberTechstack;
        this.level = level;
    }
}