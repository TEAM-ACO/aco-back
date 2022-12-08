package dev.aco.back.Security.Handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Utils.JWT.JWTManager;
import dev.aco.back.Utils.Redis.RedisManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberRepository mrepo;
    private final RedisManager redisManager;
    private final JWTManager jwtManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Member member = mrepo.findByEmail(authentication.getName()).get();
        List<String> tokenList = jwtManager.AccessRefreshGenerator(member.getMemberId(), member.getEmail());
        redisManager.setRefreshToken(tokenList.get(1), member.getMemberId());

        ResponseCookie cookie1 = ResponseCookie.from("access", "Bearer%20" + tokenList.get(0)).path("/").build();
        ResponseCookie cookie2 = ResponseCookie.from("refresh", "Bearer%20" + tokenList.get(1)).path("/").build();

        response.addHeader("SetCookie", cookie1.toString());
        response.addHeader("SetCookie", cookie2.toString());
        
        
                //127.0.0.1:8080은 나중에 프론트 서버 주소로 변경해줍니다

        if(member.getNickname().length()==0 || member.getPassword().length()==0){
            Cookie noneinituser = new Cookie("user", "{%22id%22:%22"+member.getEmail()+"%22%2C%22num%22:"+member.getMemberId().toString()
            +"%2C%22username%22:%22"+"PleaseInitYourInformation" +"%22}");
            noneinituser.setPath("/");
            response.addCookie(noneinituser);
            response.sendRedirect("http://127.0.0.1:8080/initoauth");
        }else{
            Cookie user = new Cookie("user", "{%22id%22:%22" + member.getEmail() + "%22%2C%22num%22:" + member.getMemberId().toString()
            + "%2C%22username%22:%22" + URLEncoder.encode(member.getNickname(), "UTF-8") + "%22}");
            user.setPath("/");
            response.sendRedirect("http://127.0.0.1:8080/");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
