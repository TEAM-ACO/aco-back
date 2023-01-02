package dev.aco.back.Admin.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Report.ArticleReportDTO;
import dev.aco.back.DTO.Report.MemberReportDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Visitor.Visitor;
import dev.aco.back.Repository.ArticleReportRepository;
import dev.aco.back.Repository.ArticleRepository;
import dev.aco.back.Repository.MemberReportRepository;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Repository.VisitorRepository;
import dev.aco.back.Repository.MemberRepository.getRecentMember;
import dev.aco.back.VO.VisitorVO;
import dev.aco.back.service.ArticleService.ArticleService;
import dev.aco.back.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {
    private final VisitorRepository vrepo;
    private final MemberRepository mrepo;
    private final ArticleRepository arepo;
    private final ArticleReportRepository arrepo;
    private final MemberReportRepository mrrepo;
    private final ArticleService aser;
    private final MemberService mser;

    @Override
    public HashMap<String, Object> adminMainboardInformation(Integer weeksAgo) {
        HashMap<String, Object> result = new HashMap<>();
        LocalDate currentDay = LocalDate.now();
        currentDay.minusDays(7 * weeksAgo);
        Pageable pageableArticle = PageRequest.of(0, 5, Sort.by(Direction.DESC, "articleId"));
        Pageable pageableMember = PageRequest.of(0, 5, Sort.by(Direction.DESC, "memberId"));
        result.put("visitorInfo",
                weekVisitorInformation(vrepo.findAllByCreatedDateTimeGreaterThan(currentDay.atStartOfDay())));
        result.put("recentArticle", recentArticleList(arepo.findAll(pageableArticle).getContent()));
        result.put("recentMember", recentMemberList(mrepo.findAllRecentMember(pageableMember).getContent()));
        return result;
    }

    public VisitorVO weekVisitorInformation(List<Visitor> visitorInfo) {
        HashMap<String, Integer> visitorPrevTotalInfo = new HashMap<>();
        HashMap<Integer, Integer> dayOfVisitorList = new HashMap<>();
        List<ArticleDTO> listOfVisitedArticle = new ArrayList<>();
        Integer weekTotalVisitor = visitorInfo.size();

        visitorInfo.forEach(v -> {
            listOfVisitedArticle.add(ArticleDTO.builder().articleId(v.getArticle().getArticleId())
                    .articleContext(v.getArticle().updateArticleContextToString(v.getArticle().getArticleContext()))
                    .build());

            LocalDate targetDate = v.getCreatedDateTime().toLocalDate();
            if (dayOfVisitorList.containsKey(targetDate.getDayOfMonth())) {
                dayOfVisitorList.compute(targetDate.getDayOfMonth(), (key, val) -> val += 1);
            } else {
                dayOfVisitorList.put(targetDate.getDayOfMonth(), 1);
            }

            String prev = v.getPrevLink();
            if (visitorPrevTotalInfo.containsKey(prev)) {
                visitorPrevTotalInfo.compute(prev, (key, val) -> val += 1);
            } else {
                visitorPrevTotalInfo.put(prev, 1);
            }
        });
        return VisitorVO.builder().thisWeekVisitor(weekTotalVisitor).dayOfVisitor(dayOfVisitorList)
                .prevLinkWithSum(visitorPrevTotalInfo).visitedArticle(listOfVisitedArticle).build();
    }

    public List<ArticleDTO> recentArticleList(List<Article> entity) {
        return entity.stream().map(v -> aser.toDTO(v)).toList();
    }

    public List<MemberDTO> recentMemberList(List<getRecentMember> entity) {
        return entity.stream()
                .map(v -> MemberDTO
                        .builder()
                        .email(v.getEmail())
                        .nickname(v.getNickname())
                        .joindate(v.getJoindate())
                        .memberId(v.getMemberId())
                        .build())
                .toList();
    }

    @Override
    public List<ArticleDTO> adminArticleList(Pageable pageable) {
        return arepo.findAll(pageable).stream().map(v -> aser.toDTO(v)).toList();
    }

    @Override
    public List<MemberDTO> adminMemberList(Pageable pageable) {
        return mrepo.findAll(pageable).stream().map(v -> mser.entityToDTO(v)).toList();
    }

    @Override
    public List<ArticleReportDTO> adminArticleReportList(Pageable pageable) {
        return arrepo.findAll(pageable)
                .stream()
                .map(v -> ArticleReportDTO
                        .builder()
                        .articleReportId(v.getArticleReportId())
                        .articleReportTitle(v.getArticleReportTitle())
                        .articleReportContext(v.getArticleReportContext())
                        .articlereporterId(v.getArticlereporter().getMemberId())
                        .articleId(v.getArticle().getArticleId()).build())
                .toList();
    }

    @Override
    public List<MemberReportDTO> adminMemberReportList(Pageable pageable) {
        return  mrrepo.findAll(pageable)
                .stream()
                .map(v-> MemberReportDTO
                        .builder()
                        .userReportId(v.getUserReportId())
                        .userReportTitle(v.getUserReportTitle())
                        .userReportContext(v.getUserReportContext())
                        .targetUserId(v.getReported().getMemberId())
                        .reporterUserId(v.getMemberreporter().getMemberId())
                        .build())
                .toList();
    }

    @Override
    public Boolean deleteArticle(Long articleId) {
        try {
            arepo.deleteById(articleId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteArticleReport(Long articleReportId) {
        try {
            arrepo.deleteById(articleReportId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteMember(Long memberId) {
        try {
            mrepo.deleteById(memberId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public Boolean deleteMemberReport(Long memberReportId) {
        try {
            mrrepo.deleteById(memberReportId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
