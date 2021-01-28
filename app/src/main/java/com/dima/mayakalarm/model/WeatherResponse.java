package com.dima.mayakalarm.model;

import java.util.ArrayList;

public class WeatherResponse {
    public ArrayList<Weather> weather = new ArrayList<>();
    public Main main;
    public Wind wind;

    public class Weather {
        public String description;
    }

    public class Main {
        public int temp;
        public int humidity;
    }

    public class Wind {
        public int speed;
    }

}

