package com.example.instorage_caller.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;


public class SyncResponse {

    @SerializedName("data")
    @Expose
    public List<Customer> data = null;
    @SerializedName("links")
    @Expose
    public Links links;
    @SerializedName("meta")
    @Expose
    public Meta meta;

    public List<Customer> getData() {
        return data;
    }

    public void setData(List<Customer> data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
