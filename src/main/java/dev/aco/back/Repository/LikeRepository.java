package dev.aco.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleLike;
import dev.aco.back.Entity.User.Member;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<ArticleLike, Long> {

    Boolean existsBylikeId(Long likeId);
    
    // @Query("SELECT lk FROM Like lk WHERE memberId=:memberId and articleId=:articleId")
    // Optional<Like> findByMemberAndArticle(Long memberId, Long articleId);
    // // Optional<Like> findByMemberAndArticle(Member memberId, Article articleId);

    // @Query("SELECT lk FROM Like lk WHERE memberId=:memberId")
    // Optional<List<Like>> getList(Long memberId);

    // @Modifying
    // @Transactional
    // @Query("delete from Like lk where memberId=:memberId")
    // void deleteByMemberId(Long memberId);
    // Boolean existsByArticleArticleIdAndLikerMemberId(Long article, Long liker);
}
