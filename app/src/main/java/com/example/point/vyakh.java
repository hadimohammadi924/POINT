package com.example.point;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.NumericShaper;
import java.io.File;
import java.util.ArrayList;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class vyakh extends AppCompatActivity {
    Button vyakh;
    EditText c_code,fcodee,description5;
    String user, c_codee, url, date, time, x1, x2, x3, url2,c_codeee,fcode;
    private ArrayList<FileUtils> selectedFiles2 = new ArrayList<>();
    Boolean isFileChosen = false;
    PersianDate pdate = new PersianDate();
    PersianDateFormat pdformater3 = new PersianDateFormat("YmdHis");
    final String cap=pdformater3.format(pdate);
    TextView namecust,adviseee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vyakh);
        vyakh = findViewById(R.id.vyakh);
        c_code = findViewById(R.id.c_code);
        fcodee = findViewById(R.id.fcodee);
        namecust = findViewById(R.id.namecust);
        adviseee = findViewById(R.id.adviseee);
        description5 = findViewById(R.id.description5);




        FontsOverride.setAppFont((ViewGroup)
                        findViewById(android.R.id.content).getRootView(),
                Typeface.createFromAsset(getAssets(), "iym.ttf"), true);

        SharedPreferences sharedPreferences = getSharedPreferences("logggin", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("user", "");

        PersianDate pdate = new PersianDate();
        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
        PersianDateFormat pdformater2 = new PersianDateFormat("H:i:s");

        date =pdformater1.format(pdate);
        time =pdformater2.format(pdate);



        vyakh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String hadi="https://pgtab.info/vyakhpic/"+cap.toString()+".jpg";
                fcodee.setText(hadi);
                final String c_codee = c_code.getText().toString();
                final String c_codeee = c_codee.toString();
                final  String urll="https://pgtab.info/vyakhpic/"+cap.toString()+".jpg";
                String url2= urll.toString();
                fcode=c_code.getText().toString();
                fcodee.setText(url2);
                isFileChosen = false;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(), "Camera");
                if (!storageDir.exists()) storageDir.mkdir();

                File image = new File(storageDir, cap.toString()+".jpg");


                Uri photoURI = FileProvider.getUriForFile(vyakh.this, BuildConfig.APPLICATION_ID + ".fileprovider", image);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);




                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();
                String url = "https://pgtab.info/Home/insert_vy?id_vy=1&c_code="+c_codeee+"&userv="+user+"&url="+urll.toString()+"&date="+date+"&time="+time+"&x1="+description5.getText().toString()+"&x2=2&x3=3";
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







            }
        });
        c_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getbazar(fcode);
            }

            @Override
            public void afterTextChanged(Editable s) {




            }
        });

    }


    public void bcaptureImager() {




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {









        super.onActivityResult(requestCode, resultCode, data);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);



        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + "Camera",  cap.toString()+".jpg");


        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        FileUtils fileUtils = FileUtils.getMetaData(vyakh.this, contentUri);
        fileUtils.mimeType = "image/jpeg";
        FileUtils.resizeIfMedia(getApplicationContext(), fileUtils);
        selectedFiles2.add(fileUtils);


        String url = "https://pgtab.info/home/upload_vyakh";
        Log.d("url", url);

        MultiPartConnection multipart = new MultiPartConnection(url,
                new MultiPartConnection.FileUploadListener() {
                    @Override
                    public void onUpdateProgress(int percentage, int i) {
                   //    Toast.makeText(vyakh.this, percentage, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(vyakh.this, "تصویر با موفقیت بروزرسانی شد", Toast.LENGTH_SHORT).show();



    }
    public void getbazar(String fcode) {

        RequestQueue requestQueuee;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueuee = new RequestQueue(cache, network);
        requestQueuee.start();
        String url2 = "https://pgtab.info/home/getalll_bazargardiwithcodeee?bcode="+c_code.getText().toString() ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,

                new com.android.volley.Response.Listener<String>() {

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
                            RequestOptions options = new RequestOptions();
                            namecust.setText( "نام فروشگاه:  "+respob.getString("bname"));
                            adviseee.setText("توصیه سرپرست: "+respob.getString("btoosiye")+" "+"تاریخ بازارگردی سرپرست : "+respob.getString("bdate"));
                            final String Bx1=respob.getString("bx1");
                            final String Bx2=respob.getString("bx2");

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
}