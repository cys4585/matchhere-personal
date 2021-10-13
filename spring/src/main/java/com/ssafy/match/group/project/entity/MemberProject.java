package com.ssafy.match.group.project.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity(name = "matching.member_project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberProject {

    @EmbeddedId
    private CompositeMemberProject compositeMemberProject;

    private String role;
    @Column(name = "register_date")
    private LocalDateTime registerDate;
    private String authority;
    @Column(name = "is_active")
    private boolean isActive;

    public void activation() {
        this.isActive = true;
    }
    public void deactivation() {
        this.isActive = false;
    }

    public static MemberProject of(CompositeMemberProject compositeMemberProject, String role,
        String authority){
        return MemberProject.builder()
            .compositeMemberProject(compositeMemberProject)
            .role(role)
            .registerDate(LocalDateTime.now())
            .authority(authority)
            .isActive(true)
            .build();
    }
}