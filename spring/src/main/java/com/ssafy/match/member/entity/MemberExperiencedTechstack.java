package com.ssafy.match.member.entity;

import com.ssafy.match.db.entity.embedded.CompositeMemberTechstack;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;

@Getter
//@Setter
@Entity(name = "matching.member_experienced_techstack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberExperiencedTechstack {

    @EmbeddedId
    private CompositeMemberTechstack compositeMemberTechstack;

    @Builder
    public MemberExperiencedTechstack(
        CompositeMemberTechstack compositeMemberTechstack) {
        this.compositeMemberTechstack = compositeMemberTechstack;
    }
}