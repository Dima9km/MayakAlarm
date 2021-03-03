package com.dima.mayakalarm.model;

public class Alarm {
    private long time;
    private boolean isAlarmOn;
    private String language;


    public Alarm(long time, boolean isAlarmOn, String language) {
        this.time = time;
        this.isAlarmOn = isAlarmOn;
        this.language = language;
    }

    public long getTime() {
        return time;
    }

    public boolean isAlarmOn() {
        return isAlarmOn;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setAlarmOn(boolean isAlarmOn) {
        this.isAlarmOn = isAlarmOn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
