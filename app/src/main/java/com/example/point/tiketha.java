package com.example.point;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;


import com.example.point.Adapter.shekayatadapter;
import com.example.point.DataModel.customer;
import com.example.point.DataModel.shekayat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tiketha extends AppCompatActivity {

    ArrayList<shekayat> s;
    EditText tun;
    RecyclerView ticketsListRecyclerView;
    FloatingActionButton cresponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiketha);


         tcast();
        cresponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(tiketha.this,sabte_shekayat.class));
            }
        });





        String tiketurl = "https://pgtab.info/home/getaltiket";


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
       StringRequest stringRequest = new StringRequest(Request.Method.GET, tiketurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responsee) {
                        setUpRecyclerView(responsee);
                       // Toast.makeText(tiketha.this, responsee, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);




    }



    public List<shekayat> createlist2(String responsee){

        List<shekayat> lst2 = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(responsee);

            for(int i=0; i < jsonArray.length();i++){
                shekayat info = new shekayat();
                JSONObject ttemp = jsonArray.getJSONObject(i);
               info.setId_tiket(ttemp.getInt("id_tiket"));
              info.setTdate(ttemp.getString("tdate"));
                info.setTtime(ttemp.getString("ttime"));
                info.setTcategori(ttemp.getString("tcategori"));
                info.setTtitle(ttemp.getString("ttitle"));
                info.setTdescription(ttemp.getString("tdescription"));
                info.setTbgcode(ttemp.getString("tbgcode"));
                info.setBgname(ttemp.getString("bgname"));
                info.setBtell(ttemp.getString("btell"));
                info.setTvisitor(ttemp.getString("tvisitor"));
                info.setTresponse(ttemp.getString("tresponse"));
                info.setTrdate(ttemp.getString("trdate"));
                info.setTrtime(ttemp.getString("trtime"));
                info.setTruser(ttemp.getString("truser"));
                info.setTstatus(ttemp.getString("tstatus"));
                info.setX1(ttemp.getString("x1"));
                info.setX2(ttemp.getString("x2"));
                info.setX3(ttemp.getString("x3"));
                info.setX4(ttemp.getString("x4"));


                lst2.add(info);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst2;

    }

    public void setUpRecyclerView(String responsee){
        LinearLayoutManager layout = new LinearLayoutManager(tiketha.this, LinearLayoutManager.VERTICAL, false);
        ticketsListRecyclerView.setLayoutManager(layout);

        shekayatadapter shadpter = new shekayatadapter(createlist2(responsee),tiketha.this);
        ticketsListRecyclerView.setAdapter(shadpter);

    }



    public void tcast(){
        tun=findViewById(R.id.tun);
        cresponse=findViewById(R.id.cresponse);
        ticketsListRecyclerView=findViewById(R.id.ticketsListRecyclerView);



        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        tun.setText(sharedPreferences.getString("user", ""));
        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);

    }
    public void onBackPressed() {
        startActivity(new Intent(tiketha.this, menu.class));
    }






}