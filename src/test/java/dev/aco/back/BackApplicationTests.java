package dev.aco.back;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.aco.back.DTO.Article.LikeDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleImage;
import dev.aco.back.Entity.Article.ArticleNoun;
import dev.aco.back.Entity.Article.Hashtag;
import dev.aco.back.Entity.Article.ArticleLike;
import dev.aco.back.Entity.Article.Recommend;
import dev.aco.back.Entity.Article.Reply;
import dev.aco.back.Entity.Enum.Menu;
import dev.aco.back.Entity.Enum.Roles;
import dev.aco.back.Entity.Linker.ArticleHashtag;
import dev.aco.back.Entity.Report.ArticleReport;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Entity.Visitor.Visitor;
import dev.aco.back.Repository.ArticleImageRepository;
import dev.aco.back.Repository.ArticleNounRepository;
import dev.aco.back.Repository.ArticleReportRepository;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.HashtagRepository;
import dev.aco.back.Repository.LikeRepository;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Repository.RecommendRepository;
import dev.aco.back.Repository.ReplyRepository;
import dev.aco.back.Repository.VisitorRepository;
import dev.aco.back.Repository.Linker.HashArticleLInker;
import dev.aco.back.VO.pageVO;
import dev.aco.back.service.ArticleService.ArticleLikeService;
import dev.aco.back.service.ArticleService.ArticleService;
import lombok.extern.log4j.Log4j2;
import scala.collection.Seq;

@SpringBootTest
@Log4j2
class BackApplicationTests {

	@Autowired
	private ArticleRepository arepo;
	@Autowired
	private MemberRepository mrepo;
	@Autowired
	private HashtagRepository hrepo;
	@Autowired
	private HashArticleLInker halinker;
	@Autowired
	private VisitorRepository vrepo;
	@Autowired
	private ReplyRepository rrepo;
	@Autowired
	private RecommendRepository rcrepo;
	@Autowired
	private ArticleReportRepository arrepo;
	@Autowired
	private ArticleImageRepository airepo;
	@Autowired
	private LikeRepository lrepo;
  @Autowired
	private ArticleNounRepository anrepo;

	@Autowired
	private PasswordEncoder pEncoder;

	@Autowired
	private ArticleService aser;
	@Autowired
	private ArticleLikeService alser;

	@Test
	void articleGenerator() {
		Set<Roles> roles = new HashSet<>();
		roles.add(Roles.Admin);
		roles.add(Roles.User);

		Member member = mrepo.save(Member.builder()
				.email("test@test.test")
				.password(pEncoder.encode("1234"))
				.nickname("test")
				.mobile("00000000000")
				.roleSet(roles)
				.oauth(null)
				.logged(true)
				.userimg("basic.png")
				.build());

		Article article = arepo.save(Article
				.builder()
				.articleContext("test, test, test, test, test, test".getBytes())
				.menu(Menu.Diary)
				.member(member)
				.build());

		List<Hashtag> list = LongStream.range(1, 11).mapToObj(v -> {
			rrepo.save(Reply.builder().member(member).article(article).replyContext("test" + v).replyGroup(v)
					.replySort(0L).build());
			airepo.save(ArticleImage.builder().article(article).img("basic" + v + ".png").build());
			return hrepo.save(Hashtag.builder().tag("test" + v).build());
		}).toList();

		list.stream().forEach(v -> {
			halinker.save(ArticleHashtag
					.builder()
					.article(article)
					.hashtag(v)
					.build());
		});

		vrepo.save(Visitor.builder().prevLink("test.test.com").article(article).build());
		// rcrepo.save(Recommend.builder().recomended(true).recommender(member).article(article).build());
		arrepo.save(ArticleReport.builder().article(article).articleReportContext("test")
				.articleReportTitle("testtitle").articlereporter(member).build());
		lrepo.save(ArticleLike.builder().liked(true).liker(member).article(article).build());

	}

	@Test
    void generateBunchofArticle() {
        Member member = Member.builder().memberId(1L).build();
        IntStream.range(2, 30).forEach(v -> {

            Article article = arepo.saveAndFlush(Article
                                            .builder()
                                            .articleId(v*1L)
                                            .articleContext((String.valueOf(v) + "test").getBytes())
                                            .menu(Menu.Diary)
                                            .member(member)
                                            .build());

            LongStream.range(0, 11).forEach(f -> {
                rrepo
                        .save(Reply
                                .builder()
                                .member(member)
                                .article(article)
                                .replyContext(article.getArticleId()+"번 글의 댓글"+f)
                                .replyGroup(f).replySort(0L).build());
            });

        });

    }

	@Test
	void testList() {
		List<Object> listA = new ArrayList<Object>();
		listA.add("1");
		listA.add("2");
		listA.add("3");
		pageVO vo = pageVO.builder().requestedPageNumber(1).requestedPageSize(1)
				.responsedPageNumber(1).totalPageSize(1).list(listA).build();
		Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize());
		log.info(aser.readList(pageable));
	}

	@Test
	void testLikeChecking(){
    	log.info(alser.likeChecking(LikeDTO.builder()
			.liked(true).liker(1L).article(1L).build()));
	}
	void testGetListEntityGraph() {
		Pageable pageable = PageRequest.of(0, 5, Sort.by(Direction.DESC, "articleId"));
		arepo.findAllEntityGraph(pageable).forEach(v -> v.getReplys().forEach(f -> log.info(f.getReplyContext())));
		arepo.findAllEntityGraph(pageable)
				.forEach(v -> log.info(v.updateArticleContextToString(v.getArticleContext())));
	}

	@Test
	void generateBunchofArticle() {
		Member member = Member.builder().memberId(1L).build();
		IntStream.range(2, 30).forEach(v -> {

			Article article = arepo.saveAndFlush(Article
											.builder()
											.articleId(v*1L)
											.articleContext((String.valueOf(v) + "test").getBytes())
											.menu(Menu.Diary)
											.member(member)
											.build());

			LongStream.range(0, 11).forEach(f -> {
				rrepo
						.save(Reply
								.builder()
								.member(member)
								.article(article)
								.replyContext(article.getArticleId()+"번 글의 댓글"+f)
								.replyGroup(f).replySort(0L).build());
			});

			CharSequence normalized = OpenKoreanTextProcessorJava.normalize("가나다라 안녕 테스트야 test 한다");
			Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
			List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);
					phrases.forEach(c->anrepo.save(ArticleNoun.builder().article(article).noun(c.text()).build()));

		});

	}

	@Test
	void nounTest(){
		String text = "test 가나다라마바사 안녕";
		CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);
		Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
		List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);
		phrases.stream().map(v->v.text()).toList().forEach(v->log.info(v));
	}

}
