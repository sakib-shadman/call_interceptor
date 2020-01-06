package com.example.instorage_caller.roomdb;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Hi on 8/20/2019.
 */

@Entity(tableName = "user_location")
public class UserLocation {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "user_id")
    private String userId;
    @ColumnInfo(name = "lat")
    private Double lat;
    @ColumnInfo(name = "lon")
    private Double lon;
    @ColumnInfo(name = "client_timestamp_utc")
    private Long clientTimestampUtc;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Long getClientTimestampUtc() {
        return clientTimestampUtc;
    }

    public void setClientTimestampUtc(Long clientTimestampUtc) {
        this.clientTimestampUtc = clientTimestampUtc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
