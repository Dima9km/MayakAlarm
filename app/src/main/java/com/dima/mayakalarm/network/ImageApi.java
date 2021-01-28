package com.dima.mayakalarm.network;

import com.dima.mayakalarm.model.FlikrImage;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageApi {
    @GET("200/200/all")
    Call<FlikrImage> getImageUrl();
}

