package com.pythorex.weathermonitor.constants;

public class SqlCommands {
    public static final String SqlGetAllEntries = "SELECT * FROM weatherdata";
    public static final String SqlGetAllSha1s = "SELECT _sha1 FROM weatherdata";
    public static final String SqlEntryCount = "SELECT COUNT(id) FROM weatherdata";
    public static final String SqlGetLastEntry = "SELECT * FROM weatherdata ORDER BY TIMESTAMP DESC";
    public static final String SqlRangeRainfall = 
            "(SELECT * FROM weatherdata WHERE TIMESTAMP >= :begindate AND TIMESTAMP <= :enddate ORDER BY TIMESTAMP LIMIT 1)" +
            "UNION ALL (SELECT * FROM weatherdata WHERE TIMESTAMP >= :begindate AND TIMESTAMP <= :enddate ORDER BY TIMESTAMP DESC LIMIT 1)";
    public static final String SqlInsertWeatherDataObject = 
            "INSERT INTO weatherdata (timestamp,outtemp,outhumidity,outdewpoint,heatindex,windchill,barpress,rainfall,windspeed,windspeedave,windspeedpeak,winddirection,intemp,inhumidity,indewpoint,_sha1) " +
            "values (:timestamp,:outtemp,:outhumidity,:outdewpoint,:heatindex,:windchill,:barpress,:rainfall,:windspeed,:windspeedave,:windspeedpeak,:winddirection,:intemp,:inhumidity,:indewpoint,:_sha1)";
}
