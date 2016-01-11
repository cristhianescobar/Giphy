package com.cristhianescobar.giphyappapi.utils;

import android.support.annotation.NonNull;

/**
 * Created by cristhian.escobar on 1/9/16.
 */
public class DataUnit {
        public int iconId;
        public String title;
        public String urlImage;

    @NonNull
    public static DataUnit getDataUnit(String name) {
        DataUnit d = new DataUnit();
        d.title = name;
        return d;
    }
}
