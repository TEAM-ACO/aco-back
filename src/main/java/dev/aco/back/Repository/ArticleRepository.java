package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.Article;

public interface ArticleRepository  extends JpaRepository<Article, Long>{
    
}
