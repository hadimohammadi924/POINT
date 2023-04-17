package com.example.point;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.carto.utils.BitmapUtils;
import com.example.point.Adapter.shekayatadapter;
import com.example.point.DataModel.shekayat;
import com.example.point.network.ReverseService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neshan.common.model.LatLng;
import org.neshan.common.network.RetrofitClientInstance;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.model.Marker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class finalcustforsoper extends AppCompatActivity {

    ImageView fimageveiw, bfimageveiw, bfimageveiww;
    TextView texxt, description5, custname, onedata, twodata, tamasadres, bazarurl, bazarurll, bb1, bb2, bb3, bb4, bb5, bb6, bb7, bb8, bb9, bb10, t1, t2, t3, t4, fCUser, fticketsID, ftitleTickets, fcategori, fdatetime, ftextDescription, fticketsReplyTitle, fresponseuser, frtime, fticketsStatus;
    MapView fbmap;
    String x1, x2;
    Double z1, z2, z3, z4;
    RecyclerView ticketsListRecyclerView2;
    public static final int RequestPermissionCode = 1;
    Intent intent1;
    Location location;
    LocationManager locationManager;
    boolean GpsStatus = false;
    Criteria criteria;
    String Holder;
    Context context;
    //*****//
    private static final String TAG = UserLocation.class.getName();
    // sed to track request permissions
    final int REQUEST_CODE = 123;
    // location updates interval - 1 sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // fastest updates interval - 1 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // map UI element
    private MapView bmap;
    // User's current location
    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private String lastUpdateTime;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    private Marker marker;
    public static final ReverseService getDataService = RetrofitClientInstance.getRetrofitInstance().create(ReverseService.class);
    private final PublishSubject<LatLng> locationPublishSubject = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalcust);

        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);


        //  Toast.makeText(this, getIntent().getStringExtra("pgcodee"), Toast.LENGTH_SHORT).show();
        // getIntent().getStringExtra("pgcode");
        fimageveiw = findViewById(R.id.fimageveiw);
        bfimageveiw = findViewById(R.id.bfimageveiw);
        fbmap = findViewById(R.id.fbmap);
        // texxt = findViewById(R.id.texxt);
        custname = findViewById(R.id.custname);
        onedata = findViewById(R.id.onedata);
        twodata = findViewById(R.id.twodata);
        tamasadres = findViewById(R.id.tamasadres);
        bazarurl = findViewById(R.id.bazarurl);
        bb1 = findViewById(R.id.bb1);
        bb2 = findViewById(R.id.bb2);
        bb3 = findViewById(R.id.bb3);
        bb4 = findViewById(R.id.bb4);
        bb5 = findViewById(R.id.bb5);
        bb6 = findViewById(R.id.bb6);
        bb7 = findViewById(R.id.bb7);
        bb8 = findViewById(R.id.bb8);
        bb9 = findViewById(R.id.bb9);
        bb10 = findViewById(R.id.bb10);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        fCUser = findViewById(R.id.fCUser);
        fticketsID = findViewById(R.id.fticketsID);
        ftitleTickets = findViewById(R.id.ftitleTickets);
        fcategori = findViewById(R.id.fcategori);
        fdatetime = findViewById(R.id.fdatetime);
        ftextDescription = findViewById(R.id.ftextDescription);
        fticketsReplyTitle = findViewById(R.id.fticketsReplyTitle);
        fresponseuser = findViewById(R.id.fresponseuser);
        frtime = findViewById(R.id.frtime);
        fticketsStatus = findViewById(R.id.fticketsStatus);
        ticketsListRecyclerView2 = findViewById(R.id.ticketsListRecyclerView2);
        bfimageveiww = findViewById(R.id.bfimageveiww);
        bazarurll = findViewById(R.id.bazarurll);
        description5 = findViewById(R.id.description5);


        getcust(getIntent().getStringExtra("pgcodee"));
        getbazar(getIntent().getStringExtra("pgcodee"));
        getdata(getIntent().getStringExtra("pgcodee"));
        //   gettiket(getIntent().getStringExtra("pgcodee"));
        gettiket2(getIntent().getStringExtra("pgcodee"));
        getvy(getIntent().getStringExtra("pgcodee"));


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        Holder = locationManager.getBestProvider(criteria, false);
        context = getApplicationContext();


    }

    public void getcust(String pgcodee) {
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        String url = "https://pgtab.info/home/getcustwithcode?pgcode=" + getIntent().getStringExtra("pgcodeee");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String responsee) {


                        try {
                            JSONArray respoo = new JSONArray(responsee);
                            String hadi = respoo.getString(0);

                            String hadi3 = hadi.replace("[", "");
                            String hadi4 = hadi3.replace("]", "");

                            JSONObject respo = new JSONObject(hadi4);
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher_round);

                            Glide.with(finalcustforsoper.this).load(respo.getString("picURL")).apply(options).into(fimageveiw);
                            x1 = respo.getString("x1");
                            x2 = respo.getString("x2");
                            z1 = Double.valueOf(x1);
                            z2 = Double.valueOf(x2);
                            final Double z3 = z1.doubleValue();
                            final Double z4 = z2.doubleValue();

                            addUserMarker(new LatLng(z3, z4));
                            initMap();
                            initViews();
                            fbmap.moveCamera(new LatLng(z3, z4), 0);
                            fbmap.setZoom(15, 0);
                            custname.setText(respo.getString("pgcode") + " " + respo.getString("type") + ":" + respo.getString("cust") + "   " + "به مدیریت:" + respo.getString("name"));
                            tamasadres.setText("اطلاعات تماس و ادرس:  " + respo.getString("tell1") + "****" + respo.getString("mobile") + "   " + respo.getString("address") + "   " + "نوع همکاری:" + respo.getString("typeRel") + "    " + "تاریخ شناسایی" + respo.getString("date") + " " + "توسط: " + respo.getString("un"));


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

    public void getbazar(String pgcodee) {

        RequestQueue requestQueuee;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueuee = new RequestQueue(cache, network);
        requestQueuee.start();
        String url2 = "https://pgtab.info/home/getalll_bazargardiwithcodeee?bcode=" + getIntent().getStringExtra("pgcodeee");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,

                new Response.Listener<String>() {

            /*
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respo = new JSONObject(response);
                           String Status = respo.getString("pgcode");
                         //   String Status ="55";


                           if(Status.equals(getIntent().getStringExtra("pgcode"))){

                               // texxt.setText(respo.getString("picURL"));
                                Toast.makeText(finalcust.this, response, Toast.LENGTH_SHORT).show();
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.mipmap.ic_launcher_round)
                                        .error(R.mipmap.ic_launcher_round);

                                Glide.with(finalcust.this).load(respo.getString("picURL")).apply(options).into(fimageveiw);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();  Toast.makeText(finalcust.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

             */

                    @Override
                    public void onResponse(String bresponsee) {


                        try {

                            JSONArray respoob = new JSONArray(bresponsee);
                            String bhadi = respoob.getString(0);
                            // String hadi2 = hadi.substring(0, hadi.length() - 1);
                            String bhadi3 = bhadi.replace("[", "");
                            String bhadi4 = bhadi3.replace("]", "");

                            JSONObject respob = new JSONObject(bhadi4);
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher_round);

                            Glide.with(finalcustforsoper.this).load(respob.getString("byakhurl")).apply(options).into(bfimageveiw);
                            bazarurl.setText("تصویر یخچال در تاریخ:" + respob.getString("bdate") + " " + "توسط" + respob.getString("bun"));
                            bb1.setText("شیرپاستوریزه:" + respob.getString("bsh1") + "/" + respob.getString("bsh2") + "/" + respob.getString("bsh2"));
                            bb2.setText("گروه خامه: " + respob.getString("bkh1") + "/" + respob.getString("bkh2") + "/" + respob.getString("bkh3"));
                            bb3.setText("شیراستریل یک لیتری:" + respob.getString("bshs1") + "/" + respob.getString("bshs2") + "/" + respob.getString("bshs3"));
                            bb4.setText("گروه کره" + respob.getString("bk1") + "/" + respob.getString("bk2") + "/" + respob.getString("bk3"));
                            bb5.setText("شیر200ساده و طعم دار:" + respob.getString("bshd1") + "/" + respob.getString("bshd2") + "/" + respob.getString("bshd3"));
                            bb6.setText("ماست دبه و لیوانی:" + respob.getString("bma1") + "/" + respob.getString("bma2") + "/" + respob.getString("bma3"));
                            bb7.setText("انواع پنیر:" + respob.getString("bp1") + "/" + respob.getString("bp2") + "/" + respob.getString("bp3"));
                            bb8.setText("دوغ بطری:" + respob.getString("bdb1") + "/" + respob.getString("bdb2") + "/" + respob.getString("bdb3"));
                            bb9.setText("دوغ نایلونی:" + respob.getString("bdn1") + "/" + respob.getString("bdn2") + "/" + respob.getString("bdn3"));
                            bb10.setText("آبمیوه:" + respob.getString("bab1") + "/" + respob.getString("bab2") + "/" + respob.getString("bab3"));
                            t1.setText(respob.getString("bdarkhast"));
                            t2.setText(respob.getString("btoosiye"));
                            t3.setText(respob.getString("bnazar"));
                            t4.setText("بازاریاب: " + respob.getString("bvisitor") + " با رضایت " + respob.getString("brezayatv") + "  و موزع: " + respob.getString("brannade") + " بارضایت " + respob.getString("brezayattoo"));
                            //  t4.setText("بازاریاب: "+respob.getString("bvisitor")+" با رضایت "+respob.getString("brezayatv")+"  و موزع: ");

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
        requestQueuee.add(stringRequest);

    }

    public void getdata(String pdcodee) {

        RequestQueue requestQueueed;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueueed = new RequestQueue(cache, network);
        requestQueueed.start();
        String url3 = "https://pgtab.info/home/getaldatawithcode?pdcode=" + getIntent().getStringExtra("pgcodeee");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url3,

                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String bresponseed) {


                        try {

                            JSONArray respoobd = new JSONArray(bresponseed);
                            String bbhadi = respoobd.getString(0);
                            // String hadi2 = hadi.substring(0, hadi.length() - 1);
                            String bhadid = bbhadi.replace("[", "");
                            String bbhadi4 = bhadid.replace("]", "");

                            JSONObject brespobd = new JSONObject(bbhadi4);
                            onedata.setText(brespobd.getString("pddata"));
                            twodata.setText(brespobd.getString("pdx5"));


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
        requestQueueed.add(stringRequest);

    }

    public void gettiket2(String pdcodee) {


        String url88 = "https://pgtab.info/home/getaltikettwithcode?tbgcode=" + getIntent().getStringExtra("pgcodeee");


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url88,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responsee) {
                        setUpRecyclerView2(responsee);
                        // Toast.makeText(finalcust.this, responsee, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);

    }

    public void getvy(String pgcodee) {

        RequestQueue requestQueuee;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueuee = new RequestQueue(cache, network);
        requestQueuee.start();
        String url2 = "https://pgtab.info/home/getvy?c_code=" + getIntent().getStringExtra("pgcodeee");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,

                new Response.Listener<String>() {

            /*
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respo = new JSONObject(response);
                           String Status = respo.getString("pgcode");
                         //   String Status ="55";


                           if(Status.equals(getIntent().getStringExtra("pgcode"))){

                               // texxt.setText(respo.getString("picURL"));
                                Toast.makeText(finalcust.this, response, Toast.LENGTH_SHORT).show();
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.mipmap.ic_launcher_round)
                                        .error(R.mipmap.ic_launcher_round);

                                Glide.with(finalcust.this).load(respo.getString("picURL")).apply(options).into(fimageveiw);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();  Toast.makeText(finalcust.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

             */

                    @Override
                    public void onResponse(String bresponsee) {


                        try {

                            JSONArray respoobb = new JSONArray(bresponsee);
                            String bhadii = respoobb.getString(0);
                            // String hadi2 = hadi.substring(0, hadi.length() - 1);
                            String bhadi36 = bhadii.replace("[", "");
                            String bhadi46 = bhadi36.replace("]", "");

                            JSONObject respobt = new JSONObject(bhadi46);
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher_round);

                            Glide.with(finalcustforsoper.this).load(respobt.getString("url")).apply(options).into(bfimageveiww);
                            bazarurll.setText("تصویر یخچال بازاریاب در تاریخ:" + respobt.getString("date") + " " + "توسط" + respobt.getString("userv"));
                            description5.setText("توضیح بازاریاب: " + respobt.getString("x1"));

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
        requestQueuee.add(stringRequest);

    }

    public List<shekayat> createlist2(String responsee) {

        List<shekayat> lst2 = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(responsee);

            for (int i = 0; i < jsonArray.length(); i++) {
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

    public void setUpRecyclerView2(String responsee) {
        LinearLayoutManager layout = new LinearLayoutManager(finalcustforsoper.this, LinearLayoutManager.VERTICAL, false);
        ticketsListRecyclerView2.setLayoutManager(layout);

        shekayatadapter shadpter = new shekayatadapter(createlist2(responsee), finalcustforsoper.this);
        ticketsListRecyclerView2.setAdapter(shadpter);

    }


    private void initLayoutReferences() {
        // Initializing views
        initViews();
        // Initializing mapView element
        initMap();

    }


    private void initViews() {
        fbmap = findViewById(R.id.fbmap);

    }

    // Initializing map
    private void initMap() {


        // Setting map focal position to a fixed position and setting camera zoom


        fbmap.setOnCameraMoveFinishedListener(i -> {


        });
    }

    public void focusOnUserLocation(View view) {
        if (userLocation != null) {
            bmap.moveCamera(
                    new LatLng(z3, z4), 0.25f);
            bmap.setZoom(20, 0.15f);
        }
    }

    private void addUserMarker(LatLng loc) {
        //remove existing marker from map


        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating user marker
        marker = new Marker(loc, markSt);

        // Adding user marker to map!
        fbmap.addMarker(marker);
    }


    public void onBackPressed() {
        startActivity(new Intent(finalcustforsoper.this, showcustpoint.class));
    }


}