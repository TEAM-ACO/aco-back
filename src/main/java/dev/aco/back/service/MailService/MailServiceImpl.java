package dev.aco.back.service.MailService;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aco.back.Entity.User.emailAuth;
import dev.aco.back.Repository.MailRepository;
import dev.aco.back.Utils.Redis.RedisManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
  private final AcoMailSender mailSender;
  private final MailRepository mrepo;
  private final RedisManager redisManager;

  @Override
  public boolean sendEmail(String email) {
    // 인증번호 생성
    Integer authnum = (int) (Math.floor(Math.random() * 10000) - 1);
    // 인증 객체
    Optional<emailAuth> isCheck = mrepo.getByEmail(email);
    // 존재한다면
    if (isCheck.isPresent()) {
      mrepo.delete(isCheck.get());
    }
    mrepo.save(emailAuth.builder().email(email).authNum(authnum).isAuthrized(false).build());

    redisManager.setEmailData(email, authnum.toString());

    // 유효기간 설정
    // redis(key : email , value : authnum, expiration : 60*5L )
    redisManager.setDataExpire(email, authnum.toString(), 60 * 5L);

    // 메일 발송
    mailSender.send(email, "[ACO] 이메일 인증을 위한 인증번호를 안내드립니다.", "인증번호 : " + authnum);

    return true;
  }

  public boolean verifyEmail(String key) {
    String email = redisManager.getEmailData(key);
    if (email != null) {
      // redis에 EmailData가 있으면 삭제와 인증완료
      redisManager.deleteEmailData(key);
      return true;
    } else {
      // redis에 EmailData가 없으면 = 인증실패
      return false;
    }
  }

  @Override
  public boolean saveEmailAuth(String email) {
    mrepo.save(emailAuth.builder().email(email).isAuthrized(true).build());
    return true;
  }
}
