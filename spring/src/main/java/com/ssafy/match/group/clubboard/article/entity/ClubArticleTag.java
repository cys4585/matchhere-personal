package com.ssafy.match.group.clubboard.article.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@Table(name = "club_article_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "club_article_id", nullable = false)
    private ClubArticle clubArticle;

    @NotBlank
    private String name;

    public static com.ssafy.match.group.clubboard.article.entity.ClubArticleTag of(ClubArticle clubArticle, String name){
        return com.ssafy.match.group.clubboard.article.entity.ClubArticleTag.builder()
            .clubArticle(clubArticle)
            .name(name)
            .build();
    }

}
