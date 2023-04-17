package com.example.point;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.point.Adapter.customAdapter;
import com.example.point.DataModel.bbazargardi;
import com.example.point.DataModel.customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class showcustpoint extends AppCompatActivity {
RecyclerView recyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcustpoint);
        recyclerview=findViewById(R.id.recyclerview);


        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);









        String bazarurl = "https://pgtab.info/home/getalll";
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, bazarurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        setUpRecyclerView(response);
                           //     Toast.makeText(showcustpoint.this, response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);




}

    public List<customer> createlist(String response){

        List<customer> lst = new ArrayList<customer>();


        try {
            JSONArray jsonArray = new JSONArray(response);

            for(int i=0; i < jsonArray.length();i++){
                customer info = new customer();
                JSONObject temp = jsonArray.getJSONObject(i);
                info.setId(temp.getInt("id"));
                info.setType(temp.getString("type"));
                info.setCust(temp.getString("cust"));
                info.setPower(temp.getString("power"));
                info.setName(temp.getString("name"));
                info.setTell1(temp.getString("tell1"));
                info.setMobile(temp.getString("mobile"));
                info.setAddress(temp.getString("address"));
                info.setTypeRel(temp.getString("typeRel"));
                info.setPgcode(temp.getString("pgcode"));
                info.setPicURL(temp.getString("picURL"));
                info.setX1(temp.getString("x1"));
                info.setX2(temp.getString("x2"));
                info.setDate(temp.getString("date"));
                info.setTime(temp.getString("time"));
                info.setUn(temp.getString("un"));


                lst.add(info);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst;

    }

    public void setUpRecyclerView(String response){
        LinearLayoutManager layout = new LinearLayoutManager(showcustpoint.this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layout);

        CardAdapter adapter = new CardAdapter(createlist(response),showcustpoint.this);
        recyclerview.setAdapter(adapter);

    }

    public void onBackPressed() {
        startActivity(new Intent(showcustpoint.this, menu.class));}

}