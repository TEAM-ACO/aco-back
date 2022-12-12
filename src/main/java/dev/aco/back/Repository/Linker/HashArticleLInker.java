package dev.aco.back.Repository.Linker;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Linker.ArticleHashtag;

public interface HashArticleLInker extends JpaRepository<ArticleHashtag, Long>{
    
}
