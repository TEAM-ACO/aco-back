package dev.aco.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Enum.Menu;



public interface ArticleRepository extends JpaRepository<Article, Long> {
    @EntityGraph(attributePaths = {"member"})
    Page<Article> findAll(Pageable pageable);
    
    @EntityGraph(attributePaths = {"member"})
    Page<Article> findAllByMemberMemberId(Pageable pageable, Long MemberId);

    @EntityGraph(attributePaths = {"member"})
    Page<Article> findAllByArticleIdIn(Pageable pageable, List<Long> ids);

    @EntityGraph(attributePaths = {"member"})
    Page<Article> findAllByMenuEquals(Pageable pageable, Menu menu);

    @Modifying
    @Query("UPDATE Article att set att.articleContext=:context, att.menu=:menu where att.articleId=:articleId")
    void updateArticle(byte[] context, Menu menu, Long articleId);

    @EntityGraph(attributePaths = {"member", "hashLinker", "articleImages", "nouns", "hashLinker.hashtag"})
    Optional<Article> findById(Long id);

    // 이러면 DB전체를 긁어서 매우매우 성능 문제가 생길 것 같지만.. 일단 조금 더 궁리하기
    @Query("SELECT att FROM Article att where att.menu = 1 Order by RAND() limit 1")
    Optional<Article> randomTip();
}
