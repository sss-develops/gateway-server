package goorm.project.gatewayserver.common.configuration.redis;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfiguration {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Primary
    @Bean("connectionFactory")
    public ReactiveRedisConnectionFactory connectionFactory() {
        RedisClusterConfiguration redisStandaloneConfiguration = new RedisClusterConfiguration();
        redisStandaloneConfiguration.clusterNode(host, port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }


    @Bean("redisOperations")
    public ReactiveRedisTemplate<String, String> redisOperations(@Qualifier("connectionFactory") ReactiveRedisConnectionFactory connectionFactory) {
        RedisSerializer<String> serializer = new StringRedisSerializer();
        RedisSerializationContext<String, String> serializationContext = RedisSerializationContext
                .<String, String>newSerializationContext()
                .key(serializer)
                .value(serializer)
                .hashKey(serializer)
                .hashValue(serializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

}
