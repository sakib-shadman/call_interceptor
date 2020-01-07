package com.example.instorage_caller.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncRequest {

    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("options")
    @Expose
    public Options options;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}
