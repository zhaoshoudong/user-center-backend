//package com.dong.usercenter.config;
//
//import com.dong.usercenter.util.MD5Util;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//
//
//@Configuration
//public class RedisAutoConfig {
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.password}")
//    private String password;
//    @Value("${spring.redis.database}")
//    private int database;
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration config) {
//        return new LettuceConnectionFactory(config);
//    }
//
//    @Bean
//    public RedisStandaloneConfiguration localRedisConfig() {
//        String reDigestPassword = MD5Util.reDigestDemo(password);
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        config.setHostName(host);
//        config.setPassword(RedisPassword.of(reDigestPassword));
//        config.setPort(port);
//        config.setDatabase(database);
//        return config;
//    }
//}
