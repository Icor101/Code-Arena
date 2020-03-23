package com.example.codearena;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Future extends Fragment {
    private RecyclerView recyclerView;
    private List<ContestDetails> myDataset;
    private RecyclerView.Adapter mAdapter;
    private RestClient rc;
    private TextView emptyList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rc = new RestClient();
        rc.execute(getString(R.string.upcomingUrl));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.future, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyList = view.findViewById(R.id.emptyList);
        recyclerView = view.findViewById(R.id.my_recycler_view_future);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (myDataset != null && myDataset.isEmpty())
            emptyList.setVisibility(View.VISIBLE);
        else
            emptyList.setVisibility(View.INVISIBLE);
    }

    class RestClient extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter = new MyAdapter(getContext(), myDataset);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }

        String task(String address) {
            String res = "";
            try {
                URL url = new URL(address);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String output;
                while ((output = br.readLine()) != null) {
                    res += output;
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String res = task(strings[0]);
            Gson gson = new Gson();
            JSONArray jsonArray = null;
            myDataset = new ArrayList<>();
            try {
                jsonArray = new JSONArray(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    myDataset.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), ContestDetails.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

}
