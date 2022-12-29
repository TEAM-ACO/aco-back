package dev.aco.back.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAll();
    Article findByArticleId(Long articleId);
}
