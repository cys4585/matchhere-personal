package com.ssafy.match.group.entity.project;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.member_project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProject {

    @EmbeddedId
    private CompositeMemberProject compositeMemberProject;

    private String role;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    public void activation(){
        this.isActive = true;
    }

    public void deactivation(){
        this.isActive = false;
    }

    @Builder
    public MemberProject(
        CompositeMemberProject compositeMemberProject, LocalDateTime registerDate) {
        this.compositeMemberProject = compositeMemberProject;
        this.registerDate = registerDate;
    }
}