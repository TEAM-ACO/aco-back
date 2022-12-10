package dev.aco.back.service.MailService;

public interface MailService {
  // 이름이 AcoMailSender 의 send 매서드와 햇갈리는데 뭐가 좋을까요? 상관없을까요?
  boolean sendEmail(String email);

  // String mailCheckingNumCheckRequest(String email, Integer authNum);

  boolean verifyEmail(String key);
}
