package dev.aco.back.service.ArticleService;

import java.util.List;

import dev.aco.back.DTO.Article.ArticleDTO;

public interface ArticleService {
    List<ArticleDTO> readList();
}
