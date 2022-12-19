package dev.aco.back.service.MemberService;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.VO.ChangeNicknameVO;
// import dev.aco.back.VO.SetNicknameVO;
import dev.aco.back.VO.ChangePasswordVO;

public interface MemberSettingService {
  MemberDTO getByMemberId(Long memberId);

  Boolean changePassword(ChangePasswordVO vo);

  Boolean changeNickname(ChangeNicknameVO vo);

  // void uploadProfileImg(Long memberId, String fileName);

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
