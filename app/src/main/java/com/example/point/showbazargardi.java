package com.example.point;

import static com.example.point.CardAdapter.ContactViewHolder.finalcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.carto.graphics.Color;
import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.carto.styles.TextStyle;
import com.carto.styles.TextStyleBuilder;
import com.carto.utils.BitmapUtils;
import com.example.point.Adapter.bazargardiadapter;
import com.example.point.Adapter.shekayatadapter;
import com.example.point.DataModel.bbazargardi;
import com.example.point.network.ReverseService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neshan.common.model.LatLng;
import org.neshan.common.network.RetrofitClientInstance;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.model.Label;
import org.neshan.mapsdk.model.Marker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class showbazargardi extends AppCompatActivity {
    ArrayList<bazargardi> s;
    EditText tun;
    String x1,x2;
    Double z1,z2,z3,z4;
    RecyclerView bazargardishow;
    private Label label;
    MapView bbmap;
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
    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
    PersianDateFormat pdformater2 = new PersianDateFormat("H:i:s");
TextView ttwoo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbazargardi);
        bbmap = findViewById(R.id.bbmap);
        ttwoo = findViewById(R.id.ttwoo);

        tcast();

        PersianDate pdatee = new PersianDate();
        PersianDateFormat pdformater11 = new PersianDateFormat("Y/m/d");
        ttwoo.setText("تاریخ امروز: "+pdformater11.format(pdatee));



        String tiketurl = "https://pgtab.info/home/getalll_bazargardiwithuserdate?bun="+getIntent().getStringExtra("Bun") +"&bdate="+getIntent().getStringExtra("bdate") ;
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





                     //   Toast.makeText(showbazargardi.this, responsee, Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray jsonArray = new JSONArray(responsee);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONArray respoo = new JSONArray(responsee);
                               
                                String hadi = respoo.getString(i);
                                String hadi3 = hadi.replace("[", "");
                                String hadi4 = hadi3.replace("]", "");
                                JSONObject respo = new JSONObject(hadi4);
                                x1 = respo.getString("bx1");
                                x2 = respo.getString("bx2");
                                z1 = Double.valueOf(x1);
                                z2 = Double.valueOf(x2);
                                final Double z3 = z1.doubleValue();
                                final Double z4 = z2.doubleValue();

                                addUserMarker(new LatLng(z3, z4));




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








        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        Holder = locationManager.getBestProvider(criteria, false);
        context = getApplicationContext();
        initMap();
        initViews();
        bbmap.moveCamera(new LatLng(38.08124012659, 46.29577500654), 0.25f);
        bbmap.setZoom(10, 0);
    //  addUserMarker(new LatLng(38.08124012659, 46.29577500654));





    }


    public List<bbazargardi> createlist2(String responsee){
        List<bbazargardi> lst2 = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(responsee);
            for(int i=0; i < jsonArray.length();i++){
                bbazargardi info = new bbazargardi();
                JSONObject ttemp = jsonArray.getJSONObject(i);
                info.setId_bazargardi(ttemp.getInt("id_bazargardi"));
                info.setBtypee(ttemp.getString("btypee"));
                info.setBname(ttemp.getString("bname"));
                info.setBsarparast(ttemp.getString("bsarparast"));
                info.setBtell(ttemp.getString("btell"));
                info.setBrel(ttemp.getString("brel"));
                info.setBgrade(ttemp.getString("bgrade"));
                info.setBcode(ttemp.getString("bcode"));
                info.setBvisitor(ttemp.getString("bvisitor"));
                info.setBrannade(ttemp.getString("brannade"));
                info.setBsoper(ttemp.getString("bsoper"));
                info.setBrezayatv(ttemp.getString("brezayatv"));
                info.setBrezayattoo(ttemp.getString("brezayattoo"));
                info.setBdarkhast(ttemp.getString("bdarkhast"));
                info.setBtoosiye(ttemp.getString("btoosiye"));
                info.setBnazar(ttemp.getString("bnazar"));
                info.setBsh1(ttemp.getString("bsh1"));
                info.setBsh2(ttemp.getString("bsh2"));
                info.setBsh3(ttemp.getString("bsh3"));
                info.setBkh1(ttemp.getString("bkh1"));
                info.setBkh2(ttemp.getString("bkh2"));
                info.setBkh3(ttemp.getString("bkh3"));
                info.setBshs1(ttemp.getString("bshs1"));
                info.setBshs2(ttemp.getString("bshs2"));
                info.setBshs3(ttemp.getString("bshs3"));
                info.setBk1(ttemp.getString("bk1"));
                info.setBk2(ttemp.getString("bk2"));
                info.setBk3(ttemp.getString("bk3"));
                info.setBshd1(ttemp.getString("bshd1"));
                info.setBshd2(ttemp.getString("bshd2"));
                info.setBshd3(ttemp.getString("bshd3"));
                info.setBma1(ttemp.getString("bma1"));
                info.setBma2(ttemp.getString("bma2"));
                info.setBma3(ttemp.getString("bma3"));
                info.setBp1(ttemp.getString("bp1"));
                info.setBp2(ttemp.getString("bp2"));
                info.setBp3(ttemp.getString("bp3"));
                info.setBdb1(ttemp.getString("bdb1"));
                info.setBdb2(ttemp.getString("bdb2"));
                info.setBdb3(ttemp.getString("bdb3"));
                info.setBdn1(ttemp.getString("bdn1"));
                info.setBdn2(ttemp.getString("bdn2"));
                info.setBdn3(ttemp.getString("bdn3"));
                info.setBab1(ttemp.getString("bab1"));
                info.setBab2(ttemp.getString("bab2"));
                info.setBab3(ttemp.getString("bab3"));
                info.setBx1(ttemp.getString("bx1"));
                info.setBx2(ttemp.getString("bx2"));
                info.setBaddress(ttemp.getString("baddress"));
                info.setBtime(ttemp.getString("btime"));
                info.setBdate(ttemp.getString("bdate"));
                info.setByakhurl(ttemp.getString("byakhurl"));
                info.setBun(ttemp.getString("bun"));
                lst2.add(info);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst2;

    }
    public void setUpRecyclerView(String responsee){
        LinearLayoutManager layout = new LinearLayoutManager(showbazargardi.this, LinearLayoutManager.VERTICAL, false);
        bazargardishow.setLayoutManager(layout);

        bazargardiadapter shadpter = new bazargardiadapter(createlist2(responsee),showbazargardi.this);
        bazargardishow.setAdapter(shadpter);






    }
    public void tcast(){
        tun=findViewById(R.id.tun);

        bazargardishow=findViewById(R.id.bazargardishow);



   //     SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
     //   tun.setText(sharedPreferences.getString("user", ""));
        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);

    }
    public void onBackPressed() {
        startActivity(new Intent(showbazargardi.this, resadbazargardi.class));
    }



    protected void onStart() {
        super.onStart();
        // everything related to ui is initialized here
        initLayoutReferences();
    }

    // Initializing layout references (views, map and map events)
    private void initLayoutReferences() {
        // Initializing views
        initViews();
        // Initializing mapView element
        initMap();

        // when long clicked on map, a marker is added in clicked location
        bbmap.setOnMapLongClickListener(latLng -> addLabel(latLng));

    }
    private void initViews() {
        bbmap = findViewById(R.id.bbmap);

    }
    private void initMap() {


        // Setting map focal position to a fixed position and setting camera zoom



        bbmap.setOnCameraMoveFinishedListener(i -> {


        });
    }
    public void focusOnUserLocation(View view) {
        if (userLocation != null){
            bbmap.moveCamera(new LatLng(38.08124012659, 46.29577500654), 0.25f);
            bbmap.setZoom(10, 0.15f);
        }
        else {
            bbmap.moveCamera(new LatLng(38.08124012659, 46.29577500654), 0.25f);
            bbmap.setZoom(10, 0.15f);
        }
    }

    private void addUserMarker(LatLng loc) {
        //remove existing marker from map




        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
        MarkerStyle markSt = markStCr.buildStyle();



        // Creating user marker
        marker = new Marker(loc, markSt);

        // Adding user marker to map!
        bbmap.addMarker(marker);


    }
    private void addLabel(LatLng loc) {

        // Creating text style. We should use an object of type TextStyleBuilder, set all features on it
        // and then call buildStyle method on it. This method returns an object of type TextStyle.
        TextStyleBuilder textStyleBuilder = new TextStyleBuilder();
        textStyleBuilder.setFontSize(15f);
        textStyleBuilder.setColor(new Color((short) 255, (short) 150, (short) 150, (short) 255));
        TextStyle textStyle = textStyleBuilder.buildStyle();

        // Creating label
        label = new Label(loc, textStyle, "fff");

        // Adding marker to labelLayer, or showing label on map!
        bbmap.addLabel(label);
    }

    public void toggleOnlineLayer(View view) {
    }
}