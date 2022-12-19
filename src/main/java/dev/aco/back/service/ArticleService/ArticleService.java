package dev.aco.back.service.ArticleService;

import java.util.List;

import org.springframework.data.domain.Pageable;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Enum.Menu;
import dev.aco.back.Entity.User.Member;

public interface ArticleService {
    List<ArticleDTO> readList(Pageable request);

    Long write(ArticleDTO dto);

    default Article dtoToEntity(ArticleDTO dto) {
        Article entity = Article.builder()
                .articleContext(dto.getArticleContext().getBytes())
                .menu(Menu.valueOf(dto.getMenu()))
                .member(Member.builder().memberId(dto.getMember().getMemberId()).nickname(dto.getMember().getNickname())
                        .email(dto.getMember().getEmail()).build())
                .build();
        return entity;
    }
}
