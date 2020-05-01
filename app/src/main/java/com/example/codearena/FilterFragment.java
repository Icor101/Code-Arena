package com.example.codearena;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class FilterFragment extends Fragment {
    static Set<String> set = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Filter");
        getActivity().findViewById(R.id.refreshButton).setVisibility(View.INVISIBLE);
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        final CheckBox codechef = view.findViewById(R.id.codechef);
        final CheckBox hackerearth = view.findViewById(R.id.hackerearth);
        final CheckBox hackerrank = view.findViewById(R.id.hackerrank);
        final CheckBox topcoder = view.findViewById(R.id.topcoder);
        final CheckBox codeforces = view.findViewById(R.id.codeforces);
        final CheckBox spoj = view.findViewById(R.id.spoj);
        final CheckBox atcoder = view.findViewById(R.id.atcoder);
        final CheckBox leetcode = view.findViewById(R.id.leetcode);
        final CheckBox other = view.findViewById(R.id.other);

        codechef.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (codechef.isChecked()){
                    set.add("codechef");
                }else{
                    set.remove("codechef");
                }
            }
        });
        hackerearth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hackerearth.isChecked()){
                    set.add("hackerearth");
                }else{
                    set.remove("hackerearth");
                }
            }
        });
        hackerrank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hackerrank.isChecked()){
                    set.add("hackerrank");
                }else{
                    set.remove("hackerrank");
                }
            }
        });
        topcoder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (topcoder.isChecked()){
                    set.add("topcoder");
                }else{
                    set.remove("topcoder");
                }
            }
        });
        codeforces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (codeforces.isChecked()){
                    set.add("codeforces");
                }else{
                    set.remove("codeforces");
                }
            }
        });
        spoj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (spoj.isChecked()){
                    set.add("spoj");
                }else{
                    set.remove("spoj");
                }
            }
        });
        atcoder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (atcoder.isChecked()){
                    set.add("atcoder");
                }else{
                    set.remove("atcoder");
                }
            }
        });
        leetcode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (leetcode.isChecked()){
                    set.add("leetcode");
                }else{
                    set.remove("leetcode");
                }
            }
        });
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (other.isChecked()){
                    set.add("other");
                }else{
                    set.remove("other");
                }
            }
        });

        return view;
    }
}
