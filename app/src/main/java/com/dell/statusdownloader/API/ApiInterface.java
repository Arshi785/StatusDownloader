package com.dell.statusdownloader.API;

import com.dell.statusdownloader.Model.InstaModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("video")
    Call<InstaModel> getInfo(
            @Query("link") String link
    );
}
