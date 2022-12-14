package dev.aco.back.VO;

import lombok.Data;

@Data
public class ChangePasswordVO {
  Long memberId;
  String cpassword;
  String upassword;
}
