package com.example.point.DataModel;

import com.google.gson.annotations.SerializedName;

public class resadVis {

    @SerializedName("name")
    private String name;

    @SerializedName("countToday")
    private int countToday;

    @SerializedName("count")
    private int count;

    @SerializedName("ftime")
    private String ftime;

    @SerializedName("ltime")
    private String ltime;

    @SerializedName("url")
    private String url;

    public resadVis(String name, int countToday, int count, String ftime, String ltime,
                    String url) {

        this.name = name;
        this.countToday = countToday;
        this.count = count;
        this.ftime = ftime;
        this.ltime = ltime;
        this.url = url;


    }

    public resadVis() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountToday() {
        return countToday;
    }

    public void setCountToday(int countToday) {
        this.countToday = countToday;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getLtime() {
        return ltime;
    }

    public void setLtime(String ltime) {
        this.ltime = ltime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
