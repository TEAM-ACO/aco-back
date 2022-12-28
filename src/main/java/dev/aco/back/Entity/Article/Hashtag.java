package dev.aco.back.Entity.Article;

import java.util.ArrayList;
import java.util.List;

import dev.aco.back.Entity.Linker.ArticleHashtag;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    @Builder.Default
    @OneToMany(mappedBy = "hashtag", fetch = FetchType.EAGER, orphanRemoval = false)
    private List<ArticleHashtag> hashLinker = new ArrayList<>();

    @Column(nullable = false)
    private String tag;

    @ManyToOne
    private Article article;

}
