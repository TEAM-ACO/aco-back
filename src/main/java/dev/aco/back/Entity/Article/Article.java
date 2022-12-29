package dev.aco.back.Entity.Article;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import dev.aco.back.Entity.Enum.Menu;
import dev.aco.back.Entity.Linker.ArticleHashtag;
import dev.aco.back.Entity.Report.ArticleReport;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Entity.Visitor.Visitor;
import dev.aco.back.Entity.etc.DateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column
    private byte[] articleContext;

    @Column
    private Menu menu;

    @ManyToOne
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<ArticleHashtag> hashLinker = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Visitor> visitors = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Recommend> recomends = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ArticleReport> reported = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Reply> replys = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ArticleImage> articleImages = new ArrayList<>();

    @Builder.Default
    // mappedBy - 양방향 연관관계, orphanRemoval=true -기존 NULL처리된 자식(연결된 점이 없는 객체)을 DELETE
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ArticleLike> liker = new ArrayList<>();

    public String updateArticleContextToString(byte[] byteString) {
        return new String(byteString, Charset.forName("utf8"));
    }
}
