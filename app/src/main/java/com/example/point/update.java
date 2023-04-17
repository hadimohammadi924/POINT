package com.example.point;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

public class update extends AppCompatActivity {

    int version2;
    Button updatee, exitt;
    TextView updateVersion, now_version, updatetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        castupdate();
        version2 = 1;
        chekVersion(version2);


        updatee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();
                String url = "https://pgtab.info/Home/getv?id_V=" + version2;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject respoq = new JSONObject(response);
                                    String Status = respoq.getString("V_url");
                                    Uri uri = Uri.parse(Status);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
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
                requestQueue.add(stringRequest);
            }
        });
        exitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    public void chekVersion(int version2) {

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        final String url = "https://pgtab.info/Home/getv?id_V=" + version2;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respo = new JSONObject(response);
                            String Status = respo.getString("v_now");
                            updatetext.setText(respo.getString("date"));
                            now_version.setText("نسخه فعلی برنامه نصب شده در گوشی شما: " + respo.getString("V_v"));
                            updateVersion.setText("ورژن بروز آماده بارگیری: " + respo.getString("xx1"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(sabte_response.this, "نمیشه نمیدونم چرا", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    public void castupdate() {
        updatee = findViewById(R.id.updatee);
        updateVersion = findViewById(R.id.updateVersion);
        now_version = findViewById(R.id.now_version);
        updatetext = findViewById(R.id.updatetext);
        exitt = findViewById(R.id.exitt);


        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);


    }

    public void onBackPressed() {
        moveTaskToBack(false);
    }

}