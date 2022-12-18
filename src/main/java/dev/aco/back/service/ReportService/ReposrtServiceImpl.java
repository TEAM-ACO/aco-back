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
    public Boolean reportArticle(ArticleReportDTO dto) {
        try {
            arrepo.save(toEntityArticle(dto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean reportUser(MemberReportDTO dto) {
        try {
            mrrepo.save(toEntityMember(dto));
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
