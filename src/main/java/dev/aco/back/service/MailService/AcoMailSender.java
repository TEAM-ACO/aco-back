package dev.aco.back.service.MailService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

// import dev.aco.back.Utils.Redis.RedisManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class AcoMailSender {
  // private final JavaMailSender mailSender;
  // private final RedisManager redisManager;

  @Value("${spring.mail.username}")
  private String FROM_ADDRESS;

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private String port;

  // context = "[ACO] 이메일 인증을 위한 인증번호를 안내드립니다.", "인증번호 : " + authnum
  public void send(String mailAdress, String title, String context) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailAdress);
    message.setFrom(FROM_ADDRESS);
    message.setSubject(title);
    message.setText(context);
    // 메일 전송
    // mailSender.send(message);
    // redis에 email 값 + 유효기간 저장
    // redisManager.setDataExpire(key, value, expiration : 60*5L);
    log.info("메일이 발송되었습니다" + message);
  }
}
