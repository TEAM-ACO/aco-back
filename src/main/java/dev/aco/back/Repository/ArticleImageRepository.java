package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.ArticleImage;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long>{
    
}
