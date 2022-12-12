package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Long>{
    
}
