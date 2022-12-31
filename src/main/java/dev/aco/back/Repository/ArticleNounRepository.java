package dev.aco.back.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Article.ArticleNoun;

public interface ArticleNounRepository extends JpaRepository<ArticleNoun, Long>{
    @Query(nativeQuery = true,
    value = "SELECT nns.article_article_id FROM article_noun as nns WHERE REGEXP_LIKE(nns.noun, :regex) "
    )
    List<Long> searchingArticleIdWithRegexText(String regex);
}
