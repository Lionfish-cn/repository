package com.code.repository.redis.config;

import com.code.repository.redis.service.RedisSubService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfiguration {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory , MessageListenerAdapter messageListenerAdapter){

        RedisMessageListenerContainer container  = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        //订阅频道
        container.addMessageListener(messageListenerAdapter,new PatternTopic("__keyevent@0__:expired"));//
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisSubService redisSubService){
        return new MessageListenerAdapter(redisSubService);

    }
}
