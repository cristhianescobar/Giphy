package com.cristhianescobar.giphyappapi.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cristhian.escobar on 1/10/16.
 */
public class ResponseData {
    @SerializedName("data")
    public
    List<Data> data;
}