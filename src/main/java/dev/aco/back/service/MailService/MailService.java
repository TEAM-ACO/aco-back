package dev.aco.back.service.MailService;

public interface MailService {
  boolean sendEmail(String email);

  boolean saveEmailAuth(String email);

  boolean verifyEmail(String key);
}
