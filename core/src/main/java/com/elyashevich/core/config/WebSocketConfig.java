package com.elyashevich.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.elyashevich.core.util.WebSocketConstant.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        config.enableSimpleBroker(DESTINATION_PREFIX);
        config.setApplicationDestinationPrefixes(PREFIX);
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(ENDPOINT).withSockJS();
    }
}
