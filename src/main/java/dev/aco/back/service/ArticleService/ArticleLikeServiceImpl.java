package dev.aco.back.service.ArticleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Article.LikeDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleLike;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.LikeRepository;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.service.ArticleService.ArticleService;
import dev.aco.back.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ArticleLikeServiceImpl implements ArticleLikeService {
    private final LikeRepository lrepo;
    // private final ArticleRepository arepo;
    // private final ArticleService aService;
    // private final MemberRepository mrepo;
    // private final MemberService mService;

    @Override
    public Boolean likeUser(LikeDTO dto) {
        try {
            lrepo.save(dtoToEntity(dto));
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean likeChecking(LikeDTO dto) {
        return lrepo.existsBylikeId(dto.getLikeId());
    }

    // @Override
    // public HashMap<String, Object> getListArticleId(Long memberId) {
    //     List<MemberDTO> member = new ArrayList<>();
    //     // 클래스가 null값일 수도 있는 변수를 감싸줌
    //     Optional<List<Like>> tmp = lrepo.getList(memberId);
    
    // List<ArticleDTO> result = tmp.get().stream().map((Function<Like, ArticleDTO>) v -> {
    //     Article tmp2 = arepo.findByArticleId(v.getArticle());
    //     ArticleDTO dto = aService.entityToDTO(tmp2);
    //     member.add(mService.entityToDTO(mrepo.findByMemberId(dto.getMember().getMemberId()).get()));
  
    //     return dto;
    //   }).toList();
  
    //   List<String> membernames = member.stream().map((Function<MemberDTO, String>) v -> {
    //     return v.getName();
    //   }).collect(Collectors.toList());

    //   HashMap<String, Object> hash = new HashMap<>();
    //     hash.put("postcard", result);
    //     hash.put("membername", membernames);
    //     return hash;
    // }
}
