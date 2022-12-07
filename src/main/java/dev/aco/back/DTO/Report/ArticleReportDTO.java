package dev.aco.back.DTO.Report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleReportDTO {
    private Long articleReportId;
    private String articleReportTitle;
    private String articleReportContext;
    private String articlereporter;
}
