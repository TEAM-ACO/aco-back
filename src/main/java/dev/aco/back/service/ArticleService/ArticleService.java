package dev.aco.back.service.ArticleService;

import java.util.List;

import org.springframework.data.domain.Pageable;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Enum.Menu;
import dev.aco.back.Entity.User.Member;

public interface ArticleService {
    List<ArticleDTO> readList(Pageable request);
    List<ArticleDTO> readListByMemberId(Pageable request, Long memberId);
    List<ArticleDTO> readListByKeywords(Pageable request, String keywords);

    List<ArticleDTO> readListByMenu(Pageable request, Integer menuId);
    Long write(ArticleDTO dto);


    default Article dtoToEntity(ArticleDTO dto) {
        Article entity = Article.builder()
                .articleContext(dto.getArticleContext().getBytes())
                .menu(Menu.valueOf(dto.getMenu()))
                .member(Member.builder().memberId(dto.getMember().getMemberId()).nickname(dto.getMember().getNickname())
                        .email(dto.getMember().getEmail()).build())
                .build();
        return entity;
    }

    default ArticleDTO toDTO(Article entity){
        
        return ArticleDTO.builder()
                        .articleId(entity.getArticleId())
                        .articleContext(entity.updateArticleContextToString(entity.getArticleContext()))
                        .menu(entity.getMenu().toString())
                        .member(MemberDTO.builder().memberId(entity.getMember().getMemberId())
                                .nickname(entity.getMember().getNickname())
                                .email(entity.getMember().getEmail()).build())
                        .tags(entity.getHashLinker().stream().map(v->v.getHashtag().getTag()).toList())
                        .recomends(entity.getRecomends().stream().map(r -> {
                            return r.isRecomended() ? 1 : -1;}).mapToInt(Integer::intValue).sum())
                        .reported(entity.getReported().size())
                        .visitors(entity.getVisitors().size())
                        .articleImagesNames(entity.getArticleImages().stream().map(i -> i.getImg()).toList())
                        .build();
    }
}
