package com.example.android.mycustomview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Dashboard extends Activity implements View.OnClickListener {
    private ImageView backArrow;
    private Intent mainActivity;
    private LinearLayout type_of_reading_user_controls;
    private ImageView heartbeat_selector, spo2_selector, stress_index_selector,
                      respiration_rate_selector, heart_rate_variability_selector,
                      blood_pressure_selector, activeView;
    private int readingsUserControlCount, activeSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        mainActivity = new Intent(this, MainActivity.class);
        backArrow = findViewById(R.id.back_arrow);
        type_of_reading_user_controls = findViewById(R.id.type_of_reading_user_controls);

        activeSelector = R.id.heartbeat_selector;
        activeView = findViewById(activeSelector);

        heartbeat_selector = findViewById(R.id.heartbeat_selector);
        spo2_selector = findViewById(R.id.spo2_selector);
        stress_index_selector = findViewById(R.id.stress_index_selector);
        respiration_rate_selector = findViewById(R.id.respiration_rate_selector);
        heart_rate_variability_selector = findViewById(R.id.heart_rate_variability_selector);
        blood_pressure_selector = findViewById(R.id.blood_pressure_selector);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivity);
            }
        });

        addOnclickListeners(type_of_reading_user_controls);
    }


    private void addOnclickListeners(LinearLayout ll) {
        int cnt = ll.getChildCount();
        for (int count = 0; count < cnt; count++) {
            final View child = ll.getChildAt(count);
//            child.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // make the last view inactive
//                    activeView = findViewById(activeSelector);
//                    activeView.setBackground(getDrawable(R.drawable.icon_background));
//                    String uri = "@drawable/" + activeSelector;
//                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//                    Drawable res = getResources().getDrawable(imageResource);
//                    activeView.setImageDrawable(res);
//
//                    // make current view active
//                    view.setBackground(getDrawable(R.drawable.curved_rect_active));
//                    ImageView view = (ImageView) view;
//
//                    activeSelector = view.getId();
//                    uri = "@drawable/" + activeSelector;
//                    imageResource = getResources().getIdentifier(uri, null, getPackageName());
//                    res = getResources().getDrawable(imageResource);
//                    view;
//
//                }
//            });
            child.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        // make the last view inactive
        activeView = findViewById(activeSelector);
        activeView.setBackground(getDrawable(R.drawable.icon_background));
        String uri = "@drawable/" + activeSelector;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        activeView.setImageDrawable(res);

        switch(v.getId())
        {
            case R.id.heartbeat_selector:
                heartbeat_selector.setImageResource(R.drawable.ic_heart_beat_selector_active);
                heartbeat_selector.setBackground(getDrawable(R.drawable.curved_rect_active));
                activeSelector = R.id.heartbeat_selector;
                break;
            case R.id.spo2_selector:
                spo2_selector.setImageResource(R.drawable.ic_heart_beat_selector_active);
                spo2_selector.setBackground(getDrawable(R.drawable.curved_rect_active));
                activeSelector = R.id.spo2_selector;
                break;
            case R.id.stress_index_selector:
                stress_index_selector.setImageResource(R.drawable.ic_heart_beat_selector_active);
                stress_index_selector.setBackground(getDrawable(R.drawable.curved_rect_active));
                activeSelector = R.id.stress_index_selector;
                break;

            case R.id.respiration_rate_selector:
                respiration_rate_selector.setImageResource(R.drawable.ic_heart_beat_selector_active);
                respiration_rate_selector.setBackground(getDrawable(R.drawable.curved_rect_active));
                activeSelector = R.id.respiration_rate_selector;
                break;
            case R.id.heart_rate_variability_selector:
                heart_rate_variability_selector.setImageResource(R.drawable.ic_heart_beat_selector_active);
                heart_rate_variability_selector.setBackground(getDrawable(R.drawable.curved_rect_active));
                activeSelector = R.id.heart_rate_variability_selector;
                break;
            case R.id.blood_pressure_selector:
                blood_pressure_selector.setImageResource(R.drawable.ic_heart_beat_selector_active);
                blood_pressure_selector.setBackground(getDrawable(R.drawable.curved_rect_active));
                activeSelector = R.id.blood_pressure_selector;
                break;
        }
    }
}
