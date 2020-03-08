package com.example.codearena;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabLayoutScreenFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    FragmentAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tab layout:","well hello there");
        View view = inflater.inflate(R.layout.tab_layout_screen, container, false);
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tabs);

        pagerAdapter = new FragmentAdapter(getFragmentManager());

        pagerAdapter.addFragment(new Past(), "PAST");
        pagerAdapter.addFragment(new Live(), "LIVE");
        pagerAdapter.addFragment(new Future(), "FUTURE");

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


}
