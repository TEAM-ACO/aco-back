// package dev.aco.back.service.MailService;

// import java.time.LocalDateTime;
// import java.time.temporal.ChronoUnit;
// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import dev.aco.back.Entity.User.emailAuth;
// import dev.aco.back.Repository.MailRepository;
// import dev.aco.back.Utils.Redis.RedisManager;
// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class MailServiceImpl implements MailService {
// private final AcoMailSender mailSender;
// private final MailRepository mrepo;
// private final RedisManager redisManager;

// @Override
// public boolean mailCheckingRequest(String email) {

// // 인증번호 생성
// Integer authnum = (int) (Math.floor(Math.random() * 10000) - 1);
// // 인증 객체
// Optional<emailAuth> isCheck = mrepo.getByEmail(email);
// // 존재한다면
// if (isCheck.isPresent()) {
// mrepo.delete(isCheck.get());
// }
// mrepo.save(emailAuth.builder().email(email).authNum(authnum).isAuthrized(false).build());
// mailSender.send(email, "[ACO] 이메일 인증을 위한 인증번호를 안내드립니다.", "인증번호 : " +
// authnum);
// //여기서 redisManager.setDataExpire()매서드 실행하면서 유효기간 저장함.
// return true;
// }

// @Override
// @Transactional
// public String mailCheckingNumCheckRequest(String email, Integer authNum) {
// Optional<emailAuth> isCheck = mrepo.getByEmail(email);
// if (isCheck.isPresent()) {
// LocalDateTime now = LocalDateTime.now();
// LocalDateTime pastTime = isCheck.get().getCreatedDateTime().plusMinutes(3);
// if (ChronoUnit.SECONDS.between(now, pastTime) <= 0) {
// return "인증시간이 만료되었습니다";
// }
// if (isCheck.get().getAuthNum().intValue() == authNum.intValue() &&
// isCheck.get().getIsAuthrized() == false) {
// mrepo.updateAuthTime(isCheck.get().getEauthId(),
// isCheck.get().getCreatedDateTime().plusMinutes(60));
// return "인증이 완료되었습니다 1시간 이내에 가입을 완료해주세요";
// } else if (isCheck.get().getAuthNum().intValue() == authNum.intValue()
// && isCheck.get().getIsAuthrized() == true) {
// return "이미 인증된 이메일입니다 가입을 완료해주세요";
// } else {
// return "인증 번호가 잘못되었습니다";
// }
// } else {
// return "잘못된 접근입니다";
// }
// }

// //레퍼런스 참고하면서 임시로 만든 매서드 입니다. 이런식으로 되야하는지 잘 모르겠습니다.
// public boolean verifyEmail(String key){
// String email = redisManager.getEmailData(key);
// if(email == null){
// redisManager.deleteEmailData(key);
// return false;
// }
// return true;
// }
// }
