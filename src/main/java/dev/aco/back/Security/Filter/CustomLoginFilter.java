package dev.aco.back.Security.Filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.StandardCharset;

import dev.aco.back.VO.LoginVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    public CustomLoginFilter() {
        super(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        
        ObjectMapper objMapper = new ObjectMapper();
        LoginVO loginData = objMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharset.UTF_8), LoginVO.class);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword()));
    }
}
