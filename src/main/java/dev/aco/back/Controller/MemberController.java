package dev.aco.back.Controller;

import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService service;

  @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> signup(@RequestBody MemberDTO dto) {
    Boolean result = service.signUp(dto);
    if (result == false) {
      return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}