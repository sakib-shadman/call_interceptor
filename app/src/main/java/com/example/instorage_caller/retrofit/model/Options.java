package com.example.instorage_caller.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options {
    @SerializedName("only_new")
    @Expose
    public Boolean onlyNew;

    public Boolean getOnlyNew() {
        return onlyNew;
    }

    public void setOnlyNew(Boolean onlyNew) {
        this.onlyNew = onlyNew;
    }
}
