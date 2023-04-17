package com.example.point;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.point.API.ServiceGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class loogin extends AppCompatActivity {
    ServiceGenerator serviceGenerator;
    ProgressBar progressBar3;
    EditText Eusername, Epassword;
    Button Signin, exite;
    CheckBox forget;
    TextView waite;
    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
    PersianDateFormat pdformater2 = new PersianDateFormat("H:i:s");
    PersianDateFormat pdformater3 = new PersianDateFormat("Y/m/d/H:i:s");
    String dn, dn1, dn2, dn3,token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loogin);
/*
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FireBase", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("Token",token);
                        et.setText(token);

                        // Log and toast
                        Toast.makeText(loogin.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


 */






        castlogin();
        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        Eusername.setText(sharedPreferences.getString("user", ""));
        Epassword.setText(sharedPreferences.getString("pass", ""));
        forget.setChecked(true);
        dn = android.os.Build.MODEL;
        dn1 = Settings.Secure.getString(loogin.this.getContentResolver(), Settings.Secure.ANDROID_ID);

    //    dn2 = Settings.Secure.getString(getContentResolver(), "bluetooth_name");
        dn3 = "Model:" + dn + "   " + "BName:" + dn2 + "   " + "A_ID:" + dn1;
        exite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });


        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (forget.isChecked()) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", Eusername.getText().toString());
                    editor.putString("pass", Epassword.getText().toString());
                    editor.apply();
                    //   login();
                    newlogin(Eusername.getText().toString(), Epassword.getText().toString());
                    progressBar3.setVisibility(View.VISIBLE);
                    waite.setVisibility(View.VISIBLE);


                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", "");
                    editor.putString("pass", "");

                    editor.apply();
                    login();
                }


            }
        });


    }

    public void login() {
        if (!checkInsertEmpty()) {
            final String username = Eusername.getText().toString().trim();
            final String password = Epassword.getText().toString().trim();



            Call<ResponseBody> call = serviceGenerator.getService().loogin(username, password);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String responseresult = response.body().string();
                        if (responseresult.equals("ok")) {
                            Toast.makeText(loogin.this, "با موفقیت وارد حساب کاربری خود شدید", Toast.LENGTH_SHORT).show();


                            //  Intent intent2=new Intent(getApplicationContext(),MainActivity.class);
                            Intent intentmenu = new Intent(getApplicationContext(), menu.class);
                            intentmenu.putExtra("username", Eusername.getText().toString());
                            RequestQueue requestQueue;
                            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                            Network network = new BasicNetwork(new HurlStack());
                            requestQueue = new RequestQueue(cache, network);
                            requestQueue.start();
                            String url = "https://pgtab.info/Home/insert_lastupdate?id_update=10&username_l=" + username + "&last_update=" + pdformater1.format(pdate).toString() + "&xx1=" + pdformater2.format(pdate).toString() + "&xx2=" + dn3;
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                        }
                                    },
                                    new com.android.volley.Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    });
                            requestQueue.add(stringRequest);
                            startActivity(intentmenu);


                            //    startActivity(new Intent(loogin.this, menu.class));

                        } else if (responseresult.equals("نام کاربری یا رمز اشتباه هست")) {
                            Toast.makeText(loogin.this, "نام کاربری یا رمز اشتباه هست", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("اطلاعات اشتباه هست", t.toString());
                }
            });
        }

    }


    private boolean checkInsertEmpty() {
        String username = Eusername.getText().toString().trim();
        String password = Epassword.getText().toString().trim();
        boolean flag = false;
        if (username.isEmpty()) {
            Eusername.setError("نام کاربری خالی است");
            flag = true;
        }
        if (password.isEmpty()) {
            Epassword.setError("رمز خالی است");
            flag = true;
        }
        return flag;
    }


    public void onBackPressed() {
        finishAffinity();
    }
    // public void onBackPressed() {System.exit();}


    public void castlogin() {










        serviceGenerator = new ServiceGenerator();
        Eusername = findViewById(R.id.l_username);
        Epassword = findViewById(R.id.l_password);
        Signin = findViewById(R.id.sign);
        exite = findViewById(R.id.exit);
        forget = findViewById(R.id.forget);
        progressBar3 = findViewById(R.id.progressBar3);
        waite = findViewById(R.id.waite);


        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);


    }

    public void newlogin(String username, String password) {
        final String usernamee = Eusername.getText().toString().trim();
        final String passwordd = Epassword.getText().toString().trim();
        String url7 = "https://pgtab.info/home/newlogin?name=" + usernamee + "&password=" + passwordd;


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url7,
                new com.android.volley.Response.Listener<String>() {


                    @Override
                    public void onResponse(String bresponseed) {
                        try {

                            JSONArray respoobd = new JSONArray(bresponseed);
                            String bbhadi = respoobd.getString(0);
                            // String hadi2 = hadi.substring(0, hadi.length() - 1);
                            String bhadid = bbhadi.replace("[", "");
                            String bbhadi4 = bhadid.replace("]", "");
                            JSONObject brespobd = new JSONObject(bbhadi4);



                            if (brespobd.getString("xx5").equals("true")) {

                                RequestQueue requestQueuee;
                                Cache cachee = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                                Network networkk = new BasicNetwork(new HurlStack());
                                requestQueuee = new RequestQueue(cachee, networkk);
                                requestQueuee.start();
                                String urllt = "https://pgtab.info/Home/update_newlogin?id_user="+brespobd.getString("id_user")+"&name="+brespobd.getString("name")+"&mantage="+brespobd.getString("mantage")+"&post="+brespobd.getString("post")+"&tell="+brespobd.getString("tell")+"&password="+brespobd.getString("password")+"&picurl="+brespobd.getString("picurl")+"&token="+brespobd.getString("token")+"&devicename="+dn3+"&deviceid="+dn2+"&lastupdate="+pdformater3.format(pdate).toString()+"&xx1="+brespobd.getString("xx1")+"&xx2="+brespobd.getString("xx2")+"&xx3="+brespobd.getString("xx3")+"&xx4="+brespobd.getString("xx4")+"&xx5="+brespobd.getString("xx5");
                                StringRequest stringRequestt = new StringRequest(Request.Method.GET, urllt,
                                        new com.android.volley.Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                              //  Toast.makeText(loogin.this, "ok", Toast.LENGTH_SHORT).show();
                                            }
                                            
                                        },
                                        new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(loogin.this, "no", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                requestQueuee.add(stringRequestt);

                                Toast.makeText(loogin.this, "با موفقیت وارد شدید", Toast.LENGTH_SHORT).show();

                                Intent intentmenu = new Intent(getApplicationContext(), menu.class);
                                intentmenu.putExtra("username", Eusername.getText().toString());
                                RequestQueue requestQueue;
                                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                                Network network = new BasicNetwork(new HurlStack());
                                requestQueue = new RequestQueue(cache, network);
                                requestQueue.start();
                                String url = "https://pgtab.info/Home/insert_lastupdate?id_update=10&username_l=" + username + "&last_update=" + pdformater1.format(pdate).toString() + "&xx1=" + pdformater2.format(pdate).toString() + "&xx2=" + dn3;
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new com.android.volley.Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                            }
                                        },
                                        new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            }
                                        });
                                requestQueue.add(stringRequest);
                                startActivity(intentmenu);








                            }


                            else if (brespobd.getString("name").equals("er") ){
                                Toast.makeText(loogin.this, "نام کاربری یا رمز عبور اشتباه هست", Toast.LENGTH_SHORT).show();
                                Intent intentmenut = new Intent(getApplicationContext(), loogin.class);
                                startActivity(intentmenut);
                            }

                            else {
                                Toast.makeText(loogin.this, "نام کاربری شما غیر فعال شده با مدیر تماس بگیرین", Toast.LENGTH_SHORT).show();
                                Intent intentmenutt = new Intent(getApplicationContext(), loogin.class);
                                startActivity(intentmenutt);
                            }


                        } catch (JSONException e) {

                            e.printStackTrace();


                        }


                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(loogin.this, "متاسفانه مشکلی در ارتباط با سرور رخ داده، مجددا تلاش کنین", Toast.LENGTH_SHORT).show();
                    }


                });
        requestQueue.add(stringRequest);


    }
/*
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }


 */
}
