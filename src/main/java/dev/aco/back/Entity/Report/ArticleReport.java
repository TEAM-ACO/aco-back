package dev.aco.back.Entity.Report;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.User.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleReportId;

    @Column
    private String articleReportTitle;

    @Column
    private String articleReportContext;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Member articlereporter;
}