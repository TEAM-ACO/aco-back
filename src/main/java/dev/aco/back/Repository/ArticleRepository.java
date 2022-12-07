package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.aco.back.Entity.Article.Article;

@Repository
public interface ArticleRepository  extends JpaRepository<Article, Long>{
    
}
