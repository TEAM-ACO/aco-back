package dev.aco.back.DTO.User;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDTO {
  private Long memberId;
  private String email;
  private String password;
  private String nickname;
  private String name;
  private String mobile;
  private Set<String> roleSet;
  private String oauth;
  private Boolean logged;
  private String userimg;

}
