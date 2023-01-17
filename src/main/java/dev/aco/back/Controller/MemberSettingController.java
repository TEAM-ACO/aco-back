package dev.aco.back.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.VO.MemberVO;
import dev.aco.back.VO.ChangeFindPasswordVO;
import dev.aco.back.VO.ChangeNicknameVO;
import dev.aco.back.VO.ChangePasswordVO;
import dev.aco.back.VO.ChangeUserImgVO;
import dev.aco.back.service.MemberService.MemberSettingService;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
public class MemberSettingController {
  private final MemberSettingService service;

  @PostMapping(value = "/getmember", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberDTO> getByMemberId(@RequestBody MemberVO member) {
    MemberDTO result = service.getByMemberId(member.getMemberId());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping(value = "/changepassword", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordVO vo) {
    return new ResponseEntity<>(service.changePassword(vo), HttpStatus.OK);
  }

  @PostMapping(value = "/changenickname", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> changeNickname(@RequestBody ChangeNicknameVO vo) {
    HashMap<String, Object> result = service.changeNickname(vo);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Set-Cookie", result.get("access").toString());
    headers.add("Set-Cookie", result.get("refresh").toString());
    headers.add("Set-Cookie", result.get("user").toString());
    return new ResponseEntity<>((Boolean) result.get("result"), headers, HttpStatus.OK);
  }

  @PostMapping(value = "/changeuserimg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Boolean> changeUserImg(ChangeUserImgVO vo) {
    return new ResponseEntity<>(service.changeUserImg(vo), HttpStatus.OK);
  }

  @PostMapping(value = "/changefindpassword", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> changeFindPassword(@RequestBody ChangeFindPasswordVO vo) {
    return new ResponseEntity<>(service.changeFindPassword(vo), HttpStatus.OK);
  }

}