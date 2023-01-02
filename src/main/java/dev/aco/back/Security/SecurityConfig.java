package dev.aco.back.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.aco.back.Entity.Enum.Roles;
import dev.aco.back.Security.Filter.CustomLoginFilter;
import dev.aco.back.Security.Handler.CustomSuccessHandler;
import dev.aco.back.Security.Service.CustomLoginService;
import dev.aco.back.Security.Service.CustomOAuth2Service;
import lombok.RequiredArgsConstructor;

// 간단한 스프링 시큐리티 예제입니다
// 순차별로 설명해놓았으며 의문점이 생기신다면 PR에서 댓글달아주시면 됩니다
// 현재 시큐리티6버전으로 작성되었으며 그 전버전 참고는 구글링으로 해결하시면됩니다
// (그리 바뀐게 없기때문에 바로 가능합니다)

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2Service cOAuth2Service;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomLoginService customLoginService;

    // https://www.youtube.com/watch?v=TDOHbK39Oxg
    // https://github.com/B-HS/BBlog/blob/main/bblogback/src/main/java/dev/hyns/bblogback/Configs/SecurityConfig.java
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {

        // API서버이기에 csrf, httpbasic, login, logout, session 상태 제거
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 기본적 auth전략, 모든 하위주소를 허용하나
        // admin 주소일 경우 Admin주소가 없으면 요청을 거부합니다
        http.authorizeHttpRequests(auth -> {
            auth.anyRequest().permitAll();
        });

        //// 사이트로그인
        /// filterchain의 로그인 과정을 포함하는 UsernamePasswordAuthenticationFilter를 지정한 필터로
        //// 대체합니다

        // 로그인의 과정으로 설명하면
        // request > generate info > transfer generated info to authentication manager >
        // call authentication logics
        // 으 ㅣ순으로 위와 같습니다
        // UsernamePasswordAuthenticationFilter를 대체하기위해서는 전체 로그인 과정을 정의해줄 필요가있습니다
        // 이에따른 위의 과정을 전부 담을 수 있는, UsernamePasswordAuthenticationFilter가 상속받고있는
        // 추상클래스는 AbstractAuthenticationProcessingFilter 입니다
        // 이 클래스를 상속받아 진행할수도있고 아니면 따로 하나하나 만들어서 Generalfilter를 상속받아 작성하여 인증을 진행하는 방법도
        // 있습니다
        // 이해가 잘 안가신다면 아래의 링크를 참조하셔도 괜찮을듯합니다
        // https://velog.io/@yaho1024/Spring-Security-UsernamePasswordAuthenticationFilter%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0
        // https://programmer93.tistory.com/78
        // https://awse2050.tistory.com/98
        http.addFilterBefore(customLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        //// OAUTH2 인증
        // oauth2로그인의 엔드포인트를 oauth로 지정합니다 이로인해 "백엔드주소/oauth/벤더이름" 으로 접근이 가능해집니다
        // oauth2로그인의 비지니스로직(service)을 사용자정의 service로 변경합니다
        // oauth2로그인 성공 시의 핸들러를 사용자정의 handler로 설정합니다
        http.oauth2Login().authorizationEndpoint().baseUri("/oauth")
                .and().userInfoEndpoint().userService(cOAuth2Service)
                .and().successHandler(customSuccessHandler);
        ////
        return http.build();
    }

    // AuthenticationManager에 관해서
    // https://github.com/spring-projects/spring-framework/issues/29215#issuecomment-1263775300
    // https://github.com/spring-projects/spring-security/issues/12343
    // https://github.com/spring-projects/spring-boot/issues/32632
    // https://stackoverflow.com/questions/74694980/authenticationmanager-authenticate-throws-java-lang-stackoverflowerror-null-w/74706573#74706573
    // 사실 이렇게 안해도 지금은 userdetailservice가 하나이기에
    // 그냥 CustomLoginService를 @Service로 빈을 등록하면
    // authnticationconfiguration에서 지 알아서 서비스를 불러오긴한다
    // 그래도 추후에 여러 authentication manager를 분리해서 사용할 가능 성이있고 ..
    // 또한 위의 링크에서도 아래와 같은 방법을 제시했으며
    // 그냥 했을떄 버그가 많은 모양이니 .. 이러한 방식을 취하도록 하자

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customLoginService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }



    @Bean
    CustomLoginFilter customLoginFilter(AuthenticationManager authenticationManager) {
        CustomLoginFilter filter = new CustomLoginFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(customSuccessHandler);
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
