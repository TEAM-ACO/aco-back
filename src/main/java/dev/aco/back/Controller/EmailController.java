package dev.aco.back.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.User.emailAuthDTO;
import dev.aco.back.service.MailService.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member")
@Log4j2
public class EmailController {
  private final MailService mailService;

  @RequestMapping(value = "emailauth", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> emailRequest(@RequestBody emailAuthDTO dto) {
    log.info(dto);
    return new ResponseEntity<Boolean>(mailService.sendEmail(dto.getEmail()), HttpStatus.OK);
  }

  @RequestMapping(value = "/emailverify", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> verifyEmail(@RequestBody emailAuthDTO dto) {
    log.info(dto);
    return new ResponseEntity<Boolean>(mailService.verifyEmail(dto), HttpStatus.OK);
    Boolean result = mailService.verifyEmail(dto.getEmail());
    if (result == true) {
      // 찌꺼기를 남기지 않기 위해서
      mailService.saveEmailAuth(dto.getEmail());
    }
    return new ResponseEntity<Boolean>(result, HttpStatus.OK);
  }
}
