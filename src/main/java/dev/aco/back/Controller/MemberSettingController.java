package dev.aco.back.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.VO.MemberVO;
import dev.aco.back.VO.SetNicknameVO;
import dev.aco.back.VO.SetPasswordVO;
import dev.aco.back.service.MemberService.MemberSettingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
@Log4j2
public class MemberSettingController {
  private final MemberSettingService service;

  @RequestMapping(value = "/getmember", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberDTO> getByMemberId(@RequestBody MemberVO member) {
    MemberDTO result = service.getByMemberId(member.getMemberId());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @RequestMapping(value = "/changepassword", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> changePassword(@RequestBody SetPasswordVO vo) {
    log.info(vo);
    return new ResponseEntity<>(service.changePassword(vo), HttpStatus.OK);
  }

  @RequestMapping(value = "/changenickname", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> changeNickname(@RequestBody SetNicknameVO vo) {
    log.info(vo);
    return new ResponseEntity<>(service.changeNickname(vo), HttpStatus.OK);
  }
}