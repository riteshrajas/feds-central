package com.feds201.scoutingapp2023;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feds201.scoutingapp2023.api.BlueAllianceAPI;
import com.feds201.scoutingapp2023.api.JSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class Home extends Fragment {

    public String rawMatches = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Configuration config = getResources().getConfiguration();
        View homeView = inflater.inflate(R.layout.fragment_home_tablet, container, false);

//        new Thread(() -> {
//            synchronized (this) {
//                rawMatches = BlueAllianceAPI.RequestMatchesByEventCode("2022miroc", "5ED1uRm7sTzNCXRwuSyPUnFt3uFuDVpO0lZKFQplA2EjCOsqwSWNzQpqwTTRM2ba");
//                JSONObject jsonString = null;
//                try {
//                    jsonString = new JSONObject(rawMatches);
//                    Log.d("json", jsonString.toString(4));
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
////
//
//        synchronized (this) {
//            try {
//                JSONObject jsonString = new JSONObject(rawMatches);
//                Log.d("json", jsonString.toString(4));
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }

        RecyclerView qualsList = homeView.findViewById(R.id.rvQuals);
        qualsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<String> forQualsAdapter = new ArrayList<>();
        forQualsAdapter.add("Joe");
        forQualsAdapter.add("Hello");
        forQualsAdapter.add("Hello");
        forQualsAdapter.add("Hello");
        forQualsAdapter.add("Hello");
        forQualsAdapter.add("Hello");
        QualsRecyclerViewAdapter qualsAdapter = new QualsRecyclerViewAdapter(getActivity(), forQualsAdapter);
        qualsList.setAdapter(qualsAdapter);
        qualsList.setItemAnimator(new DefaultItemAnimator());
        return homeView;
    }
}
