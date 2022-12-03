package dev.aco.back.Entity.Linker;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.Hashtag;
import jakarta.persistence.Entity;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashLinkerId;

    @ManyToOne
    private Article article;
    @ManyToOne
    private Hashtag hashtag;
}
