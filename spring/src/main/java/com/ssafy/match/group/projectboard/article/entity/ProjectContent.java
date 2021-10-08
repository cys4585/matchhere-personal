package com.ssafy.match.group.projectboard.article.entity;


import com.ssafy.match.group.projectboard.article.entity.ProjectArticle;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "matching.project_content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "project_article_id")
    private ProjectArticle projectArticle;

    @Column(name = "content")
    private String content;

    @Builder
    public ProjectContent(ProjectArticle projectArticle, String content) {
        this.projectArticle = projectArticle;
        this.content = content;
    }

}
