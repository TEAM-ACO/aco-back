package dev.aco.back.service.MemberService;

import java.util.HashMap;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.VO.ChangeFindPasswordVO;
import dev.aco.back.VO.ChangeNicknameVO;
// import dev.aco.back.VO.SetNicknameVO;
import dev.aco.back.VO.ChangePasswordVO;
import dev.aco.back.VO.ChangeUserImgVO;

public interface MemberSettingService {
  // 여기에 추가로 들어가야할것 내가 단 댓글갯수, 작성한 게시글 카운팅과 내프로필 이미지
  MemberDTO getByMemberId(Long memberId);

  Boolean changePassword(ChangePasswordVO vo);

  HashMap<String,Object> changeNickname(ChangeNicknameVO vo);

  Boolean changeUserImg(ChangeUserImgVO vo);

  Boolean changeFindPassword(ChangeFindPasswordVO vo);

  default MemberDTO entityToDTO(Member entity) {
    MemberDTO dto = MemberDTO.builder()
        .email(entity.getPassword())
        .password(entity.getPassword())
        .nickname(entity.getNickname())
        .name(entity.getName())
        .mobile(entity.getMobile())
        .userimg(entity.getUserimg())
        // articles, reply, articlereporter, memberreporter, reported, achieveLinker,
        // recommends
        .build();
    return dto;
  }

  default Member dtoToEntity(MemberDTO dto) {
    Member entity = Member.builder()
        .email(dto.getEmail())
        .password(dto.getPassword())
        .nickname(dto.getNickname())
        .name(dto.getName())
        .mobile(dto.getMobile())
        .userimg(dto.getUserimg())
        // articles, reply, articlereporter, memberreporter, reported, achieveLinker,
        // recommends
        .build();
    return entity;
  }
}
