package com.example.codearena;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    FragmentAdapter pagerAdapter;
    LoadingDialog loadingDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshButton:
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                final Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                        startActivity(intent);
                    }
                }, 2000);
                return true;
            default:
                Toast toast = Toast.makeText(this, "Sorry! I didn't get you.", Toast.LENGTH_SHORT);
                toast.show();
                return true;
        }
    }


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new LoadingDialog(MainActivity.this);

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
        pagerAdapter = new FragmentAdapter(getSupportFragmentManager());

        //Add fragments here
        pagerAdapter.addFragment(new Past(), "PAST");
        pagerAdapter.addFragment(new Live(), "LIVE");
        pagerAdapter.addFragment(new Future(), "FUTURE");

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

    }
}



