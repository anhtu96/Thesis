/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.model;

/**
 *
 * @author songo
 */
public class TempControlModel {

    private int id;
    private int deviceid;
    private String devicename;
    private int auto;
    private String modestr;
    private int tempset;
    private int humidset;
    private int mist;
    private int fanspeed;
    private String sensor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public int getAuto() {
        return auto;
    }

    public void setAuto(int auto) {
        this.auto = auto;
    }

    public int getTempset() {
        return tempset;
    }

    public void setTempset(int tempset) {
        this.tempset = tempset;
    }

    public int getHumidset() {
        return humidset;
    }

    public void setHumidset(int humidset) {
        this.humidset = humidset;
    }

    public int getMist() {
        return mist;
    }

    public void setMist(int mist) {
        this.mist = mist;
    }

    public int getFanspeed() {
        return fanspeed;
    }

    public void setFanspeed(int fanspeed) {
        this.fanspeed = fanspeed;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getModestr() {
        return modestr;
    }

    public void setModestr(String modestr) {
        this.modestr = modestr;
    }

}
