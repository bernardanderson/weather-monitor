package com.pythorex.weathermonitor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pythorex.weathermonitor.models.WeatherData;
import com.pythorex.weathermonitor.repository.DatabaseAccess;
import com.pythorex.weathermonitor.services.FileService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest 
{
    @Autowired
    DatabaseAccess dbAccess;
    
    @Autowired
    FileService fileService;
    
    @Test
    public void Test_Check_Db_Instance()
    {
        assertThat(dbAccess, is(notNullValue()));
    }
    
    @Test
    @Ignore
    public void Test_Get_Weatherdata_Count()
    {
        int dbCount = dbAccess.getSimpleCount();
        assertThat( dbCount, is(0) );
    }
    
    @Test
    @Ignore
    public void Test_Check_File_List() {
      List<String[]> processedFile = null;
      
        try {
            processedFile = fileService.readFileData();
        } catch (NullPointerException| IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertThat( processedFile.get(10)[0], is("2017-10-31 23:59:00") );
    }
    
    @Test
    @Ignore
    public void Test_Is_Date_Being_Converted() {
        
//        String textDateFromFile = "\"11/1/2017 12:00:00 AM\",\"47.5\",\"98\",\"47\",\"48\",\"48\",\"29.32327\",\"51.18\",\"0\",\"0\",\"2.485485\",\"45\",\"66.9\",\"52\"";
//        String processedDateText = "";
//      
//        processedDateText = fileService.dataTimeTransformer(textDateFromFile);
//        assertThat( processedDateText, is("2017-11-1 00:00:00") );
    }
}