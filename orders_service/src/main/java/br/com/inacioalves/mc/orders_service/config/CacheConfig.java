package br.com.inacioalves.mc.orders_service.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import br.com.inacioalves.mc.orders_service.model.Cart;

@Configuration
@EnableCaching
public class CacheConfig {
   
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public RedisTemplate<String, Cart> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, Cart> redisTemplate = new RedisTemplate<String, Cart>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

   
}