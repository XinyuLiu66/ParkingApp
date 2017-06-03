package com.example.andriod.parkingapp.data.saver;

/**
 * Created by dell on 02.06.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeStatus {

    @SerializedName("00-06")
    @Expose
    private String _0006;
    @SerializedName("06-08")
    @Expose
    private String _0608;
    @SerializedName("08-10")
    @Expose
    private String _0810;
    @SerializedName("10-12")
    @Expose
    private String _1012;
    @SerializedName("12-14")
    @Expose
    private String _1214;
    @SerializedName("14-16")
    @Expose
    private String _1416;
    @SerializedName("16-18")
    @Expose
    private String _1618;
    @SerializedName("18-21")
    @Expose
    private String _1821;
    @SerializedName("21-24")
    @Expose
    private String _2124;


    public String get_0006() {
        return _0006;
    }

    public void set_0006(String _0006) {
        this._0006 = _0006;
    }

    public String get_0608() {
        return _0608;
    }

    public void set_0608(String _0608) {
        this._0608 = _0608;
    }

    public String get_0810() {
        return _0810;
    }

    public void set_0810(String _0810) {
        this._0810 = _0810;
    }

    public String get_1012() {
        return _1012;
    }

    public void set_1012(String _1012) {
        this._1012 = _1012;
    }

    public String get_1214() {
        return _1214;
    }

    public void set_1214(String _1214) {
        this._1214 = _1214;
    }

    public String get_1416() {
        return _1416;
    }

    public void set_1416(String _1416) {
        this._1416 = _1416;
    }

    public String get_1618() {
        return _1618;
    }

    public void set_1618(String _1618) {
        this._1618 = _1618;
    }

    public String get_1821() {
        return _1821;
    }

    public void set_1821(String _1821) {
        this._1821 = _1821;
    }

    public String get_2124() {
        return _2124;
    }

    public void set_2124(String _2124) {
        this._2124 = _2124;
    }




}