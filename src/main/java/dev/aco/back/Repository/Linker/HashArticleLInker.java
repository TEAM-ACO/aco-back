package dev.aco.back.Repository.Linker;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.aco.back.Entity.Linker.ArticleHashtag;

public interface HashArticleLInker extends JpaRepository<ArticleHashtag, Long>{
    @Query(nativeQuery = true,
    value = "SELECT aht.article_article_id FROM article_hashtag aht INNER JOIN hashtag htt ON aht.hashtag_hashtag_id = htt.hashtag_id WHERE REGEXP_LIKE(htt.tag, :regex) "
    )
    List<Long> searchingArticleIdWithRegexText(String regex);
}
