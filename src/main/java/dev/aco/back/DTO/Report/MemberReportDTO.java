package dev.aco.back.DTO.Report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberReportDTO {
    private Long userReportId;
    private String userReportTitle;
    private String userReportContext;
    private Long targetUserId;
    private Long reporterUserId;
}
