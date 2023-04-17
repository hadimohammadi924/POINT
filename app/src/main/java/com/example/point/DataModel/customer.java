package com.example.point.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class customer {

@SerializedName("id")
private int id;

    @SerializedName("type")
    private String type;


    @SerializedName("cust")
    private String cust;

    @SerializedName("power")
    private String power;

    @SerializedName("name")
    private String name;

    @SerializedName("tell1")
    private String tell1;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("address")
    private String address;

    @SerializedName("typeRel")
    private String typeRel;

    @SerializedName("pgcode")
    private String pgcode;

    @SerializedName("picURL")
    private String picURL;

    @SerializedName("x1")
    private String x1;

    @SerializedName("x2")
    private String x2;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("un")
    private String un;

    @SerializedName("username")

    private String username;

    @SerializedName("password")

    private String password;




    public customer(String type, String cust, String power, String name, String tell1, String mobile, String address, String typeRel, String pgcode, String picURL, String x1, String x2, String date, String time, String un) {
        this.type = type;
        this.cust = cust;
        this.power = power;
        this.name = name;
        this.tell1 = tell1;
        this.mobile = mobile;
        this.address = address;
        this.typeRel = typeRel;
        this.pgcode = pgcode;
        this.picURL = picURL;
        this.x1 = x1;
        this.x2 = x2;
        this.date = date;
        this.time = time;
        this.un = un;
    }

    public customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public customer(int id, String type, String cust, String power, String name, String tell1, String mobile, String address, String typeRel, String pgcode, String picURL, String x1, String x2, String date, String time, String un) {
        this.id = id;
        this.type = type;
        this.cust = cust;
        this.power = power;
        this.name = name;
        this.tell1 = tell1;
        this.mobile = mobile;
        this.address = address;
        this.typeRel = typeRel;
        this.pgcode = pgcode;
        this.picURL = picURL;
        this.x1 = x1;
        this.x2 = x2;
        this.date = date;
        this.time = time;
        this.un = un;
    }

    public customer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTell1() {
        return tell1;
    }

    public void setTell1(String tell1) {
        this.tell1 = tell1;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeRel() {
        return typeRel;
    }

    public void setTypeRel(String typeRel) {
        this.typeRel = typeRel;
    }

    public String getPgcode() {
        return pgcode;
    }

    public void setPgcode(String pgcode) {
        this.pgcode = pgcode;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getX1() {
        return x1;
    }

    public void setX1(String x1) {
        this.x1 = x1;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
