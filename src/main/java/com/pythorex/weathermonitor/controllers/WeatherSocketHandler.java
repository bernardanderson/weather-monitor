package com.pythorex.weathermonitor.controllers;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.pythorex.weathermonitor.services.MessageService;

@Component
public class WeatherSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherSocketHandler.class);
    
    @Autowired
    private MessageService messageService;
    
    WebSocketSession session;
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Weather Socket Connection Established");
        this.session = session;
        this.sendWeatherData(messageService.returnCurrentWeatherDbData());
    }
    
    public void sendWeatherData(String weatherJson) {
        
        try {
            if (session != null) {
                this.session.sendMessage(new TextMessage(weatherJson));
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            LOGGER.info("No client is connected to weather channel at this time");
        }
    }
}

