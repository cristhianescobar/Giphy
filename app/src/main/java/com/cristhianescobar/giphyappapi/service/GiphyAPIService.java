package com.cristhianescobar.giphyappapi.service;

import com.cristhianescobar.giphyappapi.data.ResponseData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by cristhian.escobar on 1/9/16.
 */
public interface GiphyAPIService {
    public static final String API_BASE_URL = "http://api.giphy.com";
    public String API_KEY = "dc6zaTOxFJmzC";

    @GET("/v1/gifs/trending?api_key=dc6zaTOxFJmzC")
    Call<ResponseData> getTrendingGiphys();


    @GET("/v1/gifs/search")
    Call<ResponseData> getQueryGiphy(@Query("q") String search, @Query("api_key") String key);

    @GET("/v1/gifs/trending?api_key=dc6zaTOxFJmzC")
    Observable<ResponseData> getTrendingGiphysRX();


    @GET("/v1/gifs/search")
    Observable<ResponseData> getQueryGiphyRX(@Query("q") String search, @Query("api_key") String key);

}