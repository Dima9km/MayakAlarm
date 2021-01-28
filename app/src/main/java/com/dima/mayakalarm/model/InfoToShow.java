package com.dima.mayakalarm.model;

public class InfoToShow {
    public String currentWeather;
    public String imageUrl;

    public InfoToShow(String currentWeather, String imageUrl) {
        this.currentWeather = currentWeather;
        this.imageUrl = imageUrl;
    }

    public InfoToShow() {
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
