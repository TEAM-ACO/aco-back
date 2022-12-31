package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import dev.aco.back.Entity.Article.ArticleLike;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<ArticleLike, Long> {

    Boolean existsBylikeId(Long likeId);
    
}
