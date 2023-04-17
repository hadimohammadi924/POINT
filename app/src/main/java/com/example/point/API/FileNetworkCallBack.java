package com.example.point.API;

import okhttp3.Request;
import okhttp3.ResponseBody;

public interface FileNetworkCallBack {
    void onSuccess(ResponseBody responseBody, Request request);
    void onFailure();
}
