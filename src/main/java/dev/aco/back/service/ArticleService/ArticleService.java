package dev.aco.back.service.ArticleService;

import java.util.List;

import org.springframework.data.domain.Pageable;

import dev.aco.back.DTO.Article.ArticleDTO;

public interface ArticleService {
    List<ArticleDTO> readList(Pageable request);

}
