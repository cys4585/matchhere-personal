package com.ssafy.match.group.projectboard.board.entity;


import com.ssafy.match.group.entity.project.Project;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.project_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Builder
    public ProjectBoard(String name, Project project) {
        this.name = name;
        this.project = project;
    }
}
