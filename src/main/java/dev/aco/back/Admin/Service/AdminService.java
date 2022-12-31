package dev.aco.back.Admin.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Pageable;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Report.ArticleReportDTO;
import dev.aco.back.DTO.Report.MemberReportDTO;
import dev.aco.back.DTO.User.MemberDTO;

public interface AdminService {
    HashMap<String, Object> adminMainboardInformation(Integer whichWeekAgo);

    List<ArticleDTO> adminArticleList(Pageable pageable);
    List<MemberDTO> adminMemberList(Pageable pageable);
    List<ArticleReportDTO> adminArticleReportList(Pageable pageable);
    List<MemberReportDTO> adminMemberReportList(Pageable pageable);

    Boolean deleteArticle(Long articleId);
    Boolean deleteMember(Long memberId);
    Boolean deleteArticleReport(Long articleReportId);
    Boolean deleteMemberReport(Long memberReportId);
}
