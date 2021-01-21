package com.dima.mayakalarm;

import java.util.ArrayList;

public class WeatherResponse {
    public ArrayList<Weather> weather = new ArrayList<>();
    public Main main;
    public Wind wind;
}

class Weather {
    public String description;
}

class Main {
    public int temp;
    public int humidity;
}

class Wind {
    public int speed;
}

