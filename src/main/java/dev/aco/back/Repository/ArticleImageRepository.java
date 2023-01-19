package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Article.ArticleImage;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long>{
    @EntityGraph(attributePaths = {"article"})
    @Query("select COUNT(img) from ArticleImage img where img.article.articleId =:articleid")
    Integer imgCount(Long articleid);
}
