package com.example.point;

import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.eftimoff.androipathview.PathView;

public class Splash extends AppCompatActivity {
ImageView imageView;
ProgressBar progressBar;
PathView pathView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
          imageView=findViewById(R.id.imageview);
         progressBar=findViewById(R.id.progressBar);
        //  pathView=findViewById(R.id.pathView);




        Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        imageView.startAnimation(fade_out);


//
//        pathView.getPathAnimator()
//                .delay(100)
//                .duration(4000)
//                .interpolator(new AccelerateDecelerateInterpolator())
//                .start();
//
//        pathView.startAnimation(fade_in);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, loogin.class));
            }
        }, 4000);


    }



            }







