package com.example.point;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.IOException;
import java.util.ArrayList;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.point.API.ServiceGenerator;
import com.example.point.Adapter.customAdapter;
import com.example.point.DataModel.customer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentSender;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.carto.utils.BitmapUtils;
import com.example.point.address.NeshanAddress;
import com.example.point.network.ReverseService;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.neshan.common.model.LatLng;
import org.neshan.common.network.RetrofitClientInstance;
import org.neshan.mapsdk.MapView;
import org.neshan.mapsdk.model.Marker;


import java.text.DateFormat;
import java.util.Date;

import io.reactivex.subjects.PublishSubject;


public class MainActivity extends AppCompatActivity implements LocationListener, customAdapter.customAdapterInterface, View.OnClickListener {


    Boolean isFileChosen = false;
    private ArrayList<FileUtils> selectedFiles = new ArrayList<>();



    //*****//
    private static final String TAG = UserLocation.class.getName();

    // used to track request permissions
    final int REQUEST_CODE = 123;

    // location updates interval - 1 sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    // fastest updates interval - 1 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    // map UI element
    private MapView map;

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

    //ui elements
    private TextView addressTitle, latt;
    private ProgressBar progressBar;
//**//


    ServiceGenerator serviceGenerator;
    ArrayList<customer> customerlist;
    customAdapter customAdapter;
    RecyclerView recyclerView;


    RadioGroup radioGrouptype, radioGrouprel;
    EditText type, typerel, cust1, name1, mobile, tell1, adress, power, pgcode, id, x1, x2, url, date, time, un;
    RadioButton chek, chekrel;
String x4,x5;

    ImageView imgView;
    Button btnPhoto, delete, update, read, create, one, two;
    protected static final int CameraRequest = 1;


    int chekeedrel;
    int chekeedtype;


    public static final int RequestPermissionCode = 1;
    Intent intent1;
    Location location;
    LocationManager locationManager;
    boolean GpsStatus = false;
    Criteria criteria;
    String Holder;
    Context context;


    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d ");
    PersianDateFormat pdformater2 = new PersianDateFormat("H:i:s ");
    PersianDateFormat pdformater3 = new PersianDateFormat("y-m-d-H-i-s ");


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + "Camera", tell1.getText().toString()+ ".jpg");
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        FileUtils fileUtils = FileUtils.getMetaData(MainActivity.this, contentUri);
        fileUtils.mimeType = "image/jpeg";
        FileUtils.resizeIfMedia(getApplicationContext(), fileUtils);
        selectedFiles.add(fileUtils);


        String url = "https://pgtab.info/home/upload";
        Log.d("url", url);

        MultiPartConnection multipart = new MultiPartConnection(url,
                new MultiPartConnection.FileUploadListener() {
                    @Override
                    public void onUpdateProgress(int percentage, int i) {
                        //Toast.makeText(MainActivity.this, percentage, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseListener(String response) {
                        Log.d("multiPartResp", response);
                        //Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onErrorListener(String Error) {

                    }
                });
        for (int i = 0; i < selectedFiles.size(); i++) {
            multipart.addFile("file", new File(selectedFiles.get(i).path));
        }
        multipart.addData("user_id", "");
        multipart.Execute();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceGenerator = new ServiceGenerator();
        cast();
      x4=(pdformater3.format(pdate));
        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        un.setText(sharedPreferences.getString("user", ""));




          final String x5=x4;

        ///کد دوربین
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                captureImage();
            }

        });


        ///کد دوربین



       // String hadi=getIntent().getExtras().getString("username");
     //   un.setText(getIntent().getExtras().getString("username1"));

        // SharedPreferences sharedPreferences = getSharedPreferences("loogin", Context.MODE_PRIVATE);
        //  sharedPreferences.getString("username", "");


//**
        //   requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  setContentView(R.layout.activity_api_retrofit);
        //**


////مختصات
        EnableRuntimePermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        Holder = locationManager.getBestProvider(criteria, false);
        context = getApplicationContext();
        CheckGpsStatus();


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckGpsStatus();
                if (GpsStatus == true) {
                    if (Holder != null) {
                        if (ActivityCompat.checkSelfPermission(
                                MainActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                &&
                                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        //   location = locationManager.getLastKnownLocation(Holder);
                        //   locationManager.requestLocationUpdates(Holder, 12000, 7, MainActivity.this);


                        //    double laaat=userLocation.getLatitude();
                        //     double loong=userLocation.getLongitude();
                        //   String aaaaaa= String.valueOf (laaat);
                        //   String aaaaa2= String.valueOf (loong);
                        // hadi=  currentLocation.getLatitude() + "," + currentLocation.getLongitude();
                        //  x1.setText(aaaaaa);
                        //    x2.setText(aaaaa2);


                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please Enable GPS First", Toast.LENGTH_LONG).show();
                }
            }
        });


////مختصات


        ////نوع مرکز
        radioGrouptype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                chekeedtype = radioGroup.getCheckedRadioButtonId();
                chek = findViewById(chekeedtype);
                type.setText(chek.getText());


            }
        });
        ////نوع مرکز


        ////نوع رابطه
        radioGrouprel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                chekeedrel = radioGrouprel.getCheckedRadioButtonId();
                chekrel = findViewById(chekeedrel);
                typerel.setText(chekrel.getText());
            }
        });
        ////نوع رابطه





        customerlist = new ArrayList<>();
        customAdapter = new customAdapter(customerlist, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
        read();


        read.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        create.setOnClickListener(this);
    }


    public void cast() {
        typerel = findViewById(R.id.typeRel1);
        type = findViewById(R.id.type);
        radioGrouptype = findViewById(R.id.radiotype);
        radioGrouprel = findViewById(R.id.radiorel);
        imgView = findViewById(R.id.imageView);
        btnPhoto = findViewById(R.id.camera);
        cust1 = findViewById(R.id.cust1);
        name1 = findViewById(R.id.name1);
        mobile = findViewById(R.id.mobile);
        tell1 = findViewById(R.id.tell1);
        adress = findViewById(R.id.address);
        power = findViewById(R.id.power);
        pgcode = findViewById(R.id.pgcode);
        id = findViewById(R.id.id);
        x1 = findViewById(R.id.x1);
        x2 = findViewById(R.id.x2);
        url = findViewById(R.id.url);
        delete = findViewById(R.id.delete);
        read = findViewById(R.id.read);
        update = findViewById(R.id.update);
        create = findViewById(R.id.create);
        recyclerView = findViewById(R.id.recyclerView);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        un = findViewById(R.id.un);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);
    }

    //insert//
    private void insert() {
        if (!checkidempty()) {

            final String tttype = type.getText().toString().trim();
            final String ccust1 = cust1.getText().toString().trim();
            final String ppower = power.getText().toString().trim();
            final String nname1 = name1.getText().toString().trim();
            final String ttell1 = tell1.getText().toString().trim();
            final String mmobile = mobile.getText().toString().trim();
            final String aadress = adress.getText().toString().trim();
            final String ttyperel = typerel.getText().toString().trim();
            final String ppgcode = pgcode.getText().toString().trim();
            final String uurl ="https://pgtab.info/custpic/"+ tell1.getText().toString() + ".jpg";
            final String xx1 = x1.getText().toString().trim();
            final String xx2 = x2.getText().toString().trim();
            final String ddate = date.getText().toString().trim();
            final String ttime = time.getText().toString().trim();
            final String uun = un.getText().toString().trim();


            Call<customer> call = serviceGenerator.getService().insert(new customer(tttype, ccust1, ppower, nname1, ttell1, mmobile, aadress, ttyperel, ppgcode, uurl, xx1, xx2, ddate, ttime, uun));
            call.enqueue(new Callback<customer>() {
                @Override
                public void onResponse(Call<customer> call, Response<customer> response) {

                    customer Customer = response.body();
                    customerlist.add(new customer(Customer.getType(), Customer.getCust(), Customer.getPower(), Customer.getName(), Customer.getTell1(), Customer.getMobile(), Customer.getAddress(), Customer.getTypeRel(), Customer.getPgcode(), Customer.getPicURL(), Customer.getX1(), Customer.getX2(), Customer.getDate(), Customer.getTime(), Customer.getUn()));
               customAdapter.notifyItemInserted(customerlist.size() - 1);
                    cleartext();

                }

                @Override
                public void onFailure(Call<customer> call, Throwable t) {
                    Log.d("اطلاعات اشتباه هست", t.toString());
                }
            });

        }
    }
    //insert//

    //read//
    private void read() {

        customerlist.clear();
        customAdapter.notifyDataSetChanged();


        Call<List<customer>> call = serviceGenerator.getService().getcustomer();
        call.enqueue(new Callback<List<customer>>() {
            @Override
            public void onResponse(Call<List<customer>> call, Response<List<customer>> response) {

                for (customer Customer : response.body()) {
                    Log.d("customer", Customer.getType() + Customer.getCust() + Customer.getPower() + Customer.getName() + Customer.getTell1() + Customer.getMobile() + Customer.getAddress() + Customer.getTypeRel() + Customer.getPgcode() + Customer.getPicURL() + Customer.getX1() + Customer.getX2() + Customer.getDate() + Customer.getTime() + Customer.getUn());
                    customerlist.add(Customer);
                    customAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<List<customer>> call, Throwable t) {
                Log.d("اطلاعات موجود نیست", t.toString());
            }
        });

    }
    //read//

    //update//
    private void update() {
        if (!checkidempty() && !checkidempty()) {
            final int iid = Integer.parseInt(id.getText().toString().trim());
            final String tttype = type.getText().toString().trim();
            final String ccust1 = cust1.getText().toString().trim();
            final String ppower = power.getText().toString().trim();
            final String nname1 = name1.getText().toString().trim();
            final String ttell1 = tell1.getText().toString().trim();
            final String mmobile = mobile.getText().toString().trim();
            final String aadress = adress.getText().toString().trim();
            final String ttyperel = typerel.getText().toString().trim();
            final String ppgcode = pgcode.getText().toString().trim();
            final String uurl = url.getText().toString().trim();
            final String xx1 = x1.getText().toString().trim();
            final String xx2 = x2.getText().toString().trim();
            final String ddate = date.getText().toString().trim();
            final String ttime = time.getText().toString().trim();
            final String uun = un.getText().toString().trim();

            final int pos = getidPos(iid);

            Call<ResponseBody> call = serviceGenerator.getService().update(new customer(iid, tttype, ccust1, ppower, nname1, ttell1, mmobile, aadress, ttyperel, ppgcode, uurl, xx1, xx2, ddate, ttime, uun));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    String responseresult = null;
                    try {
                        responseresult = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (responseresult.equals("تغیرات لحاظ شد")) {
                        customerlist.set(pos, new customer(iid, tttype, ccust1, ppower, nname1, ttell1, mmobile, aadress, ttyperel, ppgcode, uurl, xx1, xx2, ddate, ttime, uun));
                        customAdapter.notifyItemChanged(pos);
                        cleartext();
                    } else {
                        id.setError("id not found");

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("اطلاعات اشتباه هست", t.toString());
                }
            });


        }
    }
    //update//

    //delete//
    private void delete() {
        if (!checkidempty()) {


            final int idd = Integer.parseInt(id.getText().toString().trim());
            final int pos = getidPos(idd);


            final String tttype = type.getText().toString().trim();
            final String ccust1 = cust1.getText().toString().trim();
            final String ppower = power.getText().toString().trim();
            final String nname1 = name1.getText().toString().trim();
            final String ttell1 = tell1.getText().toString().trim();
            final String mmobile = mobile.getText().toString().trim();
            final String aadress = adress.getText().toString().trim();
            final String ttyperel = typerel.getText().toString().trim();
            final String ppgcode = pgcode.getText().toString().trim();
            final String uurl = url.getText().toString().trim();
            final String xx1 = x1.getText().toString().trim();
            final String xx2 = x2.getText().toString().trim();
            final String ddate = date.getText().toString().trim();
            final String ttime = time.getText().toString().trim();
            final String uun = un.getText().toString().trim();


            Call<ResponseBody> call = serviceGenerator.getService().delete(idd);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        String responseresult = response.body().string();
                        if (responseresult.equals("با موفقیت حذف شد")) {
                            customerlist.remove(pos);
                            customAdapter.notifyDataSetChanged();
                            cleartext();
                        } else {
                            id.setError("حذف نشد.");
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
    //delete//








    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create:
                insert();
                break;

            case R.id.update:
                update();
                break;

            case R.id.delete:
                delete();
                break;

            case R.id.read:
                read();
                break;
        }
    }

    @Override
    public void onCustomListitemClick(int position) {
        customer customer = customerlist.get(position);
        id.setText(String.valueOf(customer.getId()));
        type.setText(String.valueOf(customer.getType()));
        cust1.setText(String.valueOf(customer.getCust()));
        name1.setText(String.valueOf(customer.getName()));
        mobile.setText(String.valueOf(customer.getMobile()));
        tell1.setText(String.valueOf(customer.getTell1()));
        adress.setText(String.valueOf(customer.getAddress()));
        typerel.setText(String.valueOf(customer.getTypeRel()));
        power.setText(String.valueOf(customer.getPower()));
        pgcode.setText(String.valueOf(customer.getPgcode()));
        x1.setText(String.valueOf(customer.getX1()));
        x2.setText(String.valueOf(customer.getX2()));
        url.setText(String.valueOf(customer.getPicURL()));
        date.setText(String.valueOf(customer.getDate()));
        time.setText(String.valueOf(customer.getTime()));
        un.setText(String.valueOf(customer.getUn()));
    }


    private boolean checkidempty() {
        String iid = String.valueOf(id.getText().toString());
        if (iid.isEmpty()) {
            id.setText("empty");
            return true;
        } else return false;

    }

    private boolean checkInsertEmpty() {
        String username = cust1.getText().toString().trim();
        String password = name1.getText().toString().trim();
        boolean flag = false;
        if (username.isEmpty()) {
            cust1.setError("نام کاربری خالی است");
            flag = true;
        }
        if (password.isEmpty()) {
            name1.setError("رمز خالی است");
            flag = true;
        }
        return flag;
    }


    private int getidPos(int id) {
        int pos = -1;
        for (int i = 0; i < customerlist.size(); i++) {
            if (customerlist.get(i).getId() == id) {
                pos = i;
                break;
            }
        }
        return pos;
    }


    private void cleartext() {
        id.setText("");
        type.setText("");
        cust1.setText("");
        name1.setText("");
        mobile.setText("");
        tell1.setText("");
        adress.setText("");
        typerel.setText("");
        power.setText("");
        pgcode.setText("");
        x1.setText("");
        x2.setText("");
        url.setText("");
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        //   x1.setText("" + location.getLongitude());
        //   x2.setText("" + location.getLatitude());
        //  date.setText(location.getLatitude()+","+location.getLongitude());

        //  date.setText(pdformater1.format(pdate));
        //  time.setText(pdformater2.format(pdate));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    public void CheckGpsStatus() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }


    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(MainActivity.this, "ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();
                }
                break;
        }


    }




    @Override
    protected void onStart() {
        super.onStart();
        // everything related to ui is initialized here
        initLayoutReferences();

        initLocation();
        startReceivingLocationUpdates();

    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    private void initLayoutReferences() {
        // Initializing views
        initViews();
        // Initializing mapView element
        initMap();

    }

    // We use findViewByID for every element in our layout file here
    private void initViews() {
        map = findViewById(R.id.map);
        addressTitle = findViewById(R.id.addressTitle);
        progressBar = findViewById(R.id.progressBar);
        latt = findViewById(R.id.latt);
    }

    // Initializing map
    private void initMap() {


        // Setting map focal position to a fixed position and setting camera zoom
        map.moveCamera(new LatLng(38.11535520699953, 46.25988337788993), 0);
        map.setZoom(15, 0);


        map.setOnCameraMoveFinishedListener(i -> {
            runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
            getReverseApi(map.getCameraTargetPosition());
        });
    }


    private void getReverseApi(LatLng currentLocation) {
        getDataService.getReverse(currentLocation.getLatitude(), currentLocation.getLongitude()).enqueue(new Callback<NeshanAddress>() {
            @Override
            public void onResponse(Call<NeshanAddress> call, Response<NeshanAddress> response) {
                String address = response.body().getAddress();
                if (address != null && !address.isEmpty()) {
                    addressTitle.setText(address);
                    String hadi, hadi1, hadi2;
                    hadi = currentLocation.getLatitude() + "," + currentLocation.getLongitude();
                    hadi1 = currentLocation.getLatitude() + "";
                    hadi2 = currentLocation.getLongitude() + "";
                    latt.setText(hadi);
                    x1.setText(hadi1);
                    x2.setText(hadi2);
                    adress.setText(addressTitle.getText());
                    url.setText(latt.getText());
                    pdformater1.format(pdate);
                    date.setText(pdformater1.format(pdate));
                    time.setText(pdformater2.format(pdate));


//******


                } else {
                    addressTitle.setText("معبر بی‌نام");
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<NeshanAddress> call, Throwable t) {
                addressTitle.setText("معبر بی‌نام");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


    //   @Override
    protected void onActivityResult1(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    // Initializing layout references (views, map and map events)


    // We use findViewByID for every element in our layout file here


    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                onLocationChange();
            }
        };

        mRequestingLocationUpdates = false;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                        onLocationChange();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MainActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        onLocationChange();
                    }
                });
    }

    public void stopLocationUpdates() {
        // Removing location updates
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void startReceivingLocationUpdates() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void onLocationChange() {
        if (userLocation != null) {
            addUserMarker(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));
        }
    }

    // This method gets a LatLng as input and adds a marker on that position
    private void addUserMarker(LatLng loc) {
        //remove existing marker from map
        if (marker != null) {
            map.removeMarker(marker);
        }
        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleBuilder markStCr = new MarkerStyleBuilder();
        markStCr.setSize(30f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating user marker
        marker = new Marker(loc, markSt);

        // Adding user marker to map!
        map.addMarker(marker);
    }

    public void focusOnUserLocation(View view) {
        if (userLocation != null) {
            map.moveCamera(
                    new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
            map.setZoom(20, 0.15f);
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, menu.class));
    }

    public void captureImage() {

        isFileChosen = false;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(), "Camera");
        if (!storageDir.exists()) storageDir.mkdir();
        File image = new File(storageDir, tell1.getText().toString()+ ".jpg");
        Uri photoURI = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", image);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent,1);
       
       // imgView.getResources();

    }


}







