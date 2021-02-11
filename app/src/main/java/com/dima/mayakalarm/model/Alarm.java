package com.dima.mayakalarm.model;

public class Alarm {
    private long time;
    private boolean isAlarmOn;

    public Alarm(long time, boolean isAlarmOn) {
        this.time = time;
        this.isAlarmOn = isAlarmOn;
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

}
