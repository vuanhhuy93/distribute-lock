package com.asim.curd_demo.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

@Configuration
@ComponentScan("com.asim.curd_demo.redis.entities")
@EnableRedisRepositories(basePackages = "com.asim.curd_demo.redis.repositories")
public class RedisConfig {
    @Autowired
    Environment env;
    @Bean
    RedisTemplate<String, Object> redisTemplate() throws IOException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redissonConnectionFactory());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return template;
    }
    @Bean
    public RedissonConnectionFactory redissonConnectionFactory() throws IOException {
        return new RedissonConnectionFactory(createdBean());
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient createdBean() throws IOException {

        String fileConfigUrl = "./config/singleNodeConfig.yaml";

        if (env.getProperty("redis.config") != null){
            fileConfigUrl = env.getProperty("redis.config");
        }
        Config config = Config.fromYAML(new File(fileConfigUrl));
        RedissonClient client = Redisson.create(config);
        return client;
    }
}