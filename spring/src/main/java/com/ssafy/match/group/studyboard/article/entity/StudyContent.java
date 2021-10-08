package com.ssafy.match.group.studyboard.article.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "matching.study_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "study_article_id")
    private StudyArticle studyArticle;

    @Column(name = "content")
    private String content;

    @Builder
    public StudyContent(StudyArticle studyArticle, String content) {
        this.studyArticle = studyArticle;
        this.content = content;
    }

}
