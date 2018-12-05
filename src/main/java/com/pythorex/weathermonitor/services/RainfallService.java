package com.pythorex.weathermonitor.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pythorex.weathermonitor.models.RainData;
import com.pythorex.weathermonitor.models.WeatherData;
import com.pythorex.weathermonitor.repository.DatabaseAccess;

@Service
public class RainfallService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RainfallService.class);

    @Autowired
    DatabaseAccess databaseAccess;
    
    //Returns the first and last WeatherData for a specific Timestamp range
    public RainData buildRainfallDataObject() {
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1; // Need to add 1 since Jan = 0
        int currentDay = cal.get(Calendar.DATE);
        Date currentDate = cal.getTime();
        Date[] rangeDates = new Date[3];
        
        try {
            rangeDates[0] = formatter.parse(currentDay + "-" + currentMonth + "-" + currentYear + " 00:00:00"); // startOfDay
            rangeDates[1] = formatter.parse("01-" + currentMonth + "-" + currentYear + " 00:00:00"); // startOfMonth
            rangeDates[2] = formatter.parse("01-" + "01-" + currentYear + " 00:00:00"); // startOfYear
        } catch (ParseException e){// TODO Auto-generated catch block
            LOGGER.error("A ParseException occurred when parsing the date!");
        }
        
        double[] rainFalls = this.getRainfallOverPeriod(rangeDates, currentDate);
        return new RainData(rainFalls[0], rainFalls[1], rainFalls[2]);
    }
    
    private double[] getRainfallOverPeriod(Date[] startDate, Date endDate) {

        double[] rainFalls = new double[3];
        List<List<WeatherData>> weatherDataList = new ArrayList<>();
        weatherDataList.add(databaseAccess.getRangedRainfall(startDate[0], endDate));
        weatherDataList.add(databaseAccess.getRangedRainfall(startDate[1], endDate));
        weatherDataList.add(databaseAccess.getRangedRainfall(startDate[2], endDate));
        
        int i = 0;
        for (List<WeatherData> singleList:weatherDataList) {
            if (singleList.size() < 2) {
                rainFalls[i] = 0.0;
            } else {
                WeatherData earliestDate = singleList.get(0);
                WeatherData latestDate = singleList.get(1);
                rainFalls[i] = (latestDate.getRainfall() - earliestDate.getRainfall());
                if (i == 2) {
                    rainFalls[i] = latestDate.getRainfall();
                }
            }
            i++;
        }
        return rainFalls;
    }
}
