package dev.aco.back.DTO.Article;

// import dev.aco.back.Entity.Article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeDTO {
    private Long likeId;
    private boolean liked;
    private Long liker;
    private Long article;
}
