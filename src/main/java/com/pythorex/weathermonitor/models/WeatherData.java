package com.pythorex.weathermonitor.models;

import java.sql.Timestamp;

public class WeatherData {
    
    private int id = 0;
    private Timestamp timestamp;
    private float outtemp;
    private int outhumidity;
    private int outdewpoint;
    private int heatindex;
    private int windchill;
    private float barpress;
    private float rainfall;
    private float windspeed;
    private float windspeedave;
    private float windspeedpeak;
    private float winddirection;
    private float intemp;
    private float inhumidity;
    private int indewpoint;
    private String _sha1;
    private Timestamp _createddate = null;
    private Boolean _active = true;
    
    public WeatherData (Timestamp timestamp, float outtemp, int outhumidity, int outdewpoint,
            int heatindex, int windchill, float barpress, float rainfall, float windspeed,
            float windspeedave, float windspeedpeak, float winddirection, float intemp,
            float inhumidity, int indewpoint, String _sha1) {
        this.timestamp = timestamp;
        this.outtemp = outtemp;
        this.outhumidity = outhumidity;
        this.outdewpoint = outdewpoint;
        this.heatindex = heatindex;
        this.windchill = windchill;
        this.barpress = barpress;
        this.rainfall = rainfall;
        this.windspeed = windspeed;
        this.windspeedave = windspeedave;
        this.windspeedpeak = windspeedpeak;
        this.winddirection = winddirection;
        this.intemp = intemp;
        this.inhumidity = inhumidity;
        this.indewpoint = indewpoint;
        this._sha1 = _sha1;
    }

    public int getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public float getOuttemp() {
        return outtemp;
    }

    public int getOuthumidity() {
        return outhumidity;
    }

    public int getOutdewpoint() {
        return outdewpoint;
    }

    public int getHeatindex() {
        return heatindex;
    }

    public int getWindchill() {
        return windchill;
    }

    public float getBarpress() {
        return barpress;
    }

    public float getRainfall() {
        return rainfall;
    }

    public float getWindspeed() {
        return windspeed;
    }

    public float getWindspeedave() {
        return windspeedave;
    }

    public float getWindspeedpeak() {
        return windspeedpeak;
    }

    public float getWinddirection() {
        return winddirection;
    }

    public float getIntemp() {
        return intemp;
    }

    public float getInhumidity() {
        return inhumidity;
    }

    public int getIndewpoint() {
        return indewpoint;
    }

    public String get_sha1() {
        return _sha1;
    }

    public Timestamp get_createddate() {
        return _createddate;
    }

    public Boolean get_active() {
        return _active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setOuttemp(float outtemp) {
        this.outtemp = outtemp;
    }

    public void setOuthumidity(int outhumidity) {
        this.outhumidity = outhumidity;
    }

    public void setOutdewpoint(int outdewpoint) {
        this.outdewpoint = outdewpoint;
    }

    public void setHeatindex(int heatindex) {
        this.heatindex = heatindex;
    }

    public void setWindchill(int windchill) {
        this.windchill = windchill;
    }

    public void setBarpress(float barpress) {
        this.barpress = barpress;
    }

    public void setRainfall(float rainfall) {
        this.rainfall = rainfall;
    }

    public void setWindspeed(float windspeed) {
        this.windspeed = windspeed;
    }

    public void setWindspeedave(float windspeedave) {
        this.windspeedave = windspeedave;
    }

    public void setWindspeedpeak(float windspeedpeak) {
        this.windspeedpeak = windspeedpeak;
    }

    public void setWinddirection(float winddirection) {
        this.winddirection = winddirection;
    }

    public void setIntemp(float intemp) {
        this.intemp = intemp;
    }

    public void setInhumidity(float inhumidity) {
        this.inhumidity = inhumidity;
    }
    
    public void setIndewpoint(int indewpoint) {
        this.indewpoint = indewpoint;
    }

    public void set_sha1(String _sha1) {
        this._sha1 = _sha1;
    }

    public void set_createddate(Timestamp _createddate) {
        this._createddate = _createddate;
    }

    public void set_active(Boolean _active) {
        this._active = _active;
    }

}
