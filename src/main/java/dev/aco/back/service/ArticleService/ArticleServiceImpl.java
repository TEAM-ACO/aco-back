package dev.aco.back.service.ArticleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleImage;
import dev.aco.back.Entity.Article.ArticleNoun;
import dev.aco.back.Entity.Article.Hashtag;
import dev.aco.back.Entity.Enum.Menu;
import dev.aco.back.Entity.Linker.ArticleHashtag;
import dev.aco.back.Repository.ArticleImageRepository;
import dev.aco.back.Repository.ArticleNounRepository;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.HashtagRepository;
import dev.aco.back.Repository.Linker.HashArticleLInker;
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

	private final HashArticleLInker halrepo;

	private final ImageManager imageManager;

	@Override
	public List<ArticleDTO> readList(Pageable pageable) {
		return arepo.findAll(pageable).stream().map(v -> toDTO(v)).toList();
	}

	@Override
	public List<ArticleDTO> readListByMemberId(Pageable request, Long memberId) {
		return arepo.findAllByMemberMemberId(request, memberId).stream().map(v -> toDTO(v)).toList();
	}

	@Override
	public List<ArticleDTO> readListByKeywords(Pageable request, String keywords) {
		String regex = String.join("|", nounExtractor(keywords));
		Set<Long> resultIds = halrepo.searchingArticleIdWithRegexText(regex).stream().collect(Collectors.toSet());
		resultIds.addAll(anrepo.searchingArticleIdWithRegexText(regex));
		return arepo.findAllByArticleIdIn(request, resultIds.stream().toList()).stream().map(v->toDTO(v)).toList();
		
	}
	@Override
	public List<ArticleDTO> readListByMenu(Pageable request, Integer menuId) {
		return arepo.findAllByMenuEquals(request, Menu.values()[menuId]).stream().map(v->toDTO(v)).toList();
		//    0: Diary, 1: Tip, 2:Question
	}

	// =========================================================================================================================================================
	// write
	@Override
	@Transactional
	public Long write(ArticleDTO dto) {
		Optional<List<MultipartFile>> imgs = Optional.ofNullable(dto.getArticleImages());
		Article article = dtoToEntity(dto);
		List<String> phrases = nounExtractor(dto.getArticleContext());
		List<Hashtag> tags = dto.getTags()
								.stream()
								.map(v-> hrepo.findByTag(v).orElse(hrepo.save(Hashtag.builder().tag(v).build())))
									.toList();
									

		
		Article result = arepo.save(article);

		imgs.ifPresentOrElse((images) -> {
			images.forEach((image) -> {
				String uploadedImgStr = imageManager.ImgUpload(image).toString();
				airepo.save(ArticleImage.builder().article(article).img(uploadedImgStr).build());
			});
		}, () -> {
			airepo.save(ArticleImage.builder().article(article)
			.img("basic.png").build());
		});

		tags.forEach(v->halrepo.save(ArticleHashtag.builder().article(result).hashtag(v).build()));
		phrases.forEach(v -> anrepo.save(ArticleNoun.builder().article(result).noun(v).build()));
		return result.getArticleId();
	}

	private List<String> nounExtractor(String string) {
		CharSequence normalized = OpenKoreanTextProcessorJava.normalize(string);
		Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
		List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);

		if(phrases.size()!=0){
			return phrases.stream().map(v -> v.text()).toList();
		}else{
			return List.of(string.split(" "));
		}
	}

}
