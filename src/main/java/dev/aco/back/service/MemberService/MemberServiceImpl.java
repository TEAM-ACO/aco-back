package dev.aco.back.service.MemberService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Enum.Roles;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Entity.User.emailAuth;
import dev.aco.back.Repository.MailRepository;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Utils.Image.ImageManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
  private final MemberRepository repo;
  private final PasswordEncoder encoder;
  private final MailRepository mrepo;
  private final ImageManager imageManager;

  @Override
  public Boolean emailChecking(MemberDTO dto) {
    return repo.existsByEmail(dto.getEmail());
  }

  @Override
  @Transactional
  public Boolean memberResign(MemberDTO dto) {
      Member member = repo.findById(dto.getMemberId()).orElse(Member.builder().memberId(-1L).password("-1").build());
      if(encoder.matches(dto.getPassword(), member.getPassword())){
        repo.delete(member);
        return true;
      }
      return false;
  }

  @Override
  public byte[] getImageByMemberId(Long memberId) {
    String imgName = repo.findById(memberId).orElse(Member.builder().userimg("basic.png").build()).getUserimg();
    return (byte[])imageManager.ImgRead(imgName).get(0);
  }

  @Transactional
  @Override
  public Boolean signUp(MemberDTO dto) {

    Set<String> userole = new HashSet<>();
    userole.add(Roles.User.toString());
    Optional<emailAuth> resultCheck = mrepo.getByEmail(dto.getEmail());
    if (resultCheck.get().getEmail().length() > 0 && resultCheck.get().getIsAuthrized() == true) {
      dto.setPassword(encoder.encode(dto.getPassword()));
      dto.setOauth("site");
      dto.setRoleSet(userole);
      dto.setLogged(false);
      dto.setUserimg("basic.png");
      repo.save(dtoToEntity(dto));
      mrepo.delete(resultCheck.get());
      return true;
    } else {
      return false;
    }
  }
}
