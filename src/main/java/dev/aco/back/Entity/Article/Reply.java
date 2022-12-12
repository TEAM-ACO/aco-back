package dev.aco.back.Entity.Article;

import dev.aco.back.Entity.User.Member;
import dev.aco.back.Entity.etc.DateEntity;
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
public class Reply extends DateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(nullable = false)
    private String replyContext;

    @Column(nullable = false)
    private Long replyGroup;

    @Column(nullable = false)
    private Long replySort;

    @Column
    private boolean hide;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Member member;
}
