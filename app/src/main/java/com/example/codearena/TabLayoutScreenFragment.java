package com.example.codearena;

import android.os.Bundle;
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
    Future future;
    Past past;
    Live live;

    TabLayoutScreenFragment() {
        future = new Future();
        past = new Past();
        live = new Live();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("CodeArena");
        View view = inflater.inflate(R.layout.tab_layout_screen, container, false);
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tabs);

        pagerAdapter = new FragmentAdapter(getFragmentManager());

        pagerAdapter.addFragment(past, "PAST");
        pagerAdapter.addFragment(live, "LIVE");
        pagerAdapter.addFragment(future, "FUTURE");

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


}