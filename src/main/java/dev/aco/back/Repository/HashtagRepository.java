package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>{
    
}
