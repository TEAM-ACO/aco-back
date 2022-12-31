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
