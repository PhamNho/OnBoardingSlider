package com.fpoly.onboardingslider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.fpoly.onboardingslider.R;
import com.fpoly.onboardingslider.adapter.IntroViewPagerAdapter;
import com.fpoly.onboardingslider.model.ScreenItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabIndicator;
    private Button btnNext;
    private Button btnGetStarted;

    private int position = 0;

    List<ScreenItem> screenItems;
    IntroViewPagerAdapter adapter;

    private Animation animBtnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.viewPager);
        tabIndicator = findViewById(R.id.tabLayout);
        btnNext = findViewById(R.id.btnNext);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        animBtnGetStarted = AnimationUtils.loadAnimation(this, R.anim.anim_btn_get_started);

        screenItems = new ArrayList<>();
        screenItems.add(new ScreenItem("Fresh Food", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.img1));
        screenItems.add(new ScreenItem("Fast Delivery", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.img2));
        screenItems.add(new ScreenItem("Easy Payment", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.img3));
        adapter = new IntroViewPagerAdapter(this, screenItems);
        viewPager.setAdapter(adapter);

        if (restorePrefData()){
            startActivity(new Intent(IntroActivity.this, HomeActivity.class));
            finish();
        }

        tabIndicator.setupWithViewPager(viewPager);
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == screenItems.size() - 1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewPager.getCurrentItem();
                if (position < screenItems.size()) {
                    position++;
                    viewPager.setCurrentItem(position);
                }
                if (position == screenItems.size() - 1) {
                    loadLastScreen();
                }
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                savePrefsData();
                finish();
            }
        });
    }

    private void loadLastScreen() {
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);

        btnGetStarted.setAnimation(animBtnGetStarted);
    }

    private void savePrefsData() {
        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isOpened = pref.getBoolean("isIntroOpened", false);
        return isOpened;
    }
}
