package dev.aco.back.Utils.Redis;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import dev.aco.back.Entity.User.Member;
import dev.aco.back.Repository.MemberRepository;
import dev.aco.back.Utils.JWT.JWTManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisManager {
    private final RedisTemplate<String, String> redisTemplate;
    private final MemberRepository mrepo;
    private final JWTManager manager;

    @Transactional
    public void setRefreshToken(String token, Long mid) {
        ValueOperations<String, String> refreshToken = redisTemplate.opsForValue();
        refreshToken.set(token, mid.toString(), Duration.ofDays(7L));
        mrepo.loggedMember(mid, true);
    }

    @Transactional
    public void removeRefreshToken(String refreshToken) {
        Optional<String> mid = Optional.ofNullable(redisTemplate.opsForValue().get(refreshToken));
        if (mid.isPresent()) {
            mrepo.loggedMember(Long.parseLong(mid.get()), false);
            redisTemplate.delete(refreshToken);
        }
    }

    @Transactional
    public Boolean TokenValidator(String accessToken, String refreshToken) {
        Optional<Member> targetMember = mrepo
                .findById(Long.parseLong(manager.tokenParser(accessToken).get("userNumber").toString()));
        Long accessTokenMemberId = targetMember.orElse(Member.builder().memberId(0L).build()).getMemberId();
        Long refreshTokenMemberId = Long
                .parseLong(Optional.ofNullable(redisTemplate.opsForValue().get(refreshToken)).orElse("-1"));
        if (refreshTokenMemberId == accessTokenMemberId & manager.tokenValidator(refreshToken)
                & isLogged(accessToken)) {
            return true;
        } else {
            removeRefreshToken(refreshToken);
            mrepo.loggedMember(refreshTokenMemberId, false);
            return false;
        }
    }

    public Boolean refreshTokenChecker(String refreshToken) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(refreshToken)).isPresent() ? true : false;
    }

    public Boolean isLogged(String aToken) {
        return mrepo.findById(Long.parseLong(manager.tokenParser(aToken).get("userNumber").toString()))
                .orElse(Member.builder().memberId(0L).logged(false).build()).getLogged();
    }

    // Q1. 이 클래스에 아래 코드를 작성하는게 맞나요? 아니면 따로 클래스를 만들어야 할까요?

    // 이메일인증 (저장, 유효기간 설정, 삭제)
    // Redis에 들어가는 DATA (Key : authnum / Value : email)
    public String getEmailData(String key) { // authnum 을 통해 email을 얻는다.
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setEmailData(String key, String value) { // authnum 을 통해 email을 얻는다.
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    // Redis에 저장하려고하다보니 유효기간 저장하는부분에서 문제가 생겼습니다.
    // 검색해보면서 이런식으로 저장을 하는걸 보고 제가 이해하는 선에서 적어봤습니다.
    // 이것이 문제가 될지 잘 모르겠습니다. 만약 이것을 사용한다면 기존 MailService에서 작성한
    // 유효기간 관련된 코드는 사용할 필요가 없을것 같음.
    public void setDataExpire(String key, String value, long expiration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // expireDuaration 동안 (key, value)를 저장한다.
        Duration expireDuration = Duration.ofMillis(expiration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteEmailData(String key) {
        // 데이터 삭제
        redisTemplate.delete(key);
    }
}
