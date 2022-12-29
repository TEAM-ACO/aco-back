package dev.aco.back.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Article.Article;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAll();
    Article findByArticleId(Long articleId);

    @EntityGraph(attributePaths = {"hashLinker", "visitors", "recomends", "reported", "articleImages", "member"})
    @Query(value = "SELECT att FROM Article att", countQuery = "SELECT count(att) FROM Article att")
    Page<Article> findAllEntityGraph(Pageable pageable);
}
