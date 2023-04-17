package com.example.point;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.example.point.Adapter.bazargardiadapter;
import com.example.point.Adapter.resadvisitoradapter;
import com.example.point.DataModel.bbazargardi;
import com.example.point.DataModel.resadVis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class resadeVisitor2 extends AppCompatActivity {
    String tiketurl;

    RecyclerView resadvisitor2;
    TextView twoo, totalcount, todaycount;
    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resade_visitor2);

        tcast();
        resadvisitor2 = findViewById(R.id.resadvisitor2R);
        twoo = findViewById(R.id.twoo);
        totalcount = findViewById(R.id.totalcount);
        todaycount = findViewById(R.id.todaycount);
        PersianDate pdatee = new PersianDate();
        PersianDateFormat pdformater11 = new PersianDateFormat("Y/m/d");
        twoo.setText("تاریخ امروز: " + pdformater11.format(pdatee));


        String tiketurl = "https://pgtab.info/home/getvy2withftimeltimeuniqname?date=" + pdformater1.format(pdate).toString();


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
                        //  Toast.makeText(resadeVisitor2.this, responsee, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);








        String tiketurll = "https://pgtab.info/home/getreport?date="+pdformater1.format(pdate).toString();
        RequestQueue requestQueuee;
        Cache cachee = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network networkk = new BasicNetwork(new HurlStack());
        requestQueuee = new RequestQueue(cachee, networkk);
        requestQueuee.start();
        StringRequest stringRequestt = new StringRequest(Request.Method.GET, tiketurll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responsee) {

                        try {


                            JSONObject respo = new JSONObject(responsee);

                            totalcount.setText("مراجعه فروشگاهی ویزیتور کل: "+respo.getString("yakhtotal").toString());
                            todaycount.setText("مراجعه فروشگاهی ویزیتور امروز:"+respo.getString("yakhtoday").toString());




                        } catch (JSONException e) {

                            e.printStackTrace();



                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequestt);



    }


    public List<resadVis> createlist2(String responsee) {

        List<resadVis> lst2 = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(responsee);

            for (int i = 0; i < jsonArray.length(); i++) {
                resadVis info = new resadVis();
                JSONObject ttemp = jsonArray.getJSONObject(i);
                info.setName(ttemp.getString("name"));
                info.setCountToday(ttemp.getInt("countToday"));
                info.setCount(ttemp.getInt("count"));
                info.setFtime(ttemp.getString("ftime"));
                info.setLtime(ttemp.getString("ltime"));
                info.setUrl(ttemp.getString("url"));


                lst2.add(info);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst2;

    }

    public void setUpRecyclerView(String responsee) {
        LinearLayoutManager layout = new LinearLayoutManager(resadeVisitor2.this, LinearLayoutManager.VERTICAL, false);
        resadvisitor2.setLayoutManager(layout);

        resadvisitoradapter shadpter = new resadvisitoradapter(createlist2(responsee), resadeVisitor2.this);
        resadvisitor2.setAdapter(shadpter);

    }


    public void tcast() {


        //     SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        //   tun.setText(sharedPreferences.getString("user", ""));
        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);

    }

    public void onBackPressed() {
        startActivity(new Intent(resadeVisitor2.this, menu.class));
    }


}