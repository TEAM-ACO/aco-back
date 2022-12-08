package dev.aco.back.DTO.Article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {
    private Long replyId;
    private String replyContext;
    private Long replyGroup;
    private Long replySort;
    private boolean hide;
    private ArticleDTO article;
}
