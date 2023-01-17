package dev.aco.back.service.MemberService;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Utils.Image.ImageManager;
import dev.aco.back.Utils.JWT.JWTManager;
import dev.aco.back.Utils.Redis.RedisManager;
import dev.aco.back.VO.ChangeFindPasswordVO;
import dev.aco.back.VO.ChangeNicknameVO;
// import dev.aco.back.VO.MemberVO;
// import dev.aco.back.VO.SetNicknameVO;
import dev.aco.back.VO.ChangePasswordVO;
import dev.aco.back.VO.ChangeUserImgVO;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSettingServiceImpl implements MemberSettingService {
  private final MemberRepository mrepo;
  private final MemberService mService;
  private final PasswordEncoder encoder;
  private final ImageManager imgManager;
  private final JWTManager jwtManager;
  private final RedisManager redisManager;

  @Override
  public MemberDTO getByMemberId(Long memberId) {
    Member entity = mrepo.findByMemberId(memberId).get();
    MemberDTO dto = mService.entityToDTO(entity);
    return dto;
  }

  @Override
  public Boolean changePassword(ChangePasswordVO vo) {
    Optional<Member> result = mrepo.findByMemberId(vo.getMemberId());
    if (encoder.matches(vo.getCpassword(), result.get().getPassword())) {
      mrepo.changePassbyMemberId(vo.getMemberId(), encoder.encode(vo.getUpassword()));
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public HashMap<String, Object> changeNickname(ChangeNicknameVO vo) {
    HashMap<String, Object> result = new HashMap<>();
    try {
      mrepo.changeNicknamebyMemberId(vo.getMemberId(), vo.getNickname());
      Member member = mrepo.findById(vo.getMemberId()).orElseThrow();
      List<String> tokn = jwtManager.AccessRefreshGenerator(member.getMemberId(), member.getEmail(),
          member.getUserimg());
      redisManager.setRefreshToken(tokn.get(1), member.getMemberId());
      result.put("access", ResponseCookie.from("access", "Bearer%20" + tokn.get(0)).path("/").build().toString());
      result.put("refresh", ResponseCookie.from("refresh", "Bearer%20" + tokn.get(1)).path("/").build().toString());
      result.put("user", ResponseCookie.from("user", "{%22id%22:%22" + member.getEmail() + "%22%2C%22num%22:" + member.getMemberId().toString()
      + "%2C%22username%22:%22" + URLEncoder.encode(member.getNickname(), "UTF-8") + "%22}").path("/").build().toString());
      

      result.put("result", true);
    } catch (Exception e) {
      result.put("result", false);
    }

    return result;
  }

  @Override
  public Boolean changeUserImg(ChangeUserImgVO vo) {
    Optional<String> oldImg = mrepo.getUserImgByMemberId(vo.getMemberId());
    if (oldImg.isPresent()) {
      mrepo.updateUserImg(vo.getUserimg(), vo.getMemberId());
    } else {
      imgManager.ImgUpload(vo.getFile());
    }
    return true;
  }

  @Override
  public Boolean changeFindPassword(ChangeFindPasswordVO vo) {
    // Optional<Member> result = mrepo.findByEmail(vo.getEmail());
    mrepo.changeFindPassbyEmail(vo.getEmail(), encoder.encode(vo.getUpassword()));
    return true;
  }
}
