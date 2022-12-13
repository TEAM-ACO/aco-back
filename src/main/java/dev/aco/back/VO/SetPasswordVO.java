package dev.aco.back.VO;

import lombok.Data;

@Data
public class SetPasswordVO {
  Long memberId;
  String cpassword;
  String upassword;
}
