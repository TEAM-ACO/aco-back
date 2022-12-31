package dev.aco.back.service.ArticleService;

import java.util.HashMap;

import dev.aco.back.DTO.Article.LikeDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleLike;
import dev.aco.back.Entity.User.Member;

public interface ArticleLikeService {
  // HashMap<String, Object> getListArticleId(Long articleId);

  Boolean likeUser(LikeDTO dto);

  Boolean likeChecking(LikeDTO dto);

  default ArticleLike dtoToEntity(LikeDTO dto) {
    ArticleLike entity = ArticleLike.builder()
    .likeId(dto.getLikeId())
    .liker(Member.builder().memberId(dto.getLiker()).build())
    .article(Article.builder().articleId(dto.getArticle()).build())
    .build();
    return entity;
  }

  default LikeDTO EntityToDto(ArticleLike entity) {
    LikeDTO dto = LikeDTO.builder()
    .likeId(entity.getLikeId())
    .liker(entity.getLiker().getMemberId())
    .article(entity.getArticle().getArticleId())
    .build();
    return dto;
  }
}
