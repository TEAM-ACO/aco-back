package dev.aco.back.service.ArticleService;



import org.springframework.stereotype.Service;

import dev.aco.back.DTO.Article.LikeDTO;

import dev.aco.back.Repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ArticleLikeServiceImpl implements ArticleLikeService {
    private final LikeRepository lrepo;

    @Override
    public Boolean likeUser(LikeDTO dto) {
        // 해피해피 findby를 이용해서 만들어봅시다
        // liker의memberId와 article의aritlceId기준으로 하나를 찾습니다
        // ifPresentOrElse >> 두 인자 값을 가집니다
        // 첫번쨰 > 있다면(28번줄), 두번째 > 없다면(29번줄)
        // 로직이 성공하면 true 실패하면 false를 던집니다
        try {
            lrepo.findByLikerMemberIdAndArticleArticleId(dto.getLiker(), dto.getArticle())
                .ifPresentOrElse(v->lrepo.deleteById(v.getLikeId()), 
                                ()->lrepo.save(dtoToEntity(dto))
                                );    
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean likeChecking(LikeDTO dto) {
        return lrepo.existsBylikeId(dto.getLikeId());
    }

}
