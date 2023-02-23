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
        ImageButton btn = inputView.findViewById(R.id.autonbutton5);

        //READ QR PAGE BEFORE WORKING!!!!!!!!!!!!!!!!!!!!!!!!!

        //For the grid (Make button class instead of this bs)
        //This example is with the top middle button
        //Im assuming in the qr cone means 0, cube means 1, nothing means 2, or something like that
        //Also use some variable to keep track of how many cubes / cones on each row
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


        //Charge station checking
        //Red means they didnt climb, yellow means they got on but didnt get the point, green means they got the point
        //Im assuming the colors should return values to tableau so 0 = didnt climb(red), 1 = got on but no point (yellow), 2 = got point (green)
        ImageButton red = inputView.findViewById(R.id.red);
        ImageButton yellow = inputView.findViewById(R.id.yellow);
        ImageButton green = inputView.findViewById(R.id.green);
        red.setOnClickListener(view -> {
            red.setImageResource(R.drawable.red);
            yellow.setImageResource(R.drawable.yellow_trans);
            green.setImageResource(R.drawable.green_trans);
        });

        yellow.setOnClickListener(view -> {
            red.setImageResource(R.drawable.red_trans);
            yellow.setImageResource(R.drawable.yellow);
            green.setImageResource(R.drawable.green_trans);
        });

        green.setOnClickListener(view -> {
            red.setImageResource(R.drawable.red_trans);
            yellow.setImageResource(R.drawable.yellow_trans);
            green.setImageResource(R.drawable.green);
        });

        Button finish = inputView.findViewById(R.id.finish);
        finish.setOnClickListener(view -> {
                getParentFragmentManager().beginTransaction().replace(R.id.body_container, new QRPage()).commit();
        });
        return inputView;
    }
}