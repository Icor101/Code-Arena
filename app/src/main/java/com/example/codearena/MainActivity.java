package com.example.codearena;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static MainActivity mInstance;
    LoadingDialog loadingDialog;
    DrawerLayout drawer;
    NoInternetFragment noInternetFragment;
    static TabLayoutScreenFragment tabLayoutScreenFragment;
    AboutUsFragment aboutUsFragment;
    FilterFragment filterFragment;
    DevelopersFragment developersFragment;

    public static synchronized MainActivity getInstance() {
        return mInstance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshButton:
                if (checkConnection()) {
                    loadingDialog.startLoadingDialog();
                    Handler handler = new Handler();
                    final Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismissDialog();
                            tabLayoutScreenFragment = new TabLayoutScreenFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tabLayoutScreenFragment).commit();
                        }
                    }, 2000);
                    //loaded = true;
                } else {
                    Toast.makeText(this, "Sorry! Not connected to the internet", Toast.LENGTH_SHORT).show();
                }
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
        mInstance = this;
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        loadingDialog = new LoadingDialog(MainActivity.this);
        tabLayoutScreenFragment = new TabLayoutScreenFragment();
        aboutUsFragment = new AboutUsFragment();
        filterFragment = new FilterFragment();
        developersFragment = new DevelopersFragment();
        noInternetFragment = new NoInternetFragment();
        if (checkConnection()) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tabLayoutScreenFragment).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, noInternetFragment).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                if (checkConnection() || (Live.myDataset!=null && Past.myDataset!=null && Future.myDataset!=null)) {
                    transaction.replace(R.id.fragment_container, tabLayoutScreenFragment).commit();
                    //transaction.addToBackStack("cache");
                    this.findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
                    break;
                } else {
                    transaction.replace(R.id.fragment_container, noInternetFragment).commit();
                    this.findViewById(R.id.refreshButton).setVisibility(View.VISIBLE);
                    break;
                }
            case R.id.nav_about_us:
                transaction.replace(R.id.fragment_container, aboutUsFragment).commit();
                transaction.addToBackStack(null);
                break;
            case R.id.nav_filter:
                transaction.replace(R.id.fragment_container, filterFragment).commit();
                transaction.addToBackStack(null);
                break;
            case R.id.nav_developers:
                transaction.replace(R.id.fragment_container, developersFragment).commit();
                transaction.addToBackStack(null);
                break;
            case R.id.nav_feedback:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"riyamish16@gmail.com", "mit.aniket98@gmail.com", "apurvsingh98@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send Email:"));
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    private boolean checkConnection() {
        return ConnectivityReceiver.isConnected();
    }
}