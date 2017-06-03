package com.example.andriod.parkingapp.data.saver;

/**
 * Created by dell on 02.06.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationInfo{

    @SerializedName("coordinate")
    @Expose
    private String coordinate;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("time_status")
    @Expose
    private TimeStatus timeStatus;

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TimeStatus getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(TimeStatus timeStatus) {
        this.timeStatus = timeStatus;
    }

}
