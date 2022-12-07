package dev.aco.back.Entity.User;

import org.hibernate.annotations.ColumnDefault;

import dev.aco.back.Entity.etc.DateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class emailAuth extends DateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long eauthId;

  @Column(nullable = false)
  String email;

  @Column(nullable = false)
  Integer authNum;

  @ColumnDefault("0")
  Boolean isAuthrized;
}
