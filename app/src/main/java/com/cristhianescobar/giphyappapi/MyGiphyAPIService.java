package com.cristhianescobar.giphyappapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by cristhian.escobar on 1/9/16.
 */
public interface MyGiphyAPIService {

    @GET("/v1/gifs/trending?api_key=dc6zaTOxFJmzC")
    Call<ResponseData> getTrendingGiphys();


    class ResponseData {
        @SerializedName("data")
        List<Data> data;


    }

    class Data {
        @SerializedName("type")
        String type;
        @SerializedName("id")
        String id;
        @SerializedName("embed_url")
        String embed_url;
        @SerializedName("rating")
        String rating;
        @SerializedName("trending_datetime")
        String trending_datetime;
        @SerializedName("images")
        Images images;


    }

    class Images {
        @SerializedName("fixed_height_downsampled")
        DownSampled downSampled;

    }

    class DownSampled {
        @SerializedName("url")
        String imageUrl;
    }

    }