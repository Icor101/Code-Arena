package com.example.codearena;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class AboutUsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("About Us");
        getActivity().findViewById(R.id.refreshButton).setVisibility(View.INVISIBLE);
        return inflater.inflate(R.layout.fragment_about_us,container,false);
    }
}
