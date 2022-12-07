package dev.aco.back.Security.Service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Security.DTO.SecurityMemberDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomLoginService implements UserDetailsService {

    private final MemberRepository mrepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = mrepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new SecurityMemberDTO(
            member.getMemberId(), 
            member.getEmail(), 
            member.getPassword(), 
            member.getNickname(), 
            member.getRoles().stream().map(v-> new SimpleGrantedAuthority(v.toString())).collect(Collectors.toSet()));
    }
}
