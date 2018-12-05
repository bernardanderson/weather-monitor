package com.pythorex.weathermonitor.models;

public class RainData {
    
    private double dailyRainfall;
    private double monthlyRainfall;
    private double ytdRainfall;
    
    public RainData (double dailyRainfall, double monthlyRainfall, double ytdRainfall) {
        this.dailyRainfall = dailyRainfall;
        this.monthlyRainfall = monthlyRainfall;
        this.ytdRainfall = ytdRainfall;
    }
    
    public double getDailyRainfall() {
        return dailyRainfall;
    }
    public double getMonthlyRainfall() {
        return monthlyRainfall;
    }
    public double getYtdRainfall() {
        return ytdRainfall;
    }
    public void setDailyRainfall(double dailyRainfall) {
        this.dailyRainfall = dailyRainfall;
    }
    public void setMonthlyRainfall(double monthlyRainfall) {
        this.monthlyRainfall = monthlyRainfall;
    }
    public void setYtdRainfall(double ytdRainfall) {
        this.ytdRainfall = ytdRainfall;
    }
}
