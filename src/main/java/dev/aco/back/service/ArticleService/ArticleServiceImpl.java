package dev.aco.back.service.ArticleService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Article.ReplyDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleImage;
import dev.aco.back.Entity.Article.Hashtag;
import dev.aco.back.Repository.ArticleImageRepository;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.HashtagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
        private final ArticleRepository arepo;
        private final ArticleImageRepository airepo;
        private final HashtagRepository hrepo;

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

        // =========================================================================================================================================================
        // write
        @Override
        @Transactional
        public Long write(ArticleDTO dto) {
                // ofNullable() 정적 팩토리 메소드를 호출하여 객체를 Optional로 감싸주었습니다. 객체가 null인 경우를 대비하여 of()
                // 대신에 ofNullable()을 사용했습니다.
                Optional<List<String>> imgs = Optional.ofNullable(dto.getArticleImages());
                Optional<List<String>> tags = Optional.ofNullable(dto.getTags());
                Long articleId = arepo.save(dtoToEntity(dto)).getArticleId();

                // 사용자가 이미지를 첨부했다면?
                imgs.ifPresentOrElse((images) -> {
                        for (int i = 0; i < images.size(); i++) {
                                airepo.save(ArticleImage.builder()
                                                .article(Article.builder().articleId(articleId).build())
                                                .img(images.get(i)).idx(i).build());
                        }
                }, () -> {
                        airepo.save(ArticleImage.builder().article(Article.builder().articleId(articleId).build())
                                        .img("basic.png")
                                        .idx(0).build());
                });

                tags.ifPresent((hashs) -> {
                        hashs.forEach((hash) -> {
                                hrepo.save(Hashtag.builder().article(Article.builder().articleId(articleId).build())
                                                .tag(hash).build());
                        });
                });

                // 참고한 레퍼런스에는 빠져있는 부분인데 게시글 자체를 저장을 해야될것 같아서 추가를 해봤습니다.
                Article article = dtoToEntity(dto);
                arepo.save(article);

                return articleId;
        }

}
