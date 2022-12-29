package dev.aco.back.Entity.Article;

import dev.aco.back.Entity.User.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
// 모든 필드 값을 파라미터로 받는 생성자를 만듦
@AllArgsConstructor
// 파라미터가 없는 기본 생성자를 생성
@NoArgsConstructor
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 기본 키 생성을 데이터베이스에 위임 id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다
    private Long likeId;

    @Column
    private boolean liked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Member liker;
}
