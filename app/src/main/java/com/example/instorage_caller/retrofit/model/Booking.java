package com.example.instorage_caller.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class Booking {
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("storage")
    @Expose
    public String storage;
    @SerializedName("floorName")
    @Expose
    public String floorName;
    @SerializedName("spaceSize")
    @Expose
    public String spaceSize;
    @SerializedName("startFrom")
    @Expose
    public String startFrom;
    @SerializedName("cancelDate")
    @Expose
    public String cancelDate;

    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getSpaceSize() {
        return spaceSize;
    }

    public void setSpaceSize(String spaceSize) {
        this.spaceSize = spaceSize;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @SerializedName("unitName")
    @Expose
    public String unitName;
}
