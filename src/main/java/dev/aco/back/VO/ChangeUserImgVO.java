package dev.aco.back.VO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ChangeUserImgVO {
  Long memberId;
  String userimg;
  MultipartFile file;
}
