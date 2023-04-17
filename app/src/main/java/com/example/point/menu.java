package com.example.point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;

public class menu extends AppCompatActivity {

    Button one, two, three, four, five, six, seven, ehight, exitee,ten,eleven;
    EditText unmenu;
    String hh, version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        ehight = findViewById(R.id.ehight);
        exitee = findViewById(R.id.exitee);
        ten = findViewById(R.id.ten);
        eleven = findViewById(R.id.eleven);
        unmenu = findViewById(R.id.unmenu);
        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);


        version = "1";
        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        unmenu.setText(sharedPreferences.getString("user", ""));

        castmenu();
        int Versionn = Integer.parseInt(version);
        chekVersion(Versionn);



        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(menu.this, MainActivity.class));
                //   startActivity(intentone);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this, bazargardi.class));
                //   startActivity(intenttwo);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this, showcustpoint.class));
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this, vyakh.class));
            }
        });
    //    five
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // startActivity(new Intent(menu.this, showbazargardi.class));
               startActivity(new Intent(menu.this, resadbazargardi.class));
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(menu.this, sabte_shekayat.class));

            }
        });
        ehight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this, tiketha.class));
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this, resadeVisitor2.class));
            }
        });
        eleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this, report.class));
            }
        });

        exitee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }


    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void chekVersion(int id_V) {

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        String url = "https://pgtab.info/Home/getv?id_V=" + id_V;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respo = new JSONObject(response);
                            String Status = respo.getString("v_now");


                            if (Status.equals("true")) {

                                //    Toast.makeText(sabte_response.this, "تغیرات لحاظ شد", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(menu.this, update.class));
                            }

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

    public void castmenu() {




    }

}
