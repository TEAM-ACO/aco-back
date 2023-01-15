package dev.aco.back.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
    @EntityGraph(attributePaths = {"member"})
    Page<Reply> findAllByArticleArticleId(Pageable pageable, Long articleId);

    boolean existsByReplyIdAndMemberMemberIdAndArticleArticleId(Long id, Long memberId, Long articleId);
}
