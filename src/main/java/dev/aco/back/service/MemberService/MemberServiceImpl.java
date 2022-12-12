package dev.aco.back.service.MemberService;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.User.emailAuth;
import dev.aco.back.Repository.MailRepository;
import dev.aco.back.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final MemberRepository repo;
  private final PasswordEncoder encoder;
  private final MailRepository mrepo;

  @Transactional
  @Override
  public String signUp(MemberDTO dto) {
    dto.setPassword(encoder.encode(dto.getPassword()));
    Optional<emailAuth> resultCheck = mrepo.getByEmail(dto.getEmail());
    if (resultCheck.get().getEmail().length() > 0 && resultCheck.get().getIsAuthrized() == true) {
      repo.save(dtoToEntity(dto));
      mrepo.delete(resultCheck.get());
      return "회원가입에 성공하였습니다.";
    } else {
      return "회원가입에 실패하였습니다.";
    }
  }
}
