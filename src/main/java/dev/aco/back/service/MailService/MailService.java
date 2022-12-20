package dev.aco.back.service.MailService;

import dev.aco.back.DTO.User.emailAuthDTO;

public interface MailService {
  boolean sendEmail(String email);

  boolean saveEmailAuth(String email);

  boolean verifyEmail(emailAuthDTO dto);
}
