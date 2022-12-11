package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Article.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
    
}
