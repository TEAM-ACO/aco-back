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
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberRepository mrepo;
    private final RedisManager redisManager;
    private final JWTManager jwtManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Member member = mrepo.findByEmail(authentication.getName()).get();
        log.info(member.getNickname());
        List<String> tokenList = jwtManager.AccessRefreshGenerator(member.getMemberId(), member.getEmail(), member.getUserimg());
        redisManager.setRefreshToken(tokenList.get(1), member.getMemberId());

        // 요건 혹시 몰라서 살려둘게용
        // HashMap<String, Object> result = new HashMap<>();
        // result.put("access", tokenList.get(0));
        // result.put("refresh", tokenList.get(1));
        // result.put("email", member.getEmail());
        // result.put("memberid", member.getMemberId());
        // result.put("nickname", member.getNickname());
        // response.setContentType("application/json");
        // PrintWriter out = response.getWriter();
        // out.print(result);
        // out.flush();


        
        ResponseCookie cookie1 = ResponseCookie.from("access", "Bearer%20" + tokenList.get(0)).path("/").build();
        ResponseCookie cookie2 = ResponseCookie.from("refresh", "Bearer%20" + tokenList.get(1)).path("/").build();
                log.info(cookie1);
                log.info(cookie2);
        response.addHeader("Set-Cookie", cookie1.toString());
        response.addHeader("Set-Cookie", cookie2.toString());

        if(member.getNickname().length()==0 || member.getPassword().length()==0){
            Cookie noneinituser = new Cookie("user", "{%22id%22:%22"+member.getEmail()+"%22%2C%22num%22:"+member.getMemberId().toString()
            +"%2C%22username%22:%22"+"PleaseInitYourInformation" +"%22}");
            noneinituser.setPath("/");
            response.addCookie(noneinituser);
            response.sendRedirect("http://localhost:3075/initoauth");
        }else{
            Cookie user = new Cookie("user", "{%22id%22:%22" + member.getEmail() + "%22%2C%22num%22:" + member.getMemberId().toString()
            + "%2C%22username%22:%22" + URLEncoder.encode(member.getNickname(), "UTF-8") + "%22}");
            user.setPath("/");
            response.addCookie(user);
            response.sendRedirect("http://localhost:3075/");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
