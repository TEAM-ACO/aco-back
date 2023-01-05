package dev.aco.back.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Enum.Menu;



public interface ArticleRepository extends JpaRepository<Article, Long> {


    // List<Article> findAll();
    // Article findByArticleId(Long articleId);

    Page<Article> findAll(Pageable pageable);
    
    List<Article> findAll();
    Article findByArticleId(Long articleId);

    @Query(value = """ 
        SELECT DISTINCT att
        FROM Article att 
            OUTER JOIN ArticleHashtag aht on aht.article.articleId = aht.article.articleId 
            OUTER JOIN Hashtag htt on aht.hashtag.hashtagId = htt.hashtagId 
            OUTER JOIN ArticleImage img on att.articleId = img.article.articleId 
            OUTER JOIN Member mb on att.member.memberId = mb.memberId 
            OUTER JOIN ArticleLike alk on att.articleId = alk.article.articleId
            OUTER JOIN Visitor vtr on att.articleId = vtr.article.articleId
        """, countQuery = """ 
            SELECT DISTINCT att from Article att
            """)
    Page<Article> findAllEntityGraph(Pageable pageable);

    @Query(value = """ 
        SELECT DISTINCT att
        FROM Article att 
            OUTER JOIN ArticleHashtag aht on aht.article.articleId = aht.article.articleId 
            OUTER JOIN Hashtag htt on aht.hashtag.hashtagId = htt.hashtagId 
            OUTER JOIN ArticleImage img on att.articleId = img.article.articleId 
            OUTER JOIN Member mb on att.member.memberId = mb.memberId 
            OUTER JOIN ArticleLike alk on att.articleId = alk.article.articleId
            OUTER JOIN Visitor vtr on att.articleId = vtr.article.articleId
        WHERE 
            att.articleId in (:ids)
        """, countQuery = """ 
            SELECT DISTINCT att from Article att
            WHERE 
            att.articleId in (:ids)
            """)
    Page<Article> findAllByArticleIdIn(Pageable pageable, List<Long> ids);
   
    @Query(value = """
        SELECT DISTINCT att 
        FROM Article att 
            OUTER JOIN ArticleHashtag aht on aht.article.articleId = aht.article.articleId 
            OUTER JOIN Hashtag htt on aht.hashtag.hashtagId = htt.hashtagId 
            OUTER JOIN ArticleImage img on att.articleId = img.article.articleId 
            INNER JOIN Member mb on att.member.memberId = mb.memberId 
            OUTER JOIN ArticleLike alk on att.articleId = alk.article.articleId
            OUTER JOIN Visitor vtr on att.articleId = vtr.article.articleId
        WHERE att.member.memberId=:memberId
        """, 
           countQuery = "SELECT DISTINCT att FROM Article att where att.member.memberId=:memberId")
    Page<Article> findAllEntityGraphByMemberId(Pageable pageable, Long memberId);

    @Query(value = """
        SELECT DISTINCT att 
        FROM Article att 
            OUTER JOIN ArticleHashtag aht on aht.article.articleId = aht.article.articleId 
            OUTER JOIN Hashtag htt on aht.hashtag.hashtagId = htt.hashtagId 
            OUTER JOIN ArticleImage img on att.articleId = img.article.articleId 
            OUTER JOIN Member mb on att.member.memberId = mb.memberId 
            OUTER JOIN ArticleLike alk on att.articleId = alk.article.articleId
            OUTER JOIN Visitor vtr on att.articleId = vtr.article.articleId
        WHERE att.menu=:menu
        """, 
    countQuery = "SELECT DISTINCT att FROM Article att where att.menu=:menu")
    Page<Article> findAllEntityGraphByMenu(Pageable pageable, Menu menu);
}
