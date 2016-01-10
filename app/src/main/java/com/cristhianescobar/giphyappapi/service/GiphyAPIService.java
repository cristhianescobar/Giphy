package com.cristhianescobar.giphyappapi.service;

import com.cristhianescobar.giphyappapi.data.ResponseData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by cristhian.escobar on 1/9/16.
 */
public interface GiphyAPIService {
    public static final String API_BASE_URL = "http://api.giphy.com";


    @GET("/v1/gifs/trending?api_key=dc6zaTOxFJmzC")
    Call<ResponseData> getTrendingGiphys();

    //    http://api.giphy.com/v1/gifs/search?q=love&api_key=dc6zaTOxFJmzC
    @GET("/v1/gifs/search?q={words}&api_key=dc6zaTOxFJmzC")
    Call<ResponseData> getQueryGiphy(@Path("words") String words);




}