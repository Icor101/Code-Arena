package com.example.codearena;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    FragmentAdapter pagerAdapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
        pagerAdapter = new FragmentAdapter(getSupportFragmentManager());

        //Add fragments here
        pagerAdapter.addFragment(new Past(), "PAST");
        pagerAdapter.addFragment(new Live(), "LIVE");
        pagerAdapter.addFragment(new Future(), "FUTURE");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}



