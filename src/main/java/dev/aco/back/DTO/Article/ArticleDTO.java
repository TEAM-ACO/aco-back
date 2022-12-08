package dev.aco.back.DTO.Article;

import java.util.List;

import dev.aco.back.DTO.Report.ArticleReportDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.DTO.Visitor.VisitorDTO;
import dev.aco.back.Entity.Linker.ArticleHashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {
    private Long articleId;
    private String articleTitle;
    private byte[] articleContext;
    private String menu;
    private MemberDTO member;
    private List<ArticleHashtag> hashLinker;
    private List<VisitorDTO> visitors;
    private List<RecommendDTO> recomends;
    private List<ArticleReportDTO> reported;
    private List<ReplyDTO> replys;
    private List<ArticleImageDTO> articleImages;
}
