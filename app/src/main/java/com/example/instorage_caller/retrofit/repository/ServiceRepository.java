package com.example.instorage_caller.retrofit.repository;

import com.example.instorage_caller.retrofit.apiinterface.ApiInterface;
import com.example.instorage_caller.retrofit.model.SyncRequest;
import com.example.instorage_caller.retrofit.model.SyncResponse;

import java.util.List;

import io.reactivex.Single;

public class ServiceRepository {

    private final ApiInterface apiInterface;

    public ServiceRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Single<SyncResponse> getAllData(Integer pageNumber){
        return apiInterface.getAllData(pageNumber);
    }


    public Single<SyncResponse> getNewData(SyncRequest syncRequest){
        return apiInterface.getNewData(syncRequest);
    }
}
