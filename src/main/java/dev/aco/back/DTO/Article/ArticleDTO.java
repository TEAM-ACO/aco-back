package dev.aco.back.DTO.Article;

import java.util.List;

import org.springframework.web.multipart.MultipartRequest;

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
public class ArticleDTO {
    private Long articleId;
    private String articleContext;
    private String menu;
    private MemberDTO member;
    private List<String> tags;
    private Integer visitors;
    private Integer recomends;
    private Integer reported;
    private List<ReplyDTO> replys;
    private List<String> articleImagesNames;
    private List<MultipartRequest> articleImages;
}
