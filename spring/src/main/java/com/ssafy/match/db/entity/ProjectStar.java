package com.ssafy.match.db.entity;

import com.ssafy.match.group.entity.project.CompositeMemberProject;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.project_star")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectStar {

    @EmbeddedId
    private CompositeMemberProject compositeMemberProject;

    public ProjectStar(CompositeMemberProject compositeMemberProject) {
        this.compositeMemberProject = compositeMemberProject;
    }
}