package com.example.codearena;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    final ArrayList<Fragment> fragments = new ArrayList<>();
    final ArrayList<String> titles = new ArrayList<>();

    FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

}
