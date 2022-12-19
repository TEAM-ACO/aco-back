package dev.aco.back.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.User.MemberDTO;

import dev.aco.back.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final MemberService mser;











    @PostMapping(value = "/signup")
    public ResponseEntity<Boolean> signup(@RequestBody MemberDTO dto){
        log.info(dto);
        return new ResponseEntity<Boolean>(mser.signUp(dto), HttpStatus.OK);
    }
    @PostMapping(value = "/signup/emailcheck")
    public ResponseEntity<Boolean> emailcheck(@RequestBody MemberDTO dto){
        return new ResponseEntity<Boolean>(mser.emailChecking(dto), HttpStatus.OK);
    }


}

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

