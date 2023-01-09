package dev.aco.back.DTO.Article;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import dev.aco.back.DTO.Article.etc.locationDTO;
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
    private Long memberId;
    private List<String> tags;
    private Integer visitors;
    private Integer recomends;
    private Integer reported;
    private Integer likes;
    private List<ReplyDTO> replys;
    private List<String> articleImagesNames;
    private List<MultipartFile> articleImages;
    private locationDTO locationInfo;
    private Integer replyCount;
    private LocalDateTime date;
    private Long totalCount;
}
