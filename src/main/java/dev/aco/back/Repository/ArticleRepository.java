package dev.aco.back.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Enum.Menu;



public interface ArticleRepository extends JpaRepository<Article, Long> {


    // List<Article> findAll();
    // Article findByArticleId(Long articleId);

    @EntityGraph(attributePaths = {"hashLinker", "visitors", "recomends", "reported", "articleImages", "member", "hashLinker.hashtag", "liker"})
    Page<Article> findAll(Pageable pageable);
    
    List<Article> findAll();
    Article findByArticleId(Long articleId);



    @EntityGraph(attributePaths = {"visitors", "recomends", "reported", "member", "liker"})
    @Query(value = """ 
        SELECT DISTINCT att 
        FROM Article att 
            INNER JOIN ArticleHashtag aht on aht.article.articleId = aht.article.articleId 
            INNER JOIN Hashtag htt on aht.hashtag.hashtagId = htt.hashtagId
            INNER JOIN ArticleImage img on att.articleId = img.article.articleId
    """, countQuery = "SELECT count(att) FROM Article att")
    Page<Article> findAllEntityGraph(Pageable pageable);

    @EntityGraph(attributePaths = {"hashLinker", "visitors", "recomends", "reported", "articleImages", "member", "hashLinker.hashtag", "liker"})
    Page<Article> findAllByArticleIdIn(Pageable pageable, List<Long> ids);

    @EntityGraph(attributePaths = {"hashLinker", "visitors", "recomends", "reported", "articleImages", "member", "liker"})
    @Query(value = "SELECT att FROM Article att where att.member.memberId=:memberId", 
           countQuery = "SELECT att FROM Article att where att.member.memberId=:memberId")
    Page<Article> findAllEntityGraphByMemberId(Pageable pageable, Long memberId);

    @EntityGraph(attributePaths = {"hashLinker", "visitors", "recomends", "reported", "articleImages", "member", "liker"})
    @Query(value = "SELECT att FROM Article att where att.menu=:menu", 
    countQuery = "SELECT att FROM Article att where att.menu=:menu")
    Page<Article> findAllEntityGraphByMenu(Pageable pageable, Menu menu);
}
