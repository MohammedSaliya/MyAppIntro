package com.example.myappintro.APIClasss;

import com.example.myappintro.Bean.ViewpagerBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APInterface {


    @GET("features")
    Call<ViewpagerBean> getViewPagerResponce();

}
