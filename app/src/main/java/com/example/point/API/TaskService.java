package com.example.point.API;


import com.example.point.DataModel.bbazargardi;
import com.example.point.DataModel.customer;
import com.example.point.DataModel.shekayat;
import com.example.point.bazargardi;

import java.util.List;
import java.util.Observable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface TaskService {


    @GET("home/getalll_bazargardi")
    Call<List<bbazargardi>> getalll_bazargardi();


    @POST("home/insert_bazargardi")
    Call<bbazargardi> insert_bazargardi(@Body bbazargardi Bazargardii);




    @GET("home/getalll")
    Call<List<customer>> getcustomer();


    @POST("home/insert_Point")
    Call<customer> insert(@Body customer Customer);


    @POST("home/update")
    Call<ResponseBody> update(@Body customer Customer);


    @FormUrlEncoded
    @POST("home/deletcust")
    Call<ResponseBody> delete(@Field("id") int id);


    @FormUrlEncoded
    @POST("home/loogin")
    Call<ResponseBody> loogin(@Field("username") String username, @Field("password") String password);


    @POST("home/insert_tiket")
    Call<shekayat> insert_tiket(@Body shekayat Shekayat);
}
