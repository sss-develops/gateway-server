package goorm.project.gatewayserver.business.web.client.redis;

import goorm.project.gatewayserver.common.domain.error.CommonTypeException;
import goorm.project.gatewayserver.common.exception.common.LoginForbiddenException;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class SessionStoreService implements RedisSessionService{

    private final ReactiveRedisOperations<String, String> redisOperations;

    public SessionStoreService(ReactiveRedisOperations redisOperations) {
        this.redisOperations = redisOperations;
    }

    @Override
    @Transactional
    public void isLoginUser(Long memberId) {
        Mono<String> session = redisOperations.opsForValue().get(getSessionsKey(memberId));

        if (session == null) {
            throw LoginForbiddenException.of(CommonTypeException.NOT_FOUND_SESSION_MEMBER);
        }
    }

    private String getSessionsKey(Long memberId) {
        return String.format("member:id:%s:sessions", memberId);
    }
}
