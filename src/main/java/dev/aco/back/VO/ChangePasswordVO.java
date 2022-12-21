package dev.aco.back.VO;

import lombok.Data;

@Data
public class ChangePasswordVO {
  Long memberId;
  // 현재 비밀번호
  String cpassword;
  // 바꾸려는 비밀번호
  String upassword;
}
