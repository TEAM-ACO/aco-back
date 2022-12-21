package dev.aco.back.service.ArticleService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartRequest;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Article.ReplyDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleImage;
import dev.aco.back.Entity.Article.Hashtag;
import dev.aco.back.Repository.ArticleImageRepository;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.HashtagRepository;
import dev.aco.back.Utils.Image.ImageManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
        private final ArticleRepository arepo;
        private final ArticleImageRepository airepo;
        private final HashtagRepository hrepo;
        private final ImageManager imageManager;

        // @Override
        // public List<ArticleDTO> readList(Pageable pageable) {
        // return arepo.findAll(pageable).stream().map(v -> {
        // return ArticleDTO
        // .builder()
        // .articleId(v.getArticleId())
        // .articleContext(v.updateArticleContextToString(v.getArticleContext()))
        // .menu(v.getMenu().toString())
        // .member(MemberDTO.builder().memberId(v.getMember().getMemberId())
        // .nickname(v.getMember().getNickname())
        // .email(v.getMember().getEmail()).build())
        // .tags(v.getHashLinker().stream().map(t -> t.getHashtag().getTag()).toList())
        // .recomends(v.getRecomends().stream().map(r -> {
        // return r.isRecomended() ? 1 : -1;
        // }).mapToInt(Integer::intValue).sum())
        // .reported(v.getReported().size())
        // .visitors(v.getVisitors().size())
        // .replys(v.getReplys().stream().map(r -> ReplyDTO
        // .builder()
        // .replyId(r.getReplyId())
        // .replyGroup(r.getReplyGroup())
        // .replySort(r.getReplySort())
        // .replyContext(r.getReplyContext())
        // .member(MemberDTO.builder()
        // .memberId(r.getMember()
        // .getMemberId())
        // .email(r.getMember().getEmail())
        // .nickname(r.getMember().getNickname())
        // .build())
        // .build()).toList())
        // .articleImages(v.getArticleImages().stream().map(i -> i.getImg()).toList())
        // .build();
        // }).toList();
        // }

        // =========================================================================================================================================================
        // write
        @Override
        @Transactional
        public Long write(ArticleDTO dto) {
                Optional<List<MultipartRequest>> imgs = Optional.ofNullable(dto.getArticleImages());
                Optional<List<String>> tags = Optional.ofNullable(dto.getTags());
                Long articleId = arepo.save(dtoToEntity(dto)).getArticleId();
                Article article = dtoToEntity(dto);

                tags.ifPresent((hashs) -> {
                        hashs.forEach((hash) -> {
                                hrepo.save(Hashtag.builder().article(Article.builder().articleId(articleId).build())
                                                .tag(hash).build());
                        });
                });

                imgs.ifPresentOrElse((images) -> {
                        images.forEach((image) -> {
                                imageManager.ImgUpload(image);
                                String uploadedImgStr = imageManager.ImgUpload(image).toString();
                                // 이미지 업로드후 리턴된 이미지 이름이 여기에 들어가야 할 것인데 이러면 현석씨가 말씀하신대로
                                // imgManager 에서 String 이름을 반환하는 매서드를 만들어야하나?
                                airepo.save(ArticleImage.builder().article(article).img(uploadedImgStr).build());
                        });
                }, () -> {
                        airepo.save(ArticleImage.builder().article(Article.builder().articleId(articleId).build())
                                        .img("basic.png").build());
                });
                arepo.save(article);

                return articleId;
        }

}
