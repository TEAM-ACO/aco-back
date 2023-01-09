package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Report.MemberReport;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {
    boolean existsByMemberreporterMemberIdAndReportedMemberId(Long reporter, Long reported);
}
