package dev.aco.back.service.MemberService;

import java.util.function.Function;
import java.util.stream.Collectors;

import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.Entity.Enum.Roles;
import dev.aco.back.Entity.User.Member;

public interface MemberService {
  Boolean signUp(MemberDTO dto);
  Boolean emailChecking(MemberDTO dto);
  byte[] getImageByMemberId(Long memberId);
  Boolean memberResign(MemberDTO dto);

  // Long findPassword(findPassword vo);
  // String changePassword(changePassword vo);

  default MemberDTO entityToDTO(Member entity) {
    MemberDTO dto = MemberDTO.builder()
        .email(entity.getEmail())
        .password(entity.getPassword())
        .nickname(entity.getNickname())
        .name(entity.getName())
        .mobile(entity.getMobile())
        .roleSet(entity.getRoleSet().stream().map(new Function<Roles, String>() {
          @Override
          public String apply(Roles r) {
            return new String("ROLE_" + r.name());
          }
        }).collect(Collectors.toSet()))
        .oauth(entity.getOauth())
        .logged(entity.getLogged())
        .userimg(entity.getUserimg())
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
        .oauth(dto.getOauth())
        .logged(dto.getLogged())
        .roleSet(dto.getRoleSet().stream().map(v->Roles.valueOf(v)).collect(Collectors.toSet()))
        .userimg(dto.getUserimg())
        .build();
    return entity;
  }
}
