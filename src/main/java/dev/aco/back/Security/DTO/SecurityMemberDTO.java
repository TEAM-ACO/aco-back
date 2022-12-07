package dev.aco.back.Security.DTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityMemberDTO extends User implements OAuth2User{
    Long memberId;
    String email;
    String password;
    String nickname;
    String oauth;
    Set<GrantedAuthority> authorities;

    public SecurityMemberDTO(Long memberId, String email, String password, String nickname,
            Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public SecurityMemberDTO(Long memberId, String email, String password, String nickname, String oauth,
            Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.memberId = memberId;
        this.email = email;
        this.oauth = oauth;
        this.password = password;
        this.nickname = nickname;
    }
    
    @Override
    public String getName() {
        return this.nickname;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }
}
