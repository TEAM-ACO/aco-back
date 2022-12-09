package dev.aco.back.service.MailService;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aco.back.Entity.User.emailAuth;
import dev.aco.back.Repository.MailRepository;
import dev.aco.back.Utils.Redis.RedisManager;
// import jakarta.transaction.Transactional;
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

    // redisManager 에 요걸 set 해줘야할 것 같은데 제가 참고한 코드에서는 이부분이 누락되어있더라구요. 이해가 잘 가질 않습니다
    // 피드백 부탁드립니다.
    redisManager.setEmailData(email, authnum.toString());

    // 유효기간 설정
    // redis(key : email , value : authnum, expiration : 60*5L )
    redisManager.setDataExpire(email, authnum.toString(), 60 * 5L);

    // 메일 발송
    mailSender.send(email, "[ACO] 이메일 인증을 위한 인증번호를 안내드립니다.", "인증번호 : " + authnum);

    return true;
  }

  // Transactional 어노테이션이 필요한거같은데 맞을까요?
  // 검색해봤는데 이해가 아직 잘 가질 않습니다 T-T
  public boolean verifyEmail(String key) {
    String email = redisManager.getEmailData(key);
    if (email != null) {
      // redis에 EmailData가 있으면 삭제와 인증완료
      redisManager.deleteEmailData(key);
      // 요렇게 저장을 해야 될것 같은데 피드백 부탁드립니다.
      mrepo.save(emailAuth.builder().email(email).isAuthrized(true).build());
      return true;
    } else {
      // redis에 EmailData가 없으면 = 인증실패
      return false;
    }
  }
}
