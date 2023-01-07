package dev.aco.back.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
