package com.example.point;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.point.DataModel.bbazargardi;
import com.example.point.DataModel.shekayat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class sabte_shekayat extends AppCompatActivity {

    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
    PersianDateFormat pdformater2 = new PersianDateFormat("H:i:s");
    ServiceGenerator serviceGenerator;
    ArrayList<shekayat> shekayatlist;
    Button shcreate;
    TextView shresponse,shresponseuser,shstatus,hh;
    EditText s_un,shtitle,shdescrip,shcode,shname,shtell,tresponse;
    String sdate,stime,trdate,trtime,tstatus,x3,x4;
    Spinner shcategori,shvisitor,shtozi,prirority;
    String[] Sshcategori = {"انتخاب گروه بندی شکایت","فروش", "توزیع", "کیفی", "درخواست", "فوری", "متفرقه غیر مهم"};
    String[] Sshvisitor = {"انتخاب ویزیتور", "پيشقدم", "علي صمدي فرد", "ياسر داداشي", "حامد غفاري", "کياني", "يوسف محمدپور", "عمران پور", "ناظري", "امير حسين اميني", "محمدحسين نيا", "دهقاني", "گروه آقای احیایی", "سيامک محمدپور", "بهنام سرخابي", "رضا پارچه بافيه", "جديري", "محمد نامور", "جعفر حيدريان", "پرويزقهاري", "رضا جنگجو", "بهنام پاک رفته", "گروه آقای شیرینی", "ليلا محمدي", "رقيه غفاري", "دوست کام", "سلطاني", "رضايي", "مينا اصغري", "کريمي", "سحر حسيني", "گروه خانم برزم"};
    String[] Sshtozi = {"انتخاب موزع", "نام موزع", "وهاب اللهويردي زاده(تعاوني جهاد)", "وحيد محمدي مغانلو", "وحيد رسولي اقدم", "ميثم ميرزايي قديم-تعاوني جهاد", "مهدي پوري", "مهدي پور نقي-هتلها", "محمد آقايان (ره آورد مبين)", "محرم چمني - آذرپخش", "فردين صادق", "علي رسولي2(تعاوني جهاد)", "علي چمني- آذرپخش", "صياد وظايف منور", "سيروس جمال پور", "سعيد خدايي", "رهاورد مبين(مهدي محمدي نژاد)", "رهاورد مبين(عليرضا رسولي اقدم)", "رهاورد مبين(عبداله آقايان)", "رهاورد مبين(صفر محمدزاده)", "رهاورد مبين (يونس شوري )", "رهاورد مبين (عليرضا جعفري )", "رهاورد مبين (رحمان شکوهي کيا )", "جهاد (سياوش هاشمي )", "جواد قدسي (تعاوني جهاد)", "جابر زاهدي (تعاوني جهاد )", "تعاوني جهاد مقصود خدايي", "تعاوني جهاد محمدتقي زارع", "تعاوني جهاد شكر الله كريم پور", "تعاوني جهاد (علي اصغر علي قلي زاده )", "تعاوني جهاد (داود امير سفيدان )", "تعاوني جهاد (حسين زارع علويق )", "تعاوني جهاد (حسن فتحي مددلو )", "بهزاد پناهي (ره آورد مبين)", "امير شامخي", "التفات دنيوي", "اذر پخش توزيع گستر رضا سلطاني", "اذر پخش توزيع گستر (برات فداکار", "اذر پخش  - تيمور جعفري", "(تعاوني جهاد)يونس فتحي مددلو", "(تعاوني جهاد)مهدي نصير زاده", "(اذر پخش توزيع گستر)غلامرضا قنبرزاده", "(اذر پخش توزيع گستر)داوود جعفري"};
    String[] prio = {"نوع اولویت", "فوری", "مهم", "عادی", "متفرقه"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabte_shekayat);
        scast();


        ArrayAdapter<String> adapterc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Sshcategori);
        ArrayAdapter<String> adapterv = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Sshvisitor);
        ArrayAdapter<String> adaptert = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Sshtozi);
        ArrayAdapter<String> adapterp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prio);
        adapterc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptert.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shcategori.setAdapter(adapterc);
        shvisitor.setAdapter(adapterv);
        shtozi.setAdapter(adaptert);
        prirority.setAdapter(adapterp);
       // shcategori.setOnItemSelectedListener(this);


        sdate=(pdformater1.format(pdate));
        trdate=(pdformater1.format(pdate));
        stime=(pdformater2.format(pdate));
        trtime=(pdformater2.format(pdate));



        shcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shshcategori = shcategori.getSelectedItem().toString();
                String shshvisitor = shvisitor.getSelectedItem().toString();
                String shshtozi = shtozi.getSelectedItem().toString();
                String shprirority = prirority.getSelectedItem().toString();
                String Tshtitle = shtitle.getText().toString();
                String Tshdescrip = shdescrip.getText().toString();
                String Tshcode = shcode.getText().toString();
                String Tshname = shname.getText().toString();
                String Tshtell = shtell.getText().toString();
                String Ttresponse = tresponse.getText().toString();
                String Ts_un = s_un.getText().toString();
                String Ttstatus = tstatus.toString();
                String Tshprirority = shprirority.toString();
                String Tshshtozi = shshtozi.toString();


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
                    String url = "https://pgtab.info/Home/insert_tiket?tdate="+tdate+"&ttime="+tshtime+"&tcategori="+tshshcategori+"&ttitle="+tshtitle+"&tdescription="+tshdescrip+"&tbgcode="+tshcode+"&bgname="+tshname+"&btell="+tshtell+"&tvisitor="+tshshvisitor+"&tresponse="+ttresponse+"&trdate="+ttrdate+"&trtime="+ttrtime+"&truser="+truser+"&tstatus="+ttstatus+"&x1="+tx1+"&x2="+tx2+"&x3="+tx3+"&x4="+tx4;
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                            Toast.makeText(sabte_shekayat.this, "با موفقیت ثبت شد" , Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(sabte_shekayat.this, tiketha.class));



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



    }





    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Toast.makeText(getApplicationContext(), "Selected User: " + shir[position], Toast.LENGTH_SHORT).show();




    }
    public void onBackPressed() {
        startActivity(new Intent(sabte_shekayat.this, menu.class));
    }

    public void scast(){
        s_un=findViewById(R.id.s_un);
        hh=findViewById(R.id.hh);
        shcreate=findViewById(R.id.shcreate);
        shresponse=findViewById(R.id.shresponse);
        shcategori=findViewById(R.id.shcategori);
        shtitle=findViewById(R.id.shtitle);
        shvisitor=findViewById(R.id.shvisitor);
        shtozi=findViewById(R.id.shtozi);
        shresponseuser=findViewById(R.id.shresponseuser);
        shstatus=findViewById(R.id.shstatus);
        shdescrip=findViewById(R.id.shdescrip);
        shcode=findViewById(R.id.shcode);
        prirority=findViewById(R.id.prirority);
        shname=findViewById(R.id.shname);
        shtell=findViewById(R.id.shtell);
        tresponse=findViewById(R.id.tresponse);
        tstatus="در انتظار پاسخ";

        x4="1";
        serviceGenerator = new ServiceGenerator();
        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        s_un.setText(sharedPreferences.getString("user", ""));







        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);
    }
}