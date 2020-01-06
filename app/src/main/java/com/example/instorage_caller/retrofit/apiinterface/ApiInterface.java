package com.example.instorage_caller.retrofit.apiinterface;

import com.example.instorage_caller.retrofit.model.SyncResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Content-Type: application/json"})
    @GET("app/v1/customers")

    Single<SyncResponse> getAllData(
       @Query("page") Integer pageNumber
    );
}
