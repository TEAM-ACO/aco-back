package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.ArticleNoun;

public interface ArticleNounRepository extends JpaRepository<ArticleNoun, Long>{
    
}
