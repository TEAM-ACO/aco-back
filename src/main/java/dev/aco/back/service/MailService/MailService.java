package dev.aco.back.service.MailService;

public interface MailService {
  boolean sendEmail(String email);

  // String mailCheckingNumCheckRequest(String email, Integer authNum);

  boolean verifyEmail(String key);
}
