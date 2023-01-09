package dev.aco.back.service.ReportService;

import org.springframework.stereotype.Service;

import dev.aco.back.DTO.Report.ArticleReportDTO;
import dev.aco.back.DTO.Report.MemberReportDTO;
import dev.aco.back.Repository.ArticleReportRepository;
import dev.aco.back.Repository.MemberReportRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReposrtServiceImpl implements ReportService{
    private final ArticleReportRepository arrepo;
    private final MemberReportRepository mrrepo;
    @Override
    public Integer reportArticle(ArticleReportDTO dto) {
        if(arrepo.existsByArticleArticleIdAndArticlereporterMemberId(dto.getArticleId(), dto.getArticlereporterId())){
            return -1;
        }else{
            try {
                arrepo.save(toEntityArticle(dto));
                return 1;
            } catch (Exception e) {
                return 2;
            }
        }
    }

    @Override
    public Integer reportUser(MemberReportDTO dto) {
        if(mrrepo.existsByMemberreporterMemberIdAndReportedMemberId(dto.getReporterUserId(), dto.getTargetUserId())){
            return -1;
        }
        try {
            mrrepo.save(toEntityMember(dto));
            return 1;

        } catch (Exception e) {
            return 2;
        }
    }
}
