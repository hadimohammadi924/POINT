package com.example.point;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class report extends AppCompatActivity {

    TextView rtitle,rpoint,rtpoint,rvyt,rvy,rbazar,rtbazar,rtiket,rttiket;
    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        rtitle = findViewById(R.id.rtitle);
        rpoint = findViewById(R.id.rpoint);
        rtpoint = findViewById(R.id.rtpoint);
        rvyt = findViewById(R.id.rvyt);
        rvy = findViewById(R.id.rvy);
        rbazar = findViewById(R.id.rbazar);
        rtbazar = findViewById(R.id.rtbazar);
        rtiket = findViewById(R.id.rtiket);
        rttiket = findViewById(R.id.rttiket);

        String tiketurll = "https://pgtab.info/home/getreport?date=" + pdformater1.format(pdate).toString();
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

                            rtitle.setText("گزارش عملکرد تا تاریخ: " + pdformater1.format(pdate).toString());
                            rvy.setText("امروز:" + respo.getString("yakhtoday").toString());
                            rvyt.setText("کل:" + respo.getString("yakhtotal").toString());
                            rbazar.setText("امروز:" + respo.getString("bazartoday").toString());
                            rtbazar.setText(" کل:" + respo.getString("bazartotal").toString());
                            rpoint.setText("امروز:" + respo.getString("custdate").toString());
                            rtpoint.setText(" کل:" + respo.getString("custtotal").toString());
                            rtiket.setText("امروز" + respo.getString("shekayattoday").toString());
                            rttiket.setText("کل" + respo.getString("shekayattotal").toString());


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
        requestQueuee.add(stringRequestt);


    }

}