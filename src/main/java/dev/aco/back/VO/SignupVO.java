package dev.aco.back.VO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SignupVO {
  Long memberId;
  String email;
  String password;
  String nickname;
  String name;
  String mobile;
  String userimg;
  MultipartFile file;
}
