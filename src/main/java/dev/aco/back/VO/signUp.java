package dev.aco.back.VO;

import lombok.Data;

@Data
public class signUp {
  private Long memberId;
  private String email;
  private String password;
  private String repassword;
  private String nickname;
  private String name;
  private String mobile;
  private String oauth;

}
