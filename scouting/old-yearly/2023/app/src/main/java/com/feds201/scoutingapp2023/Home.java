package com.feds201.scoutingapp2023;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feds201.scoutingapp2023.api.BlueAllianceAPI;
import com.feds201.scoutingapp2023.api.JSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Home extends Fragment {

    public JSONArray matches = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Configuration config = getResources().getConfiguration();
        View homeView = inflater.inflate(R.layout.fragment_home_tablet, container, false);

//        new Thread(() -> {
//            synchronized (this) {
//                String rawMatches = BlueAllianceAPI.RequestMatchesByEventCode("2022miroc", "5ED1uRm7sTzNCXRwuSyPUnFt3uFuDVpO0lZKFQplA2EjCOsqwSWNzQpqwTTRM2ba");
//                try {
//                    matches = new JSONArray(rawMatches);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        ArrayList<String> matchesList = new ArrayList<>();
        matchesList.add("Joe");

//        synchronized (this) {
//            try {
//                for(int i = 0; i < matches.length(); i++) {
//                    JSONObject jo = (JSONObject) matches.get(i);
//                    Log.d("match"+i, jo.toString(4));
//                }
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }

        RecyclerView qualsList = homeView.findViewById(R.id.rvQuals);
        qualsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        matchesList.add("Hello");
        matchesList.add("Hello");
        matchesList.add("Hello");
        matchesList.add("Hello");
        matchesList.add("Hello");
        QualsRecyclerViewAdapter qualsAdapter = new QualsRecyclerViewAdapter(getActivity(), matchesList);
        qualsList.setAdapter(qualsAdapter);
        qualsList.setItemAnimator(new DefaultItemAnimator());
        return homeView;
    }
}
