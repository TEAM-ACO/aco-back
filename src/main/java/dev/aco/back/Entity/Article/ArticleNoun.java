package dev.aco.back.Entity.Article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleNoun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleNounId;

    @Column
    private String noun;

    @ManyToOne(fetch = FetchType.EAGER)
    private Article article;
}
