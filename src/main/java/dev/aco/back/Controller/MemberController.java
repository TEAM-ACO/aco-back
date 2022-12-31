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
        log.info("======================"+dto.getEmail());
        return new ResponseEntity<Boolean>(mser.emailChecking(dto), HttpStatus.OK);
    }


}



