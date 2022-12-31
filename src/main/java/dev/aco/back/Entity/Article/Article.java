package dev.aco.back.Entity.Article;

import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Set;


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
import jakarta.persistence.OrderBy;
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
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, orphanRemoval = false)

    @OrderBy("hashLinkerId asc")
    private Set<ArticleHashtag> hashLinker = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = false)
    private Set<Visitor> visitors = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Recommend> recomends = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ArticleReport> reported = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("replyId asc")
    private Set<Reply> replys = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("articleImageId asc")
    private Set<ArticleImage> articleImages = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ArticleNoun> nouns = new LinkedHashSet<>();

    @Builder.Default
    // mappedBy - 양방향 연관관계, orphanRemoval=true -기존 NULL처리된 자식(연결된 점이 없는 객체)을 DELETE
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ArticleLike> liker = new LinkedHashSet<>();


    public String updateArticleContextToString(byte[] byteString) {
        return new String(byteString, Charset.forName("utf8"));
    }
}
