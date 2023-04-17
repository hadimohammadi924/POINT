package com.example.point.API;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {


private static final String Url="https://pgtab.info/";
private  TaskService apiService;
public ServiceGenerator(){

    OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request orginal=chain.request();
Request.Builder requestbuilder=orginal.newBuilder()
        .header("header","123")
        .method(orginal.method(),orginal.body());
                    Request request=requestbuilder.build();


                    return chain.proceed(request);
                }
            })
            .build();

    Retrofit restadapter=new Retrofit.Builder()
            .baseUrl(Url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    apiService=restadapter.create(TaskService.class);


}


    public TaskService getService() {
        return apiService;
    }




}
