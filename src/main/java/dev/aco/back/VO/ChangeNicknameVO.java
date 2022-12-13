package dev.aco.back.VO;

import lombok.Data;

@Data
public class ChangeNicknameVO {
  Long memberId;
  String nickname;
}
