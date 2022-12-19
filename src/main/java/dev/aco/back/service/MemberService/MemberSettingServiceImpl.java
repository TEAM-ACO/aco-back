package dev.aco.back.service.MemberService;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Utils.Image.ImageManager;
import dev.aco.back.VO.ChangeNicknameVO;
// import dev.aco.back.VO.MemberVO;
// import dev.aco.back.VO.SetNicknameVO;
import dev.aco.back.VO.ChangePasswordVO;
import dev.aco.back.VO.ChangeUserImgVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSettingServiceImpl implements MemberSettingService {
  private final MemberRepository mrepo;
  private final MemberService mService;
  private final PasswordEncoder encoder;
  private final ImageManager imgManager;

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
  public Boolean changeNickname(ChangeNicknameVO vo) {
    mrepo.changeNicknamebyMemberId(vo.getMemberId(), vo.getNickname());
    return true;
  }

  @Override
  public Boolean changeUserImg(ChangeUserImgVO vo) {
    Optional<String> oldImg = mrepo.getUserImgByMemberId(vo.getMemberId());
    // 기존 사진이 존재할경우?
    if (oldImg.isPresent()) {
      mrepo.updateUserImg(vo.getUserimg(), vo.getMemberId());
    } else { // 기존 사진이 존재하지 않을경우 ==> 디폴트 이미지일것일텐데..
      imgManager.ImgUpload(vo.getFile());
    }
    return true;
  }
}
