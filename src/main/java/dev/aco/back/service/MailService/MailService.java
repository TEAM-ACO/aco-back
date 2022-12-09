package dev.aco.back.service.MailService;

public interface MailService {
  boolean mailCheckingRequest(String email);

  String mailCheckingNumCheckRequest(String email, Integer authNum);

  boolean verifyEmail(String key);
}
