package dev.aco.back.service.ArticleService.Reply;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Article.ReplyDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.Reply;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.ReplyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository rrepo;

    @Override
    public Boolean writeReply(ReplyDTO dto) {
        Long replyNumber = rrepo.save(
                Reply.builder()
                        .replyContext(dto.getReplyContext())
                        .replyGroup(dto.getReplyGroup())
                        .replySort(dto.getReplySort())
                        .article(Article.builder().articleId(dto.getArticle().getArticleId())
                                .member(Member.builder().memberId(dto.getMember().getMemberId()).build()).build())
                        .build())
                .getReplyId();
        return replyNumber > 0 ? true : false;
    }

    @Override
    public List<ReplyDTO> readReplyByArticleId(Long articleId, Pageable pageable) {
		Page<Reply> result = rrepo.findAllByArticleArticleId(pageable, articleId);
            return result.getContent().stream().map(v->{
                return ReplyDTO.builder()
                .replyId(v.getReplyId())
                .replyContext(v.getReplyContext())
                .replyGroup(v.getReplyGroup())
                .replySort(v.getReplySort())
                .member(MemberDTO.builder()
                            .memberId(v.getMember().getMemberId())
							.nickname(v.getMember().getNickname())
							.email(v.getMember().getEmail())
							.build()
                        )
				.article(ArticleDTO.builder().articleId(articleId).build())
				.totalCount(result.getTotalElements())
                .build();
            }).toList();
    }
}
