package com.example.point.DataModel;

import com.google.gson.annotations.Expose;

import com.google.gson.annotations.SerializedName;

public class shekayat {

    @SerializedName("id_tiket")
    private int id_tiket;

    @SerializedName("tdate")
    private String tdate;


    @SerializedName("ttime")
    private String ttime;

    @SerializedName("tcategori")
    private String tcategori;

    @SerializedName("ttitle")
    private String ttitle;

    @SerializedName("tdescription")
    private String tdescription;

    @SerializedName("tbgcode")
    private String tbgcode;


    @SerializedName("bgname")
    private String bgname;

    @SerializedName("btell")
    private String btell;

    @SerializedName("tvisitor")
    private String tvisitor;

    @SerializedName("tresponse")
    private String tresponse;

    @SerializedName("trdate")
    private String trdate;

    @SerializedName("trtime")
    private String trtime;

    @SerializedName("truser")
    private String truser;

    @SerializedName("tstatus")
    private String tstatus;

    @SerializedName("x1")
    private String x1;

    @SerializedName("x2")
    private String x2;

    @SerializedName("x3")
    private String x3;

    @SerializedName("x4")
    private String x4;


    public shekayat(String tdate, String ttime, String tcategori, String ttitle, String tdescription,
                    String tbgcode, String bgname, String btell, String tvisitor, String tresponse,
                    String trdate, String trtime, String truser, String tstatus, String x1, String x2, String x3, String x4) {

        this.tdate = tdate;
        this.ttime = ttime;
        this.tcategori = tcategori;
        this.ttitle = ttitle;
        this.tdescription = tdescription;
        this.tbgcode = tbgcode;
        this.bgname = bgname;
        this.btell = btell;
        this.tvisitor = tvisitor;
        this.tresponse = tresponse;
        this.trdate = trdate;
        this.trtime = trtime;
        this.truser = truser;
        this.tstatus = tstatus;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;


    }

    public shekayat() {

    }


    public int getId_tiket() {
        return id_tiket;
    }

    public void setId_tiket(int id_tiket) {
        this.id_tiket = id_tiket;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getTtime() {
        return ttime;
    }

    public void setTtime(String ttime) {
        this.ttime = ttime;
    }

    public String getTcategori() {
        return tcategori;
    }

    public void setTcategori(String tcategori) {
        this.tcategori = tcategori;
    }

    public String getTtitle() {
        return ttitle;
    }

    public void setTtitle(String ttitle) {
        this.ttitle = ttitle;
    }

    public String getTdescription() {
        return tdescription;
    }

    public void setTdescription(String tdescription) {
        this.tdescription = tdescription;
    }

    public String getTbgcode() {
        return tbgcode;
    }

    public void setTbgcode(String tbgcode) {
        this.tbgcode = tbgcode;
    }

    public String getBgname() {
        return bgname;
    }

    public void setBgname(String bgname) {
        this.bgname = bgname;
    }

    public String getBtell() {
        return btell;
    }

    public void setBtell(String btell) {
        this.btell = btell;
    }

    public String getTvisitor() {
        return tvisitor;
    }

    public void setTvisitor(String tvisitor) {
        this.tvisitor = tvisitor;
    }

    public String getTresponse() {
        return tresponse;
    }

    public void setTresponse(String tresponse) {
        this.tresponse = tresponse;
    }

    public String getTrdate() {
        return trdate;
    }

    public void setTrdate(String trdate) {
        this.trdate = trdate;
    }

    public String getTrtime() {
        return trtime;
    }

    public void setTrtime(String trtime) {
        this.trtime = trtime;
    }

    public String getTruser() {
        return truser;
    }

    public void setTruser(String truser) {
        this.truser = truser;
    }

    public String getTstatus() {
        return tstatus;
    }

    public void setTstatus(String tstatus) {
        this.tstatus = tstatus;
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

    public String getX3() {
        return x3;
    }

    public void setX3(String x3) {
        this.x3 = x3;
    }

    public String getX4() {
        return x4;
    }

    public void setX4(String x4) {
        this.x4 = x4;
    }





}
