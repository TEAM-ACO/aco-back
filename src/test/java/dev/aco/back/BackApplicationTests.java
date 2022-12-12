package dev.aco.back;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleImage;
import dev.aco.back.Entity.Article.Hashtag;
import dev.aco.back.Entity.Article.Recommend;
import dev.aco.back.Entity.Article.Reply;
import dev.aco.back.Entity.Enum.Menu;
import dev.aco.back.Entity.Enum.Roles;
import dev.aco.back.Entity.Linker.ArticleHashtag;
import dev.aco.back.Entity.Report.ArticleReport;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Entity.Visitor.Visitor;
import dev.aco.back.Repository.ArticleImageRepository;
import dev.aco.back.Repository.ArticleReportRepository;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.HashtagRepository;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Repository.RecommendRepository;
import dev.aco.back.Repository.ReplyRepository;
import dev.aco.back.Repository.VisitorRepository;
import dev.aco.back.Repository.Linker.HashArticleLInker;

@SpringBootTest
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
	private PasswordEncoder pEncoder;

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
			rrepo.save(Reply.builder().article(article).replyContext("test" + v).replyGroup(v).replySort(0L).build());
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
		rcrepo.save(Recommend.builder().recomended(true).recommender(member).article(article).build());
		arrepo.save(ArticleReport.builder().article(article).articleReportContext("test")
				.articleReportTitle("testtitle").articlereporter(member).build());

	}

	@Test
	void articleList(){
		
		
	}

}
