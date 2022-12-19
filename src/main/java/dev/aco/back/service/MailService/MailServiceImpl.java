package dev.aco.back.service.MailService;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aco.back.DTO.User.emailAuthDTO;
import dev.aco.back.Entity.User.emailAuth;
import dev.aco.back.Repository.MailRepository;
import dev.aco.back.Utils.Redis.RedisManager;
import jakarta.transaction.Transactional;
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

  @Transactional
  public boolean verifyEmail(emailAuthDTO dto) {
    // 현석 : 
    // redis키만 확인해놓아서 값도 비교할수있게끔 약간 수정해놔용
    // redisManager.getEmailData(dto.getEmail()); >> email값으로 저장한 authnum.toString()값을 불러옴
    // front에서 받은 dto에 담긴 email, authnum 기준으로 간단하게 확인
    // mrepo로 저장후 진행
    if (redisManager.getEmailData(dto.getEmail()).equals(dto.getAuthNum().toString())) {
      redisManager.deleteEmailData(dto.getEmail());
      mrepo.userAuthorized(dto.getEmail(), true);
  public boolean verifyEmail(String key) {
    String email = redisManager.getEmailData(key);
    if (email != null) {
      // redis에 EmailData가 있으면 삭제와 인증완료
      redisManager.deleteEmailData(key);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean saveEmailAuth(String email) {
    mrepo.save(emailAuth.builder().email(email).isAuthrized(true).build());
    return true;
  }
}
