package dev.aco.back.service.ArticleService;

import java.util.List;
import java.util.Optional;

import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartRequest;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleImage;
import dev.aco.back.Entity.Article.ArticleNoun;
import dev.aco.back.Entity.Article.Hashtag;
import dev.aco.back.Entity.Enum.Menu;
import dev.aco.back.Repository.ArticleImageRepository;
import dev.aco.back.Repository.ArticleNounRepository;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.HashtagRepository;
import dev.aco.back.Utils.Image.ImageManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import scala.collection.Seq;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
	private final ArticleRepository arepo;
	private final ArticleImageRepository airepo;
	private final ArticleNounRepository anrepo;
	private final HashtagRepository hrepo;
	private final ImageManager imageManager;

	@Override
	public List<ArticleDTO> readList(Pageable pageable) {
		return arepo.findAllEntityGraph(pageable).stream().map(v -> toDTO(v)).toList();
	}

	@Override
	public List<ArticleDTO> readListByMemberId(Pageable request, Long memberId) {
		return arepo.findAllEntityGraphByMemberId(request, memberId).stream().map(v -> toDTO(v)).toList();
	}

	@Override
	public List<ArticleDTO> readListByKeywords(Pageable request, String keywords) {
		return arepo.findByNounsNounIn(request, nounExtractor(keywords)).stream().map(v -> toDTO(v)).toList();
	}

	@Override
	public List<ArticleDTO> readListByMenu(Pageable request, Integer menuId) {
		
		return arepo.findAllEntityGraphByMenu(request, Menu.values()[menuId]).stream().map(v->toDTO(v)).toList();
		//    0: Diary, 1: Tip, 2:Question
	}	

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

		Article result = arepo.save(article);

		List<String> phrases = nounExtractor(dto.getArticleContext());
		phrases.forEach(v -> anrepo.save(ArticleNoun.builder().article(result).noun(v).build()));
		return articleId;
	}

	private List<String> nounExtractor(String string) {
		CharSequence normalized = OpenKoreanTextProcessorJava.normalize(string);
		Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
		List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);
		return phrases.stream().map(v -> v.text()).toList();
	}

}
