package dev.aco.back.Security.Filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Utils.JWT.JWTManager;
import dev.aco.back.Utils.Redis.RedisManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserFilter extends OncePerRequestFilter {
    private final JWTManager jwtManager;
    private final RedisManager redisManager;
    private final MemberRepository mrepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        AntPathMatcher matcher = new AntPathMatcher();

        Boolean PathChecker = Arrays.asList(new String[] {
                "/mypage"
                //이러면 안되는거 알지만.. 일단.. 토큰체크할 경로
                
        }).stream().anyMatch(v -> {
            if (matcher.match(request.getContextPath() + v, request.getRequestURI())
                    || matcher.match(v, request.getRequestURI())) {
                return true;
            } else {
                return false;
            }
        });

        if (PathChecker) {
            Member rtknMember = mrepo
                    .findById(Long.parseLong(jwtManager.tokenParser(request.getHeader("htk")).get("userNumber").toString())).orElseThrow();
            Boolean atkBool = jwtManager.tokenValidator(request.getHeader("atk"));
            Boolean bothCheck = redisManager.TokenValidator(request.getHeader("atk"), request.getHeader("htk")) && atkBool;
            if (bothCheck) {
                filterChain.doFilter(request, response);
            } else{
                response.sendRedirect("/login");
                filterChain.doFilter(request, response);
            }

            if (!atkBool){
                List<String> tokenList = jwtManager.AccessRefreshGenerator(rtknMember.getMemberId(), rtknMember.getEmail());
                redisManager.setRefreshToken(tokenList.get(1), rtknMember.getMemberId());

                if(rtknMember.getNickname().length()==0 || rtknMember.getPassword().length()==0){
                    Cookie noneinituser = new Cookie("user", "{%22id%22:%22"+rtknMember.getEmail()+"%22%2C%22num%22:"+rtknMember.getMemberId().toString()
                    +"%2C%22username%22:%22"+"PleaseInitYourInformation" +"%22}");
                    noneinituser.setPath("/");
                    response.addCookie(noneinituser);
                    response.sendRedirect("http://localhost:3075/initoauth");
                }else{
                    Cookie user = new Cookie("user", "{%22id%22:%22" + rtknMember.getEmail() + "%22%2C%22num%22:" + rtknMember.getMemberId().toString()
                    + "%2C%22username%22:%22" + URLEncoder.encode(rtknMember.getNickname(), "UTF-8") + "%22}");
                    user.setPath("/");
                    response.addCookie(user);
                }
                filterChain.doFilter(request, response);
            }

        }
        filterChain.doFilter(request, response);

    }
}
