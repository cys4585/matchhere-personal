package com.ssafy.match.group.entity.project;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "matching.project_techstack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectTechstack {

    @EmbeddedId
    private CompositeProjectTechstack compositeProjectTechstack;

    @Builder
    public ProjectTechstack(
        CompositeProjectTechstack compositeProjectTechstack) {
        this.compositeProjectTechstack = compositeProjectTechstack;

    }
}