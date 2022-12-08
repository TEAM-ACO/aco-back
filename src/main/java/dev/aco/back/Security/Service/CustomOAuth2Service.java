package dev.aco.back.Security.Service;

import java.util.HashSet;
import java.util.Set;

import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import dev.aco.back.Entity.Enum.Roles;
import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Security.DTO.SecurityMemberDTO;
import dev.aco.back.Security.Vender.OAuthAttributes;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomOAuth2Service extends DefaultOAuth2UserService {
    private final MemberRepository mrepo;
    Member member;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User userinfo = super.loadUser(userRequest);
        Set<Roles> oauthRoles = new HashSet<>();
        oauthRoles.add(Roles.OAuth);
        oauthRoles.add(Roles.User);

        // 현재 진행중인 서비스를 구분하기 위해 문자열로 받음.
        // oAuth2UserRequest.getClientRegistration().getRegistrationId()에 값이 들어있다.
        // {registrationId='naver'} 이런식으로
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 시 키 값이 된다. 구글은 키 값이 "sub"이고, 네이버는 "response"이고, 카카오는 "id"이다. 각각
        // 다르므로 이렇게 따로 변수로 받아서 넣어줘야함.
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2 로그인을 통해 가져온 OAuth2User의 attribute를 담아주는 of 메소드.
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,userinfo.getAttributes());
        mrepo.findByEmail(userinfo.getAttribute("email")).ifPresentOrElse(
                v -> member = v,
                () -> mrepo.save(Member.builder()
                        .email(attributes.getEmail())
                        .name(attributes.getName())
                        .nickname("")
                        .password(attributes.getEmail()+attributes.getName())
                        .userimg(attributes.getPicture())
                        .oauth(registrationId)
                        .roleSet(oauthRoles)
                        .build()));
                        
                        
        return new SecurityMemberDTO(
            member.getMemberId(), 
            member.getEmail(), 
            member.getPassword(), 
            member.getNickname(), 
            member.getOauth(), 
            member.getRoleSet().stream().map(v-> new SimpleGrantedAuthority(v.toString())).collect(Collectors.toSet()));
    }
}
