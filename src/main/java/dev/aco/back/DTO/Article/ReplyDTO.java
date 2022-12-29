package dev.aco.back.DTO.Article;

import com.fasterxml.jackson.annotation.JsonInclude;

import dev.aco.back.DTO.User.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyDTO {
    private Long replyId;
    private String replyContext;
    private Long replyGroup;
    private Long replySort;
    private boolean hide;
    private MemberDTO member;
    private ArticleDTO article;
    private Long totalCount;
}
