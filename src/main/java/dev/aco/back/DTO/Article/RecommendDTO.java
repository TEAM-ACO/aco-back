package dev.aco.back.DTO.Article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendDTO {
    private Long RecommendId;
    private boolean recomended;
    private String recommender;
}
