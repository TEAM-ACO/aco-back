package dev.aco.back.service.ArticleService.Reply;

import java.util.List;

import org.springframework.data.domain.Pageable;

import dev.aco.back.DTO.Article.ReplyDTO;

public interface ReplyService {
    Boolean writeReply(ReplyDTO dto);
    List<ReplyDTO> readReplyByArticleId(Long articleId, Pageable pageable);
}
