package com.ssafy.match.group.studyboard.board.entity;


import com.ssafy.match.group.study.entity.Study;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity(name = "matching.study_board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Builder
    public StudyBoard(String name, Study study) {
        this.name = name;
        this.study = study;
    }
}
