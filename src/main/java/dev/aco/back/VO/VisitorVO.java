package dev.aco.back.VO;

import java.util.HashMap;
import java.util.List;

import dev.aco.back.DTO.Article.ArticleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorVO {
    private Integer thisWeekVisitor;
    private HashMap<Integer, Integer> dayOfVisitor;
    private HashMap<String, Integer> prevLinkWithSum;
    private List<ArticleDTO> visitedArticle;
}
