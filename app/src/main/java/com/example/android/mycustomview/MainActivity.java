// https://guides.codepath.com/android/defining-custom-views#creating-fully-custom-components

package com.example.android.mycustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView startCamera;
    private ShapeSelectorView ssView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startCamera = findViewById(R.id.recordImageView);
        ssView = findViewById(R.id.shapeSelector);

        startCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ssView.vitals.put("bpm", 67f);
                ssView.postInvalidate();
            }

        });


// Generate random number each time view is rendered
// final int min = 60;
// final int max = 90;
// final int random = new Random().nextInt((max - min) + 1) + min;

//        Param is optional, to run task on UI thread.
//        Handler handler = new Handler(Looper.getMainLooper());
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                // Do the task...
//                handler.postDelayed(this, 5000);
//            }
//        };
//        handler.postDelayed(runnable, 5000);
//
//        // Stop a repeating task like this.
//        handler.removeCallbacks(runnable);
    }
}