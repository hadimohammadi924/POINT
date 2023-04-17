package com.example.point;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.point.API.ServiceGenerator;
import com.example.point.Adapter.shekayatadapter;
import com.example.point.DataModel.customer;
import com.example.point.DataModel.shekayat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class sabte_response extends AppCompatActivity {

    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
    PersianDateFormat pdformater2 = new PersianDateFormat("H:i:s");
    ServiceGenerator serviceGenerator;
    ArrayList<shekayat> shekayatlist;
    Button rupdate;
    TextView rshresponse,rshstatus,title3,ctime,cdate,rshtitle,rhh,rs_un,zun,zun2,rshdescrip,rshcode,rshname,rshtell,rshcategori,rshvisitor,rshtozi,rprirority,priority;
    EditText rtresponse;
    String sdate,stime,trdate,trtime,tstatus,x3,x4;
    String ttstatus="پاسخ داده شده",qqrshcategori,title5,rshdescrip5,rshcode5,rshname5,rshtell5,rshvisitor5,rtresponse5,rshtozi5,rprirority5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabte_response);
        scast();

       sdate=(pdformater1.format(pdate));
        trdate=(pdformater1.format(pdate));
        stime=(pdformater2.format(pdate));
        trtime=(pdformater2.format(pdate));
        String zx4=1+"";


        getaltikett(Integer.parseInt(getIntent().getStringExtra("tiketid")));
/*
        rupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Eshshcategori = rshcategori.getText().toString();
                String Eshshvisitor = rshvisitor.getText().toString();
                String Eshshtozi = rshtozi.getText().toString();
                String Eshprirority = rprirority.getText().toString();
                String ETshtitle = rshtitle.getText().toString();
                String ETshdescrip = rshdescrip.getText().toString();
                String ETshcode = rshcode.getText().toString();
                String ETshname = rshname.getText().toString();
                String ETshtell = rshtell.getText().toString();
                String ETtresponse = rtresponse.getText().toString();
                String ETs_un = rs_un.getText().toString();
                String ETtstatus = rshstatus.getText().toString();
               // String ETshprirority = rshprirority.getText().toString();
              //  String ETshshtozi = rshshtozi.toString();


                final String tdate = sdate.toString().trim();
                final String tshtime = stime.trim();

              String tshshcategori = shshcategori.trim();
              String tshtitle = Tshtitle.toString().trim();
              String tshdescrip = Tshdescrip.toString().trim();
              String tshcode = Tshcode.toString().trim();
              String tshname = Tshname.toString().trim();
              String tshtell = Tshtell.toString().trim();
              String tshshvisitor = shshvisitor.trim();
              String ttresponse = Ttresponse.toString().trim();
              String ttrdate = sdate.toString().trim();
              String ttrtime = stime.trim();
              String truser = Ts_un.toString().trim();
              String ttstatus = Ttstatus.trim();
              String tx1 = Ts_un.toString().trim();
              String tx2 = Tshprirority.trim();
              String tx3 = Tshshtozi.trim();
                String tx4 = x4.trim();






                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();

              String url = "https://pgtab.info/Home/insert_respone?tdate="+tdate+"&ttime="+tshtime+"&tcategori="+tshshcategori+"&ttitle="+tshtitle+"&tdescription="+tshdescrip+"&tbgcode="+tshcode+"&bgname="+tshname+"&btell="+tshtell+"&tvisitor="+tshshvisitor+"&tresponse="+ttresponse+"&trdate="+ttrdate+"&trtime="+ttrtime+"&truser="+truser+"&tstatus="+ttstatus+"&x1="+tx1+"&x2="+tx2+"&x3="+tx3+"&x4="+tx4;
              StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(sabte_response.this, "با موفقیت ثبت شدید" , Toast.LENGTH_SHORT).show();

                                finish();


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
 */

        rupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           final  String rtresponse4 = rtresponse.getText().toString();

                      rtresponse5 =rtresponse4.trim();

                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();


                String url = "https://pgtab.info/Home/insert_respone?id_tiket="+getIntent().getStringExtra("tiketid")
                        +"&tdate="+cdate.getText()+"&ttime="+ctime.getText()+"&tcategori="+qqrshcategori+"&ttitle="+title5
                        +"&tdescription="+rshdescrip5+"&tbgcode="+rshcode5+"&bgname="+rshname5
                        +"&btell="+rshtell5+"&tvisitor="+rshvisitor5+"&tresponse="+rtresponse5
                        +"&trdate="+trdate+"&trtime="+trtime+"&truser="+rs_un.getText()+"&tstatus="
                        +ttstatus.toString()+"&x1="+zun2.getText()+"&x2="+rprirority5.toString()+"&x3="+rshtozi5.toString()+"&x4="+zx4.toString();


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject respo = new JSONObject(response);
                                    String Status = respo.getString("id_tiket");


                                    if(Status.equals("تغیرات لحاظ شد")){

                                        Toast.makeText(sabte_response.this, "تغیرات لحاظ شد", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(sabte_response.this, "نمیشه نمیدونم چرا", Toast.LENGTH_SHORT).show();
                            }
                        });
                requestQueue.add(stringRequest);


                startActivity(new Intent(sabte_response.this,menu.class));



            }
        });






    }





    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Toast.makeText(getApplicationContext(), "Selected User: " + shir[position], Toast.LENGTH_SHORT).show();




    }
    public void onBackPressed() {
        startActivity(new Intent(sabte_response.this, menu.class));
    }

    public void scast(){
        rs_un=findViewById(R.id.rs_un);
        rhh=findViewById(R.id.rhh);
        rupdate=findViewById(R.id.rupdate);
        rshresponse=findViewById(R.id.rshresponse);
       rshcategori=findViewById(R.id.rshcategori);
        rshtitle=findViewById(R.id.rshtitle);
        rshvisitor=findViewById(R.id.rshvisitor);
        rshtozi=findViewById(R.id.rshtozi);
        zun=findViewById(R.id.zun);
        zun2=findViewById(R.id.zun2);
        title3=findViewById(R.id.title3);

        rshdescrip=findViewById(R.id.rshdescrip);
        rshcode=findViewById(R.id.rshcode);
        rprirority=findViewById(R.id.rprirority);
        rshname=findViewById(R.id.rshname);
        rshtell=findViewById(R.id.rshtell);
        rtresponse=findViewById(R.id.rtresponse);
        priority=findViewById(R.id.priority);
        ctime=findViewById(R.id.ctime);
        cdate=findViewById(R.id.cdate);
        tstatus="پاسخ داده شده";
        zun=findViewById(R.id.zun);
        x4="1";
        serviceGenerator = new ServiceGenerator();
        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        rs_un.setText(sharedPreferences.getString("user", ""));







        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);
    }



    public void getaltikett(int id_tiket){
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = "https://pgtab.info/home/getaltikett?id_tiket="+getIntent().getStringExtra("tiketid");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respo = new JSONObject(response);
                            String Status = respo.getString("id_tiket");


                            if(Status.equals(getIntent().getStringExtra("tiketid"))){


                                ctime.setText(respo.getString("ttime"));
                                cdate.setText(respo.getString("tdate"));
                                rshcategori.setText(respo.getString("tcategori"));

                                String qrshcategori = rshcategori.getText().toString();
                              qqrshcategori = qrshcategori.trim();


                                rshtitle.setText(respo.getString("tcategori")+"/"+respo.getString("ttitle"));
                                title3.setText(respo.getString("ttitle"));
                                String title4 = title3.getText().toString();
                                title5 = title4.trim();


                                rshdescrip.setText(respo.getString("tdescription"));
                                String rshdescrip4 = rshdescrip.getText().toString();
                                rshdescrip5 = rshdescrip4.trim();


                                rshcode.setText(respo.getString("tbgcode"));
                              //  rshcode.setText("کد:"+respo.getString("tbgcode"));
                                String rshcode4 = rshcode.getText().toString();
                                rshcode5 = rshcode4.trim();




                                rshname.setText(respo.getString("bgname"));
                              //  rshname.setText("تماس گیرنده:"+respo.getString("bgname"));
                                String rshname4 = rshname.getText().toString();
                                rshname5 = rshname4.trim();





                                rshtell.setText(respo.getString("btell"));
                             //   rshtell.setText("تلفن: "+respo.getString("btell"));
                                String rshtell4 = rshtell.getText().toString();
                                rshtell5 = rshtell4.trim();





                                rshvisitor.setText("بازاریاب: "+respo.getString("tvisitor"));
                              //  rshvisitor.setText("بازاریاب: "+respo.getString("tvisitor"));
                                String rshvisitor4 = respo.getString("tvisitor").toString();
                                rshvisitor5 = rshvisitor4.trim();







                                rshtozi.setText("موزع: "+respo.getString("x3"));

                                String rshtozi4 = respo.getString("x3").toString();
                                rshtozi5 = rshtozi4.trim();





                                rprirority.setText("نوع فوریت:"+respo.getString("x2"));
                                String rprirority4 =respo.getString("x2").toString();
                                rprirority5 = rprirority4.trim();






                                zun.setText("کاربر ثبت کننده: "+respo.getString("x1"));
                                zun2.setText(respo.getString("x1"));

                                String suser=respo.getString("x1");
                                String x4=respo.getString("x4");








                            }

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



}