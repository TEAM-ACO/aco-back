package dev.aco.back.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.MemberDTO;
import dev.aco.back.VO.signUp;
import dev.aco.back.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService service;

  // @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes =
  // MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

}
