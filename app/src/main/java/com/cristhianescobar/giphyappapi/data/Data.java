package com.cristhianescobar.giphyappapi.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cristhian.escobar on 1/10/16.
 */
public class Data {
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
    public
    Images images;
}