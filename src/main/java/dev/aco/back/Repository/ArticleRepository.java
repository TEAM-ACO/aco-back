package dev.aco.back.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Article.Article;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAll();

    @EntityGraph(attributePaths = {"hashLinker", "visitors", "recomends", "reported", "replys", "articleImages"})
    @Query("SELECT att FROM Article att")
    Page<Article> findAllEntityGraph(Pageable pageable);
}
