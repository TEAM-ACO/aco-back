package dev.aco.back.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import dev.aco.back.Entity.Article.ArticleLike;


public interface LikeRepository extends JpaRepository<ArticleLike, Long> {
    Boolean existsBylikeId(Long likeId);

    Optional<ArticleLike> findByLikerMemberIdAndArticleArticleId(Long memberId, Long articleId);

}
