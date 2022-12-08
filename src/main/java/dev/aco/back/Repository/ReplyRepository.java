package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.aco.back.Entity.Article.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{
    
}
