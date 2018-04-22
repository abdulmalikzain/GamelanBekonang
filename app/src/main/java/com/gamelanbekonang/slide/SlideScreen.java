package com.gamelanbekonang.slide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gamelanbekonang.R;
import com.gamelanbekonang.splash.SplashScreen;

public class SlideScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private TextView [] dotsTv;
    private Button mNextBtn, mSkipBtn;
    private int []layouts;
    private SlideAdapter slideAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isFirstTimeStartApp()){
            startSplashScreen();
            finish();
        }

        setStatusBarTransparent();

        setContentView(R.layout.activity_slide_screen);

        viewPager = findViewById(R.id.slideViewPage);
        layoutDot = findViewById(R.id.dotsLayout);
        mNextBtn = findViewById(R.id.next_btn);
        mSkipBtn = findViewById(R.id.skip_btn);

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSplashScreen();
            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem()+1;
                if (currentPage < layouts.length){
                    //move to next page
                    viewPager.setCurrentItem(currentPage);
                }else {
                    startSplashScreen();
                }
            }
        });

        layouts = new int[]{R.layout.slide1, R.layout.slide2, R.layout.slide3};
        slideAdapter = new SlideAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(slideAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == layouts.length-1){
                    //lastpage
                    mNextBtn.setText("Start");
                    mSkipBtn.setVisibility(View.GONE);
                }else {
                    mNextBtn.setText("Next");
                    mSkipBtn.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotStatus(0);

    }

    private Boolean isFirstTimeStartApp(){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag", true);
    }

    private void setFirstTimeStartStatus(boolean stt){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartFlag", stt);
        editor.commit();
    }

    private void setDotStatus(int page){
        layoutDot.removeAllViews();
        dotsTv = new TextView[layouts.length];
        for (int i = 0; i < dotsTv.length; i++) {
            dotsTv[i] = new TextView(this);
            dotsTv[i].setText(Html.fromHtml("&#8226"));
            dotsTv[i].setTextSize(30);
            dotsTv[i].setTextColor(Color.parseColor("#cccccc"));
            layoutDot.addView(dotsTv[i]);
        }

        if (dotsTv.length>0){
            dotsTv[page].setTextColor(Color.parseColor("#ffffff"));
        }
    }

    private void startSplashScreen(){
        setFirstTimeStartStatus(false);
        startActivity(new Intent(SlideScreen.this, SplashScreen.class));
        finish();
    }

    private void setStatusBarTransparent (){
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }



}
