package dev.aco.back.service.ArticleService;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Article.ReplyDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Repository.ArticleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
        private final ArticleRepository arepo;

        @Override
        public List<ArticleDTO> readList(Pageable pageable) {
                return arepo.findAll(pageable).stream().map(v -> {
                        return ArticleDTO
                                        .builder()
                                        .articleId(v.getArticleId())
                                        .articleContext(v.updateArticleContextToString(v.getArticleContext()))
                                        .menu(v.getMenu().toString())
                                        .member(MemberDTO.builder().memberId(v.getMember().getMemberId())
                                                        .nickname(v.getMember().getNickname())
                                                        .email(v.getMember().getEmail()).build())
                                        .tags(v.getHashLinker().stream().map(t -> t.getHashtag().getTag()).toList())
                                        .recomends(v.getRecomends().stream().map(r -> {
                                                return r.isRecomended() ? 1 : -1;
                                        }).mapToInt(Integer::intValue).sum())
                                        .reported(v.getReported().size())
                                        .visitors(v.getVisitors().size())
                                        .replys(v.getReplys().stream().map(r -> ReplyDTO
                                                        .builder()
                                                        .replyId(r.getReplyId())
                                                        .replyGroup(r.getReplyGroup())
                                                        .replySort(r.getReplySort())
                                                        .replyContext(r.getReplyContext())
                                                        .member(MemberDTO.builder()
                                                                        .memberId(r.getMember()
                                                                                        .getMemberId())
                                                                        .email(r.getMember().getEmail())
                                                                        .nickname(r.getMember().getNickname())
                                                                        .build())
                                                        .build()).toList())
                                        .articleImages(v.getArticleImages().stream().map(i -> i.getImg()).toList())
                                        .build();
                }).toList();
        }

}
