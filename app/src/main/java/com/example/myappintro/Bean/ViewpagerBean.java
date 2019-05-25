package com.example.myappintro.Bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewpagerBean {
    @SerializedName("data")
    @Expose
    private List<ViewpagerDataBean> data;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;


    public ViewpagerBean(List<ViewpagerDataBean> data, Integer statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }

    public List<ViewpagerDataBean> getData() {
        return data;
    }

    public void setData(List<ViewpagerDataBean> data) {
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
