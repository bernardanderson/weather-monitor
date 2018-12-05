package com.pythorex.weathermonitor.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Query;

import com.pythorex.weathermonitor.constants.SqlCommands;
import com.pythorex.weathermonitor.models.WeatherData;

@Repository
public class DatabaseAccess {
    
    @Autowired
    private Sql2o sql2o;
    
    public List<WeatherData> getAllEntries() {
        try (Connection con = sql2o.open()) {
            return con.createQuery(SqlCommands.SqlGetAllEntries).executeAndFetch(WeatherData.class);
        }
    }
    
    public List<WeatherData> getRangedRainfall(Date begindate, Date enddate) {
        try (Connection con = sql2o.open()) {
            return con.createQuery(SqlCommands.SqlRangeRainfall)
            .addParameter("begindate", begindate)
            .addParameter("enddate", enddate)
            .executeAndFetch(WeatherData.class);
        }
    }
    
    public int getSimpleCount() {
        try (Connection con = sql2o.open()) {
            return con.createQuery(SqlCommands.SqlEntryCount).executeScalar(Integer.class);
        }
    }
    
    public List<String> getGetSha1List() {
        try (Connection con = sql2o.open()) {
            return con.createQuery(SqlCommands.SqlGetAllSha1s).executeAndFetch(String.class);
        }
    }
    
    public void insertSingleWeatherData(WeatherData sentWeatherData) {
        try (Connection con = sql2o.open()) {
            con.createQuery(SqlCommands.SqlInsertWeatherDataObject).bind(sentWeatherData).executeUpdate();
        }
    }
    
    public WeatherData getLastWeatherDatum() {
        try (Connection con = sql2o.open()) {
            return con.createQuery(SqlCommands.SqlGetLastEntry).executeAndFetchFirst(WeatherData.class);
        }
    }
    
}
