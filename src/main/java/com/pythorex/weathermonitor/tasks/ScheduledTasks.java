package com.pythorex.weathermonitor.tasks;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pythorex.weathermonitor.controllers.RainSocketHandler;
import com.pythorex.weathermonitor.controllers.WeatherSocketHandler;
import com.pythorex.weathermonitor.services.FileService;
import com.pythorex.weathermonitor.services.MessageService;

@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private FileService fileService;
    @Autowired
    private WeatherSocketHandler weatherSocketHandler;
    @Autowired
    private RainSocketHandler rainSocketHandler;
    @Autowired
    private MessageService messageService;
    
    private long lastProcessedFileTime = -1L;

    private boolean processFileAndUpdateSocketIfNew() {
        long currentfilesModificationTime = fileService.fileLastModified();
        boolean successfullyProcessed = false;
        
        if (currentfilesModificationTime != lastProcessedFileTime) {
            try {
                fileService.processFile();
                weatherSocketHandler.sendWeatherData(messageService.returnCurrentWeatherDbData());
                rainSocketHandler.sendRainData(messageService.returnRainData());
                lastProcessedFileTime = currentfilesModificationTime;
                successfullyProcessed = true;
            } catch(IOException ioEx) {
                LOGGER.error("Reading of the Weather Station File Failed");
            }
        }
        return successfullyProcessed;
    }
    
    @Scheduled(cron = "${scheduler.filecheck.cron}")
    public void pullRecentWeatherData() {
        
        LOGGER.info("Attemping to process Weather Station File Data at: " + new Date());
        if (fileService.doesFileExist()) {
            if (processFileAndUpdateSocketIfNew()) {
                LOGGER.info("The Acu-rite datafile was processed.");
            } else {
                LOGGER.info("The Acu-rite datafile wasn't processed.");
            }
        } else {
            LOGGER.error("The Acu-rite datafile does not Exist");
        }
    }
}