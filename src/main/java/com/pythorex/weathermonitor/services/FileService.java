package com.pythorex.weathermonitor.services;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pythorex.weathermonitor.models.WeatherData;
import com.pythorex.weathermonitor.repository.DatabaseAccess;

@Service
public class FileService {

    @Autowired
    DatabaseAccess databaseAccess;
    
    @Value("${file.mainstation.location}")
    private String mainStationPath;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    
    public boolean doesFileExist() {
        File mainStationFile = FileUtils.getFile(mainStationPath);
        return mainStationFile.exists();
    }
    
    public long fileLastModified() {
        long fileTimeStamp = -1L;
        
        File mainStationFile = FileUtils.getFile(mainStationPath);
        
        if (mainStationFile.exists()) {
            fileTimeStamp = mainStationFile.lastModified();
        }
        return fileTimeStamp;
    }
    
    public List<String[]> readFileData() throws IOException {
        List<String[]> fileLineList = new ArrayList<>();

        File mainStationFile = FileUtils.getFile(mainStationPath);
        
        FileUtils.readLines(mainStationFile, "UTF-8").stream()
                 .filter(line -> line.contains("/"))
                 .map(line -> line + "," + DigestUtils.sha1Hex(line))
                 .map(line -> line.replace("\"", ""))
                 .map(line -> line.split(","))
                 .forEach(stringArray -> {
                     stringArray[0] = dataTimeTransformer(stringArray[0]);
                     fileLineList.add(stringArray);
                 });
        return fileLineList;
    }
    
    // Converts the Accurite 12hr Time String "MM/DD/YYYY hh:mm:ss AM|PM" into a Valid 24-hour TimeStamp "YYYY-MM-DD hh:mm:ss"
    private String dataTimeTransformer (String stringDate) {
        
        List<String> tList = new ArrayList<>();
        Pattern regexExpression = Pattern.compile("\\d{1,4}+|(AM|PM)");
        Matcher matcher = regexExpression.matcher(stringDate);

        while (matcher.find()) {
            tList.add(matcher.group());
        }
        
        if (tList.get(6).equals("PM")) {
            if (!tList.get(3).equals("12")) {
                tList.set(3, Integer.toString((Integer.parseInt(tList.get(3)) + 12)));
            }
        } else if (tList.get(3).equals("12")) {
            tList.set(3, "00");
        }
        String newTimestamp = tList.get(2) + "-" + tList.get(0) + "-" + tList.get(1) + " " + tList.get(3) + ":" + tList.get(4) + ":" + tList.get(5);
        return newTimestamp;
    }
    
    private int generateDewpoint(String insideTempInF, String insideHumidity) {
        double tempInC = (Double.parseDouble(insideTempInF)-32.0)*(5.0/9.0);
        double mathInsideHumidity = Double.parseDouble(insideHumidity);
        double dewpointNumerator = (243.04 * (Math.log(mathInsideHumidity/100)+((17.625*tempInC)/(243.04+tempInC))));
        double dewpointDenominator = (17.625-Math.log(mathInsideHumidity/100)-((17.625*tempInC)/(243.04+tempInC)));
        return (int) Math.round((dewpointNumerator/dewpointDenominator)*1.8+32);
    }
    
    public WeatherData mapToWeatherDataObject(String[] weatherDataArray) {
        WeatherData tempWdObject = new WeatherData(
                Timestamp.valueOf(weatherDataArray[0]),
                Float.parseFloat(weatherDataArray[1]),
                Integer.parseInt(weatherDataArray[2]),
                Integer.parseInt(weatherDataArray[3]),
                Integer.parseInt(weatherDataArray[4]),
                Integer.parseInt(weatherDataArray[5]),
                Float.parseFloat(weatherDataArray[6]),
                Float.parseFloat(weatherDataArray[7]),
                Float.parseFloat(weatherDataArray[8]),
                Float.parseFloat(weatherDataArray[9]),
                Float.parseFloat(weatherDataArray[10]),
                Float.parseFloat(weatherDataArray[11]),
                Float.parseFloat(weatherDataArray[12]),
                Float.parseFloat(weatherDataArray[13]),
                generateDewpoint(weatherDataArray[12], weatherDataArray[13]),
                weatherDataArray[14]
                );
        return tempWdObject;
    }
    
    public List<WeatherData> getNewWeatherData (List<String[]> fileLineList) {
        List<String> sha1List = databaseAccess.getGetSha1List();
        List<WeatherData> dataToBeInserted = new ArrayList<>();
        for (String[] singleRow:fileLineList) {
            if (Collections.disjoint(Arrays.asList(singleRow), sha1List)){
                dataToBeInserted.add(mapToWeatherDataObject(singleRow));
            }
        }
        return dataToBeInserted;
    }
    
    public void processFile() throws IOException {
        
        List<String[]> currentWeatherData = this.readFileData();
        List<WeatherData> newWeatherDataToAddToDb = this.getNewWeatherData(currentWeatherData);
    
        for (WeatherData singleWeatherData:newWeatherDataToAddToDb) {
            databaseAccess.insertSingleWeatherData(singleWeatherData);
        }
        LOGGER.info("The Weather Station .CSV was retrieved and processed successfully");
    }
}
