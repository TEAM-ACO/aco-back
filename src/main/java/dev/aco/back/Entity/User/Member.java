package dev.aco.back.Entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.aco.back.Entity.Article.Article;
import dev.aco.back.Entity.Article.ArticleLike;
import dev.aco.back.Entity.Article.Reply;
import dev.aco.back.Entity.Enum.Roles;
import dev.aco.back.Entity.Linker.MemberAchievement;
import dev.aco.back.Entity.Report.ArticleReport;
import dev.aco.back.Entity.Report.MemberReport;
import dev.aco.back.Entity.etc.DateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String name;

    @Column(unique = true)
    private String mobile;

    @Column
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Roles> roleSet = new HashSet<>();

    @Column
    private String oauth;

    @Column
    private Boolean logged;

    @Column
    private String userimg;

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Reply> reply = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "articlereporter", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ArticleReport> articlereporter = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "memberreporter", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MemberReport> memberreporter = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reported", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MemberReport> reported = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MemberAchievement> achieveLinker = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "liker", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ArticleLike> liker = new ArrayList<>();

}
