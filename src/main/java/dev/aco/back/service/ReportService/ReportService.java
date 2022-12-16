package dev.aco.back.service.ReportService;

import dev.aco.back.DTO.Report.ArticleReportDTO;
import dev.aco.back.DTO.Report.MemberReportDTO;
import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Report.ArticleReport;
import dev.aco.back.Entity.Report.MemberReport;
import dev.aco.back.Entity.User.Member;

public interface ReportService {
    Boolean reportUser(MemberReportDTO dto);

    Boolean reportArticle(ArticleReportDTO dto);

    default ArticleReport toEntityArticle(ArticleReportDTO dto) {
        return ArticleReport.builder()
                .articleReportTitle(dto.getArticleReportTitle())
                .articleReportContext(dto.getArticleReportContext())
                .articlereporter(Member.builder().memberId(dto.getArticleId()).build())
                .article(Article.builder().articleId(dto.getArticleId()).build())
                .build();
    }

    default MemberReport toEntityMember(MemberReportDTO dto) {
        return MemberReport.builder()
                .userReportTitle(dto.getUserReportTitle())
                .userReportContext(dto.getUserReportContext())
                .reported(Member.builder().memberId(dto.getTargetUserId()).build())
                .memberreporter(Member.builder().memberId(dto.getReporterUserId()).build())
                .build();
    }
}
