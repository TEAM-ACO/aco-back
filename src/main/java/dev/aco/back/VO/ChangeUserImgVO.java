package dev.aco.back.VO;

import org.springframework.web.multipart.MultipartRequest;

import lombok.Data;

@Data
public class ChangeUserImgVO {
  Long memberId;
  String userimg;
  MultipartRequest file;
}
