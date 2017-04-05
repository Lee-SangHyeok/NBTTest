package com.nbt.oopssang.nbttest.network;

import com.nbt.oopssang.nbttest.data.NewYorkDataSet;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sang on 2017-04-04.
 */

public interface NewYorkService {
    @GET("home.json?api-key=cf23f0334a174fff975fc2400ccbfdd9")
    Call<NewYorkDataSet> getdata();
}
