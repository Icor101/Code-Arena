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
        pagerAdapter.addFragment(new Past(), "Past");
        pagerAdapter.addFragment(new Live(), "Live");
        pagerAdapter.addFragment(new Future(), "Future");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}



