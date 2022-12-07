package dev.aco.back.DTO;

import java.util.Set;
import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
  private Long memberId;
  private String email;
  private String password;
  private String nickname;
  private String name;
  private String mobile;

  @Builder.Default
  private Set<String> roleSet = new HashSet<>();

  private String oauth;
  private Boolean logged;
  private String userimg;

}
