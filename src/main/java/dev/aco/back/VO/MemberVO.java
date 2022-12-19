package dev.aco.back.VO;

// import java.util.Set;

import lombok.Data;

@Data
public class MemberVO {
  Long memberId;
  String email;
  String nickname;
  String name;
  String mobile;
  // Set<String> roleSet;
  String userimg;
}
