package com.example.point;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carto.styles.MarkerStyle;
import com.carto.styles.MarkerStyleBuilder;
import com.carto.utils.BitmapUtils;
import com.example.point.API.ServiceGenerator;
import com.example.point.DataModel.bbazargardi;
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

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.subjects.PublishSubject;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class bazargardi extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


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
    //*****//
    private static final String TAG = UserLocation.class.getName();


    final int REQUEST_CODE = 123;


    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

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

    //ui elements
    private TextView baddressTitle, blatt;
    private ProgressBar progressBar;


    Boolean isFileChosen = false;
    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
    PersianDateFormat pdformater2 = new PersianDateFormat("H:i:s");
    PersianDateFormat pdformater3 = new PersianDateFormat("YmdHis");

    ServiceGenerator serviceGenerator;
    ArrayList<bbazargardi> bazargardilist;
    private ArrayList<FileUtils> selectedFiles2 = new ArrayList<>();

    String[] shir = {"انتخاب نشده", "پگاه", "کاله", "سوتچی لر", "میهن", "صباح", "هراز", "توریس", "کوهساران", "حیدربابا", "رامک"};
    String[] btype = {"انتخاب نشده","سوپرمارکت", "هایپرمارکت", "رستوران", "لبنیات سنتی", "دکه", ""};
    String[] brel = {"انتخاب نشده", "ویزیتوری", "گرم", "دارایی", "دامنه میشاب", "عدم همکاری", ""};
    String[] bgreade = {"انتخاب نشده", "خیلی قوی", "قوی", "معمولی", "ضعیف", "خیلی ضعیف", ""};
    String[] bvisito = {"انتخاب نشده", "پيشقدم", "علي صمدي فرد", "ياسر داداشي", "حامد غفاري", "کياني", "يوسف محمدپور", "عمران پور", "ناظري", "امير حسين اميني", "محمدحسين نيا", "دهقاني", "گروه آقای احیایی", "سيامک محمدپور", "بهنام سرخابي", "رضا پارچه بافيه", "جديري", "محمد نامور", "جعفر حيدريان", "پرويزقهاري", "رضا جنگجو", "بهنام پاک رفته", "گروه آقای شیرینی", "ليلا محمدي", "رقيه غفاري", "دوست کام", "سلطاني", "رضايي", "مينا اصغري", "کريمي", "سحر حسيني", "گروه خانم برزم"};
    String[] brannad = {"انتخاب نشده", "نام موزع", "وهاب اللهويردي زاده(تعاوني جهاد)", "وحيد محمدي مغانلو", "وحيد رسولي اقدم", "ميثم ميرزايي قديم-تعاوني جهاد", "مهدي پوري", "مهدي پور نقي-هتلها", "محمد آقايان (ره آورد مبين)", "محرم چمني - آذرپخش", "فردين صادق", "علي رسولي2(تعاوني جهاد)", "علي چمني- آذرپخش", "صياد وظايف منور", "سيروس جمال پور", "سعيد خدايي", "رهاورد مبين(مهدي محمدي نژاد)", "رهاورد مبين(عليرضا رسولي اقدم)", "رهاورد مبين(عبداله آقايان)", "رهاورد مبين(صفر محمدزاده)", "رهاورد مبين (يونس شوري )", "رهاورد مبين (عليرضا جعفري )", "رهاورد مبين (رحمان شکوهي کيا )", "جهاد (سياوش هاشمي )", "جواد قدسي (تعاوني جهاد)", "جابر زاهدي (تعاوني جهاد )", "تعاوني جهاد مقصود خدايي", "تعاوني جهاد محمدتقي زارع", "تعاوني جهاد شكر الله كريم پور", "تعاوني جهاد (علي اصغر علي قلي زاده )", "تعاوني جهاد (داود امير سفيدان )", "تعاوني جهاد (حسين زارع علويق )", "تعاوني جهاد (حسن فتحي مددلو )", "بهزاد پناهي (ره آورد مبين)", "امير شامخي", "التفات دنيوي", "اذر پخش توزيع گستر رضا سلطاني", "اذر پخش توزيع گستر (برات فداکار", "اذر پخش  - تيمور جعفري", "(تعاوني جهاد)يونس فتحي مددلو", "(تعاوني جهاد)مهدي نصير زاده", "(اذر پخش توزيع گستر)غلامرضا قنبرزاده", "(اذر پخش توزيع گستر)داوود جعفري"};
    String[] abmive = {"انتخاب نشده", "سن ایچ","سان استار","تکدانه","سانی نس","پگاه","سهند","احلام","","",""};
    String[] bsoper = {"شیرینی", "احیایی", "برزم",};

    Spinner spiner1, spiner2, spiner3, spiner4, spiner5, spiner6, spiner7, spiner8, spiner9, spiner10, spiner11, spiner12, spiner13, spiner14, spiner15, spiner16, spiner17, spiner18, spiner19, spiner20, spiner21, spiner22, spiner23, spiner24, spiner25, spiner26, spiner27, spiner28, spiner29, spiner30,
            btypee, breel, bgrade, bvisitor, brannade, bsoperviser, brezayatv, brezayatto;
    TextView textview;
    EditText bbun, bbname, bbsarparast, bbtell, bbcode, bbdarkhast, bbtoosiye, bbnazar, bbx1, bbx2, bbadress, bbtime, bbdate, bbyakhurl, bid;
    Button bcreate, bcamera;
    private TextView addressTitle, latt;

    final String cap=pdformater3.format(pdate);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + "Camera",cap.toString()+".jpg");
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        FileUtils fileUtils = FileUtils.getMetaData(bazargardi.this, contentUri);
        fileUtils.mimeType = "image/jpeg";
        FileUtils.resizeIfMedia(getApplicationContext(), fileUtils);
        selectedFiles2.add(fileUtils);


        String url = "https://pgtab.info/home/upload_yakh";
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
        for (int i = 0; i < selectedFiles2.size(); i++) {
            multipart.addFile("file", new File(selectedFiles2.get(i).path));
        }
        multipart.addData("user_id", "");
        multipart.Execute();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazargardi);




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


        serviceGenerator = new ServiceGenerator();


        PersianDate pdate = new PersianDate();
        bazargardilist = new ArrayList<>();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shir);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, btype);
        ArrayAdapter<String> adapterrel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brel);
        ArrayAdapter<String> adaptergrade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bgreade);
        ArrayAdapter<String> adaptevisitor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bvisito);
        ArrayAdapter<String> adapterannade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brannad);
        ArrayAdapter<String> adaptersarparast = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bsoper);
        ArrayAdapter<String> adapterrezayatv = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bgreade);
        ArrayAdapter<String> adapterrezayatto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bgreade);
        ArrayAdapter<String> adapterabmive = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, abmive);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterrel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptergrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptevisitor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterannade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptersarparast.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterrezayatv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterrezayatto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spiner1 = findViewById(R.id.spiner1);
        spiner2 = findViewById(R.id.spiner2);
        spiner3 = findViewById(R.id.spiner3);
        spiner4 = findViewById(R.id.spiner4);
        spiner5 = findViewById(R.id.spiner5);
        spiner6 = findViewById(R.id.spiner6);
        spiner7 = findViewById(R.id.spiner7);
        spiner8 = findViewById(R.id.spiner8);
        spiner9 = findViewById(R.id.spiner9);
        spiner10 = findViewById(R.id.spiner10);
        spiner11 = findViewById(R.id.spiner11);
        spiner12 = findViewById(R.id.spiner12);
        spiner13 = findViewById(R.id.spiner13);
        spiner14 = findViewById(R.id.spiner14);
        spiner15 = findViewById(R.id.spiner15);
        spiner16 = findViewById(R.id.spiner16);
        spiner17 = findViewById(R.id.spiner17);
        spiner18 = findViewById(R.id.spiner18);
        spiner19 = findViewById(R.id.spiner19);
        spiner20 = findViewById(R.id.spiner20);
        spiner21 = findViewById(R.id.spiner21);
        spiner22 = findViewById(R.id.spiner22);
        spiner23 = findViewById(R.id.spiner23);
        spiner24 = findViewById(R.id.spiner24);
        spiner25 = findViewById(R.id.spiner25);
        spiner26 = findViewById(R.id.spiner26);
        spiner27 = findViewById(R.id.spiner27);
        spiner28 = findViewById(R.id.spiner28);
        spiner29 = findViewById(R.id.spiner29);
        spiner30 = findViewById(R.id.spiner30);
        btypee = findViewById(R.id.bbtypee);
        breel = findViewById(R.id.bbrel);
        bgrade = findViewById(R.id.bbgrade);
        brannade = findViewById(R.id.bbrannade);
        bvisitor = findViewById(R.id.bbvisitor);
        brannade = findViewById(R.id.bbrannade);
        bsoperviser = findViewById(R.id.bbsoper);
        brezayatv = findViewById(R.id.bbrezayatv);
        brezayatto = findViewById(R.id.bbrezayattoo);
        bid = findViewById(R.id.bid);
        blatt = findViewById(R.id.blatt);
        baddressTitle = findViewById(R.id.baddressTitle);
        bcreate = findViewById(R.id.bcreate);
        bcamera = findViewById(R.id.bcamera);

        bbun = findViewById(R.id.bbun);
        bbname = findViewById(R.id.bbname);
        bbsarparast = findViewById(R.id.bbsarparast);
        bbtell = findViewById(R.id.bbtell);
        bbcode = findViewById(R.id.bbcode);
        bbdarkhast = findViewById(R.id.bbdarkhast);
        bbtoosiye = findViewById(R.id.bbtoosiye);
        bbnazar = findViewById(R.id.bbnazar);
        bbx1 = findViewById(R.id.bbx1);
        bbx2 = findViewById(R.id.bbx2);
        bbadress = findViewById(R.id.bbaddress);
        bbtime = findViewById(R.id.bbtime);
        bbdate = findViewById(R.id.bbdate);
        bbyakhurl = findViewById(R.id.bbyakhurl);


        spiner1.setAdapter(adapter);
        spiner1.setOnItemSelectedListener(this);
        spiner2.setAdapter(adapter);
        spiner2.setOnItemSelectedListener(this);
        spiner3.setAdapter(adapter);
        spiner3.setOnItemSelectedListener(this);
        spiner4.setAdapter(adapter);
        spiner4.setOnItemSelectedListener(this);
        spiner5.setAdapter(adapter);
        spiner5.setOnItemSelectedListener(this);
        spiner6.setAdapter(adapter);
        spiner6.setOnItemSelectedListener(this);
        spiner7.setAdapter(adapter);
        spiner7.setOnItemSelectedListener(this);
        spiner8.setAdapter(adapter);
        spiner8.setOnItemSelectedListener(this);
        spiner9.setAdapter(adapter);
        spiner9.setOnItemSelectedListener(this);
        spiner10.setAdapter(adapter);
        spiner10.setOnItemSelectedListener(this);
        spiner11.setAdapter(adapter);
        spiner11.setOnItemSelectedListener(this);
        spiner12.setAdapter(adapter);
        spiner12.setOnItemSelectedListener(this);
        spiner13.setAdapter(adapter);
        spiner13.setOnItemSelectedListener(this);
        spiner14.setAdapter(adapter);
        spiner14.setOnItemSelectedListener(this);
        spiner15.setAdapter(adapter);
        spiner15.setOnItemSelectedListener(this);
        spiner16.setAdapter(adapter);
        spiner16.setOnItemSelectedListener(this);
        spiner17.setAdapter(adapter);
        spiner17.setOnItemSelectedListener(this);
        spiner18.setAdapter(adapter);
        spiner18.setOnItemSelectedListener(this);
        spiner19.setAdapter(adapter);
        spiner19.setOnItemSelectedListener(this);
        spiner20.setAdapter(adapter);
        spiner20.setOnItemSelectedListener(this);
        spiner21.setAdapter(adapter);
        spiner21.setOnItemSelectedListener(this);
        spiner22.setAdapter(adapter);
        spiner22.setOnItemSelectedListener(this);
        spiner23.setAdapter(adapter);
        spiner23.setOnItemSelectedListener(this);
        spiner24.setAdapter(adapter);
        spiner24.setOnItemSelectedListener(this);
        spiner25.setAdapter(adapter);
        spiner25.setOnItemSelectedListener(this);
        spiner26.setAdapter(adapter);
        spiner26.setOnItemSelectedListener(this);
        spiner27.setAdapter(adapter);
        spiner27.setOnItemSelectedListener(this);
        spiner28.setAdapter(adapterabmive);
        spiner28.setOnItemSelectedListener(this);
        spiner29.setAdapter(adapterabmive);
        spiner29.setOnItemSelectedListener(this);
        spiner30.setAdapter(adapterabmive);
        spiner30.setOnItemSelectedListener(this);
        btypee.setAdapter(adapter1);
        btypee.setOnItemSelectedListener(this);
        breel.setAdapter(adapterrel);
        breel.setOnItemSelectedListener(this);
        bgrade.setAdapter(adaptergrade);
        bgrade.setOnItemSelectedListener(this);
        bvisitor.setAdapter(adaptevisitor);
        bgrade.setOnItemSelectedListener(this);
        brannade.setAdapter(adapterannade);
        brannade.setOnItemSelectedListener(this);
        bsoperviser.setAdapter(adaptersarparast);
        bsoperviser.setOnItemSelectedListener(this);
        brezayatv.setAdapter(adapterrezayatv);
        brezayatv.setOnItemSelectedListener(this);
        brezayatto.setAdapter(adapterrezayatto);
        brezayatto.setOnItemSelectedListener(this);
//        bbun.setText(getIntent().getExtras().getString("username2"));
        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);

        bbdate.setText(pdformater1.format(pdate));
        bbtime.setText(pdformater2.format(pdate));




        bcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bbsh1 = spiner1.getSelectedItem().toString();
                String bbsh2 = spiner2.getSelectedItem().toString();
                String bbsh3 = spiner3.getSelectedItem().toString();
                String bbkh1 = spiner4.getSelectedItem().toString();
                String bbkh2 = spiner5.getSelectedItem().toString();
                String bbkh3 = spiner6.getSelectedItem().toString();
                String bbshs1 = spiner7.getSelectedItem().toString();
                String bbshs2 = spiner8.getSelectedItem().toString();
                String bbshs3 = spiner9.getSelectedItem().toString();
                String bbk1 = spiner10.getSelectedItem().toString();
                String bbk2 = spiner11.getSelectedItem().toString();
                String bbk3 = spiner12.getSelectedItem().toString();
                String bbshd1 = spiner13.getSelectedItem().toString();
                String bbshd2 = spiner14.getSelectedItem().toString();
                String bbshd3 = spiner15.getSelectedItem().toString();
                String bbma1 = spiner16.getSelectedItem().toString();
                String bbma2 = spiner17.getSelectedItem().toString();
                String bbma3 = spiner18.getSelectedItem().toString();
                String bbp1 = spiner19.getSelectedItem().toString();
                String bbp2 = spiner20.getSelectedItem().toString();
                String bbp3 = spiner21.getSelectedItem().toString();
                String bbdb1 = spiner22.getSelectedItem().toString();
                String bbdb2 = spiner23.getSelectedItem().toString();
                String bbdb3 = spiner24.getSelectedItem().toString();
                String bbdn1 = spiner25.getSelectedItem().toString();
                String bbdn2 = spiner26.getSelectedItem().toString();
                String bbdn3 = spiner27.getSelectedItem().toString();
                String bbab1 = spiner28.getSelectedItem().toString();
                String bbab2 = spiner29.getSelectedItem().toString();
                String bbab3 = spiner30.getSelectedItem().toString();
                String bbtypee = btypee.getSelectedItem().toString();
                String bbreel = breel.getSelectedItem().toString();
                String bbgrade = bgrade.getSelectedItem().toString();
                String bbvisitor = bvisitor.getSelectedItem().toString();
                String bbrannade = brannade.getSelectedItem().toString();
                String bbbsoper = bsoperviser.getSelectedItem().toString();
                String bbrezayatv = brezayatv.getSelectedItem().toString();
                String bbrezayatto = brezayatto.getSelectedItem().toString();


                final String fbbtypee = bbtypee.toString().trim();
                final String fbbreel = bbreel.toString().trim();
                final String fbbgrade = bbgrade.toString().trim();
                final String fbbvisitor = bbvisitor.toString().trim();
                final String fbbrannade = bbrannade.toString().trim();
                final String fbbrezayatv = bbrezayatv.toString().trim();
                final String fbbrezayatto = bbrezayatto.toString().trim();
                final String fbbsh1 = bbsh1.toString().trim();
                final String fbbsh2 = bbsh2.toString().trim();
                final String fbbsh3 = bbsh3.toString().trim();
                final String fbbkh1 = bbkh1.toString().trim();
                final String fbbkh2 = bbkh2.toString().trim();
                final String fbbkh3 = bbkh3.toString().trim();
                final String fbbshs1 = bbshs1.toString().trim();
                final String fbbshs2 = bbshs2.toString().trim();
                final String fbbshs3 = bbshs3.toString().trim();
                final String fbbk1 = bbk1.toString().trim();
                final String fbbk2 = bbk2.toString().trim();
                final String fbbk3 = bbk3.toString().trim();
                final String fbbshd1 = bbshd1.toString().trim();
                final String fbbshd2 = bbshd2.toString().trim();
                final String fbbshd3 = bbshd3.toString().trim();
                final String fbbma1 = bbma1.toString().trim();
                final String fbbma2 = bbma2.toString().trim();
                final String fbbma3 = bbma3.toString().trim();
                final String fbbp1 = bbp1.toString().trim();
                final String fbbp2 = bbp2.toString().trim();
                final String fbbp3 = bbp3.toString().trim();
                final String fbbdb1 = bbdb1.toString().trim();
                final String fbbdb2 = bbdb2.toString().trim();
                final String fbbdb3 = bbdb3.toString().trim();
                final String fbbdn1 = bbdn1.toString().trim();
                final String fbbdn2 = bbdn2.toString().trim();
                final String fbbdn3 = bbdn3.toString().trim();
                final String fbbab1 = bbab1.toString().trim();
                final String fbbab2 = bbab2.toString().trim();
                final String fbbab3 = bbab3.toString().trim();
                final String fbbbsoper = bbbsoper.toString().trim();
                final String fbbun = bbun.getText().toString().trim();
                final String fbbname = bbname.getText().toString().trim();
                final String fbbsarparast = bbsarparast.getText().toString().trim();
                final String fbbtell = bbtell.getText().toString().trim();
                final String fbbcode = bbcode.getText().toString().trim();
                final String fbbdarkhast = bbdarkhast.getText().toString().trim();
                final String fbbtoosiye = bbtoosiye.getText().toString().trim();
                final String fbbnazar = bbnazar.getText().toString().trim();
                final String fbbx1 = bbx1.getText().toString().trim();
                final String fbbx2 = bbx2.getText().toString().trim();
                final String fbbadress = bbadress.getText().toString().trim();
                final String fbbtime = bbtime.getText().toString().trim();
                final String fbbdate = bbdate.getText().toString().trim();
                final String fbbyakhurl ="https://pgtab.info/yakhpic/"+ cap.toString().trim()+".jpg";

                Call<bbazargardi> call1 = serviceGenerator.getService().insert_bazargardi(new bbazargardi(fbbtypee, fbbname, fbbsarparast, fbbtell, fbbreel, fbbgrade, fbbcode, fbbvisitor, fbbrannade, fbbbsoper, fbbrezayatv, fbbrezayatto, fbbdarkhast, fbbtoosiye, fbbnazar,
                        fbbsh1, fbbsh2, fbbsh3, fbbkh1, fbbkh2, fbbkh3, fbbshs1, fbbshs2, fbbshs3, fbbk1, fbbk2, fbbk3,
                        fbbshd1, fbbshd2, fbbshd3, fbbma1, fbbma2, fbbma3,
                        fbbp1, fbbp2, fbbp3, fbbdb1, fbbdb2, fbbdb3,
                        fbbdn1, fbbdn2, fbbdn3, fbbab1, fbbab2, fbbab3,
                        fbbx1, fbbx2, fbbadress, fbbtime, fbbdate, fbbyakhurl, fbbun));

                call1.enqueue(new Callback<bbazargardi>() {


                    @Override
                    public void onResponse(Call<bbazargardi> call1, Response<bbazargardi> response) {
                        bbazargardi Bazargardii = response.body();
                        bazargardilist.add(new bbazargardi(Bazargardii.getBtypee(), Bazargardii.getBname(), Bazargardii.getBsarparast(), Bazargardii.getBtell(), Bazargardii.getBrel(),
                                Bazargardii.getBgrade(), Bazargardii.getBcode(), Bazargardii.getBvisitor(), Bazargardii.getBrannade(), Bazargardii.getBsoper(), Bazargardii.getBrezayatv(),
                                Bazargardii.getBrezayattoo(), Bazargardii.getBdarkhast(), Bazargardii.getBtoosiye(), Bazargardii.getBnazar(),
                                Bazargardii.getBsh1(), Bazargardii.getBsh2(), Bazargardii.getBsh3(), Bazargardii.getBkh1(), Bazargardii.getBkh2(), Bazargardii.getBkh3(),
                                Bazargardii.getBshs1(), Bazargardii.getBshs2(), Bazargardii.getBshs3(), Bazargardii.getBk1(), Bazargardii.getBk2(), Bazargardii.getBk3(),
                                Bazargardii.getBshd1(), Bazargardii.getBshd2(), Bazargardii.getBshd3(), Bazargardii.getBma1(), Bazargardii.getBma2(), Bazargardii.getBma3(),
                                Bazargardii.getBp1(), Bazargardii.getBp2(), Bazargardii.getBp3(), Bazargardii.getBdb1(), Bazargardii.getBdb2(), Bazargardii.getBdb3(),
                                Bazargardii.getBdn1(), Bazargardii.getBdn2(), Bazargardii.getBdn3(), Bazargardii.getBab1(), Bazargardii.getBab2(), Bazargardii.getBab3(),
                                Bazargardii.getBx1(), Bazargardii.getBx2(), Bazargardii.getBaddress(), Bazargardii.getBtime(), Bazargardii.getBdate(), Bazargardii.getByakhurl(), Bazargardii.getBun()));
                        //    customAdapter.notifyItemInserted(customerlist.size() - 1);
                        bcleartext();

                    }

                    @Override
                    public void onFailure(Call<bbazargardi> call1, Throwable t) {
                        Log.d("اطلاعات اشتباه هست", t.toString());
                    }
                });

            }
        });
        bcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bcaptureImage();

            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        bbun.setText(sharedPreferences.getString("user", ""));

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Toast.makeText(getApplicationContext(), "Selected User: " + shir[position], Toast.LENGTH_SHORT).show();
        // textview.setText(spiner1.getSelectedItem().toString());

        String bbsh1 = spiner1.getSelectedItem().toString();
        String bbsh2 = spiner2.getSelectedItem().toString();
        String sbbsh3 = spiner3.getSelectedItem().toString();
        String bbkh1 = spiner4.getSelectedItem().toString();
        String bbkh2 = spiner5.getSelectedItem().toString();
        String bbkh3 = spiner6.getSelectedItem().toString();
        String bbshs1 = spiner7.getSelectedItem().toString();
        String bbshs2 = spiner8.getSelectedItem().toString();
        String bbshs3 = spiner9.getSelectedItem().toString();
        String bbk1 = spiner10.getSelectedItem().toString();
        String bbk2 = spiner11.getSelectedItem().toString();
        String bbk3 = spiner12.getSelectedItem().toString();
        String bbshd1 = spiner13.getSelectedItem().toString();
        String bbshd2 = spiner14.getSelectedItem().toString();
        String bbshd3 = spiner15.getSelectedItem().toString();
        String bbma1 = spiner16.getSelectedItem().toString();
        String bbma2 = spiner17.getSelectedItem().toString();
        String bbma3 = spiner18.getSelectedItem().toString();
        String bbp1 = spiner19.getSelectedItem().toString();
        String bbp2 = spiner20.getSelectedItem().toString();
        String bbp3 = spiner21.getSelectedItem().toString();
        String bbdb1 = spiner22.getSelectedItem().toString();
        String bbdb2 = spiner23.getSelectedItem().toString();
        String bbdb3 = spiner24.getSelectedItem().toString();
        String bbdn1 = spiner25.getSelectedItem().toString();
        String bbdn2 = spiner26.getSelectedItem().toString();
        String bbdn3 = spiner27.getSelectedItem().toString();
        String bbab1 = spiner28.getSelectedItem().toString();
        String bbab2 = spiner29.getSelectedItem().toString();
        String bbab3 = spiner30.getSelectedItem().toString();


        String bbtypee = btypee.getSelectedItem().toString();
        String bbreel = breel.getSelectedItem().toString();
        String bbgrade = bgrade.getSelectedItem().toString();
        String bbvisitor = bvisitor.getSelectedItem().toString();
        String bbrannade = brannade.getSelectedItem().toString();
        String bbsoper = bsoperviser.getSelectedItem().toString();
        String bbrezayatv = brezayatv.getSelectedItem().toString();
        String bbrezayatto = brezayatto.getSelectedItem().toString();

    }


    private void cleartext1() {


        spiner1.setOnItemSelectedListener(this);

        spiner2.setOnItemSelectedListener(this);

        spiner3.setOnItemSelectedListener(this);

        spiner4.setOnItemSelectedListener(this);

        spiner5.setOnItemSelectedListener(this);

        spiner6.setOnItemSelectedListener(this);

        spiner7.setOnItemSelectedListener(this);

        spiner8.setOnItemSelectedListener(this);

        spiner9.setOnItemSelectedListener(this);

        spiner10.setOnItemSelectedListener(this);

        spiner11.setOnItemSelectedListener(this);

        spiner12.setOnItemSelectedListener(this);

        spiner13.setOnItemSelectedListener(this);

        spiner14.setOnItemSelectedListener(this);

        spiner15.setOnItemSelectedListener(this);

        spiner16.setOnItemSelectedListener(this);

        spiner17.setOnItemSelectedListener(this);

        spiner18.setOnItemSelectedListener(this);

        spiner19.setOnItemSelectedListener(this);

        spiner20.setOnItemSelectedListener(this);

        spiner21.setOnItemSelectedListener(this);

        spiner22.setOnItemSelectedListener(this);

        spiner23.setOnItemSelectedListener(this);

        spiner24.setOnItemSelectedListener(this);

        spiner25.setOnItemSelectedListener(this);

        spiner26.setOnItemSelectedListener(this);

        spiner27.setOnItemSelectedListener(this);

        spiner28.setOnItemSelectedListener(this);

        spiner29.setOnItemSelectedListener(this);

        spiner30.setOnItemSelectedListener(this);

        btypee.setOnItemSelectedListener(this);

        breel.setOnItemSelectedListener(this);

        bgrade.setOnItemSelectedListener(this);

        bgrade.setOnItemSelectedListener(this);

        brannade.setOnItemSelectedListener(this);

        bsoperviser.setOnItemSelectedListener(this);

        brezayatv.setOnItemSelectedListener(this);

        brezayatto.setOnItemSelectedListener(this);
        bbun.setText(getIntent().getExtras().getString("username2"));

    }

    public void onBackPressed() {
        startActivity(new Intent(bazargardi.this, menu.class));
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {


    }




    private void bcleartext() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shir);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, btype);
        ArrayAdapter<String> adapterrel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brel);
        ArrayAdapter<String> adaptergrade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bgreade);
        ArrayAdapter<String> adaptevisitor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bvisito);
        ArrayAdapter<String> adapterannade = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brannad);
        ArrayAdapter<String> adaptersarparast = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bsoper);
        ArrayAdapter<String> adapterrezayatv = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bgreade);
        ArrayAdapter<String> adapterrezayatto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bgreade);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterrel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptergrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptevisitor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterannade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptersarparast.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterrezayatv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterrezayatto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spiner1.setAdapter(adapter);
        spiner1.setOnItemSelectedListener(this);
        spiner2.setAdapter(adapter);
        spiner2.setOnItemSelectedListener(this);
        spiner3.setAdapter(adapter);
        spiner3.setOnItemSelectedListener(this);
        spiner4.setAdapter(adapter);
        spiner4.setOnItemSelectedListener(this);
        spiner5.setAdapter(adapter);
        spiner5.setOnItemSelectedListener(this);
        spiner6.setAdapter(adapter);
        spiner6.setOnItemSelectedListener(this);
        spiner7.setAdapter(adapter);
        spiner7.setOnItemSelectedListener(this);
        spiner8.setAdapter(adapter);
        spiner8.setOnItemSelectedListener(this);
        spiner9.setAdapter(adapter);
        spiner9.setOnItemSelectedListener(this);
        spiner10.setAdapter(adapter);
        spiner10.setOnItemSelectedListener(this);
        spiner11.setAdapter(adapter);
        spiner11.setOnItemSelectedListener(this);
        spiner12.setAdapter(adapter);
        spiner12.setOnItemSelectedListener(this);
        spiner13.setAdapter(adapter);
        spiner13.setOnItemSelectedListener(this);
        spiner14.setAdapter(adapter);
        spiner14.setOnItemSelectedListener(this);
        spiner15.setAdapter(adapter);
        spiner15.setOnItemSelectedListener(this);
        spiner16.setAdapter(adapter);
        spiner16.setOnItemSelectedListener(this);
        spiner17.setAdapter(adapter);
        spiner17.setOnItemSelectedListener(this);
        spiner18.setAdapter(adapter);
        spiner18.setOnItemSelectedListener(this);
        spiner19.setAdapter(adapter);
        spiner19.setOnItemSelectedListener(this);
        spiner20.setAdapter(adapter);
        spiner20.setOnItemSelectedListener(this);
        spiner21.setAdapter(adapter);
        spiner21.setOnItemSelectedListener(this);
        spiner22.setAdapter(adapter);
        spiner22.setOnItemSelectedListener(this);
        spiner23.setAdapter(adapter);
        spiner23.setOnItemSelectedListener(this);
        spiner24.setAdapter(adapter);
        spiner24.setOnItemSelectedListener(this);
        spiner25.setAdapter(adapter);
        spiner25.setOnItemSelectedListener(this);
        spiner26.setAdapter(adapter);
        spiner26.setOnItemSelectedListener(this);
        spiner27.setAdapter(adapter);
        spiner27.setOnItemSelectedListener(this);
        spiner28.setAdapter(adapter);
        spiner28.setOnItemSelectedListener(this);
        spiner29.setAdapter(adapter);
        spiner29.setOnItemSelectedListener(this);
        spiner30.setAdapter(adapter);
        spiner30.setOnItemSelectedListener(this);
        btypee.setAdapter(adapter1);
        btypee.setOnItemSelectedListener(this);
        breel.setAdapter(adapterrel);
        breel.setOnItemSelectedListener(this);
        bgrade.setAdapter(adaptergrade);
        bgrade.setOnItemSelectedListener(this);
        bvisitor.setAdapter(adaptevisitor);
        bgrade.setOnItemSelectedListener(this);
        brannade.setAdapter(adapterannade);
        brannade.setOnItemSelectedListener(this);
        bsoperviser.setAdapter(adaptersarparast);
        bsoperviser.setOnItemSelectedListener(this);
        brezayatv.setAdapter(adapterrezayatv);
        brezayatv.setOnItemSelectedListener(this);
        brezayatto.setAdapter(adapterrezayatto);
        brezayatto.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {
    }

    public void bcaptureImage() {

        isFileChosen = false;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(), "Camera");
        if (!storageDir.exists()) storageDir.mkdir();
        File image = new File(storageDir, cap.toString()+".jpg");
        Uri photoURI = FileProvider.getUriForFile(bazargardi.this, BuildConfig.APPLICATION_ID + ".fileprovider", image);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, 1);

        // imgView.getResources();

    }


    public void CheckGpsStatus() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }


    public void EnableRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(bazargardi.this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(bazargardi.this, "ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(bazargardi.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                 //   Toast.makeText(bazargardi.this, "Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();

                } else {
                 //   Toast.makeText(bazargardi.this, "Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();
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
        bmap = findViewById(R.id.bmap);
        baddressTitle = findViewById(R.id.baddressTitle);
        progressBar = findViewById(R.id.progressBar);
        blatt = findViewById(R.id.blatt);
    }

    // Initializing map
    private void initMap() {


        // Setting map focal position to a fixed position and setting camera zoom
        bmap.moveCamera(new LatLng(38.11535520699953, 46.25988337788993), 0);
        bmap.setZoom(15, 0);


        bmap.setOnCameraMoveFinishedListener(i -> {
            runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
            getReverseApi(bmap.getCameraTargetPosition());
        });
    }

    private void getReverseApi(LatLng currentLocation) {
        getDataService.getReverse(currentLocation.getLatitude(), currentLocation.getLongitude()).enqueue(new Callback<NeshanAddress>() {
            @Override
            public void onResponse(Call<NeshanAddress> call, Response<NeshanAddress> response) {
                String address = response.body().getAddress();
                if (address != null && !address.isEmpty()) {
                    baddressTitle.setText(address);
                    String hadi, hadi1, hadi2;
                    hadi = currentLocation.getLatitude() + "," + currentLocation.getLongitude();
                    hadi1 = currentLocation.getLatitude() + "";
                    hadi2 = currentLocation.getLongitude() + "";
                    blatt.setText(hadi);
                    bbx1.setText(hadi1);
                    bbx2.setText(hadi2);
                    bbadress.setText(baddressTitle.getText());
                    bbyakhurl.setText(blatt.getText());
                    pdformater1.format(pdate);


//******


                } else {
                    baddressTitle.setText("معبر بی‌نام");
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<NeshanAddress> call, Throwable t) {
                baddressTitle.setText("معبر بی‌نام");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

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
                                    rae.startResolutionForResult(bazargardi.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(bazargardi.this, errorMessage, Toast.LENGTH_LONG).show();
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
            bmap.removeMarker(marker);
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
        bmap.addMarker(marker);
    }

    public void focusOnUserLocation(View view) {
        if (userLocation != null) {
            bmap.moveCamera(
                    new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 0.25f);
            bmap.setZoom(20, 0.15f);
        }
    }


}












