package com.pythorex.weathermonitor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.pythorex.weathermonitor.controllers.RainSocketHandler;
import com.pythorex.weathermonitor.controllers.WeatherSocketHandler;

@Configuration
@EnableWebSocket
public class WeatherSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    WeatherSocketHandler weatherSocketHandler;
    
    @Autowired
    RainSocketHandler rainSocketHandler;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(weatherSocketHandler, "/weather-websocket").withSockJS();
        registry.addHandler(rainSocketHandler, "/rain-websocket").withSockJS();
    }
}
