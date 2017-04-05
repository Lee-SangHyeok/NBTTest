package com.nbt.oopssang.nbttest.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sang on 2017-04-04.
 */

public class RetrofitManager{
    private final static String BASE_URL_NEWYORKTIMES = "https://api.nytimes.com/svc/topstories/v2/";

    public static NewYorkService getNewYorkService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_NEWYORKTIMES)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewYorkService service = retrofit.create(NewYorkService.class);
        return service;
    }
}
