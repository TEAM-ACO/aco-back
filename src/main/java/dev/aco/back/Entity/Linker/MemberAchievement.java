package dev.aco.back.Entity.Linker;

import dev.aco.back.Entity.User.Achievement;
import dev.aco.back.Entity.User.Member;
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
public class MemberAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achieveLinkerId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Achievement achievement;
}
