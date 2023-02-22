package com.feds201.scoutingapp2023;

import android.content.res.Resources;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class Input extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inputView = inflater.inflate(R.layout.fragment_input, container, false);
        ImageButton btn = inputView.findViewById(R.id.autonbutton1);

        btn.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            @Override
            public void onClick(View view) {
                x++;
                if(x == 1) {
                    btn.setImageResource(R.drawable.cone);
                }
                if(x == 2) {
                    btn.setImageResource(R.drawable.cube);
                }
                if(x == 3) {
                    btn.setImageResource(R.drawable.nothing);
                    x = 0;
                }
            }
        });
        return inputView;
    }
}