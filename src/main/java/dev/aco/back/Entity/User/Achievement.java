package dev.aco.back.Entity.User;

import java.util.ArrayList;
import java.util.List;

import dev.aco.back.Entity.Linker.MemberAchievement;
import dev.aco.back.Entity.etc.DateEntity;
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
public class Achievement extends DateEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementId;


    @Builder.Default
    @OneToMany(mappedBy = "achievement", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<MemberAchievement> achieveLinker = new ArrayList<>();
}
