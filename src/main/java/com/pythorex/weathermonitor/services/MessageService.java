package com.pythorex.weathermonitor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pythorex.weathermonitor.controllers.WeatherSocketHandler;
import com.pythorex.weathermonitor.models.RainData;
import com.pythorex.weathermonitor.models.WeatherData;
import com.pythorex.weathermonitor.repository.DatabaseAccess;

@Controller
public class MessageService {
    
    @Autowired
    private RainfallService rainfallService;
    
    @Autowired
    private DatabaseAccess databaseAccess;
    
    public String returnCurrentWeatherDbData() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(databaseAccess.getLastWeatherDatum());
    }
    
    public String returnRainData() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(rainfallService.buildRainfallDataObject());
    }
}
