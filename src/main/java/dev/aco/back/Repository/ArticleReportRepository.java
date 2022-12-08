package dev.aco.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.aco.back.Entity.Report.ArticleReport;

public interface ArticleReportRepository extends JpaRepository<ArticleReport, Long>{
    
}
