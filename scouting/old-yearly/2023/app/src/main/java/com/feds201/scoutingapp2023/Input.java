package com.feds201.scoutingapp2023;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.feds201.scoutingapp2023.component.GamePieceButton;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class Input extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inputView = inflater.inflate(R.layout.fragment_input, container, false);
        //READ QR PAGE BEFORE WORKING!!!!!!!!!!!!!!!!!!!!!!!!!

        //For the grid (Make button class instead of this bs)
        //This example is with the top middle button
        //Im assuming in the qr cone means 0, cube means 1, nothing means 2, or something like that
        //Also use some variable to keep track of how many cubes / cones on each row

        //TAB SYSTEM
        ImageButton autontab = inputView.findViewById(R.id.autontab);
        ImageButton teleoptab = inputView.findViewById(R.id.teleoptab);
        ImageButton endgametab = inputView.findViewById(R.id.endgametab);

        //EVERYTHING ON AUTON PAGE
        ArrayList<GamePieceButton> autonButtons = GamePieceButton.generateCyclingButtonsFromPrefix(R.id.class, "autonbutton", inputView);
        ImageButton autonGreen = inputView.findViewById(R.id.auton_green);
        ImageButton autonYellow = inputView.findViewById(R.id.auton_yellow);
        ImageButton autonRed = inputView.findViewById(R.id.auton_red);

        // 1-9   is high
        // 10-18 is med
        // 19-27 is low
        Class resClass = R.id.class;
        Field[] fields = resClass.getFields();

        ArrayList<View> allAutonViews = new ArrayList<>();
        ArrayList<View> allTeleopViews = new ArrayList<>();

        for(Field field : fields) {
            if(!field.getName().contains("tab") && !field.getName().contains("checkbox")) {
                if(field.getName().contains("auton")){
                    try {
                        int id = field.getInt(resClass);
                        allAutonViews.add(inputView.findViewById(id));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (field.getName().contains("teleop")){
                    try {
                        int id = field.getInt(resClass);
                        allTeleopViews.add(inputView.findViewById(id));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        //EVERYTHING ON TELEOP PAGE
        ArrayList<GamePieceButton> teleopButtons = GamePieceButton.generateCyclingButtonsFromPrefix(R.id.class, "teleopbutton", inputView);

        //EVERYTHING ON ENDGAME PAGE
        Button finish = inputView.findViewById(R.id.endgame_finish);

        //STUFF THAT SHOULD BE HIDDEN WHILE APP IS ON START
        // teleop disabled and hidden
        for(View v : allTeleopViews) {
            v.setVisibility(View.GONE);
        }

        // endtab disabled and hidden
        finish.setVisibility(View.GONE);

        //TAB SWITCHING
        autontab.setOnClickListener(view -> {
            // auton visible and active
            autontab.setImageResource(R.drawable.autontab);
            for(View v : allAutonViews) {
                v.setVisibility(View.VISIBLE);
            }

            // teleop transparent and disabled
            teleoptab.setImageResource(R.drawable.teleoptabtrans);
            for(View v : allTeleopViews) {
                v.setVisibility(View.GONE);
            }

            // finish transparent and disabled
            endgametab.setImageResource(R.drawable.endgametabtrans);
            finish.setVisibility(View.GONE);
        });

        teleoptab.setOnClickListener(view -> {
            autontab.setImageResource(R.drawable.autontabtrans);
            for(View v : allAutonViews) {
                v.setVisibility(View.GONE);
            }

            teleoptab.setImageResource(R.drawable.teleoptab);
            for(View v : allTeleopViews) {
                v.setVisibility(View.VISIBLE);
            }

            endgametab.setImageResource(R.drawable.endgametabtrans);
            finish.setVisibility(View.GONE);
        });

        endgametab.setOnClickListener(view -> {
            autontab.setImageResource(R.drawable.autontabtrans);
            for(View v : allAutonViews) {
                v.setVisibility(View.GONE);
            }

            teleoptab.setImageResource(R.drawable.teleoptabtrans);
            for(View v : allTeleopViews) {
                v.setVisibility(View.GONE);
            }

            endgametab.setImageResource(R.drawable.endgametab);
            finish.setVisibility(View.VISIBLE);
        });

        //AUTON BUTTON ON CLICK LISTENERS
        for(GamePieceButton gamePieceButton : autonButtons) {
            Log.d("DEBUG", gamePieceButton.toString());
            gamePieceButton.button.setOnClickListener(view -> {
                gamePieceButton.cycleImage();
            });
        }

        for(GamePieceButton gamePieceButton : teleopButtons) {
            Log.d("DEBUG", gamePieceButton.toString());
            gamePieceButton.button.setOnClickListener(view -> {
                gamePieceButton.cycleImage();
            });
        }

        //AUTON CHARGE STATION CODE
        autonRed.setOnClickListener(view -> {
            autonRed.setImageResource(R.drawable.red);
            autonYellow.setImageResource(R.drawable.yellow_trans);
            autonGreen.setImageResource(R.drawable.green_trans);
        });

        autonYellow.setOnClickListener(view -> {
            autonRed.setImageResource(R.drawable.red_trans);
            autonYellow.setImageResource(R.drawable.yellow);
            autonGreen.setImageResource(R.drawable.green_trans);
        });

        autonGreen.setOnClickListener(view -> {
            autonRed.setImageResource(R.drawable.red_trans);
            autonYellow.setImageResource(R.drawable.yellow_trans);
            autonGreen.setImageResource(R.drawable.green);
        });

        finish.setOnClickListener(view -> {
                getParentFragmentManager().beginTransaction().replace(R.id.body_container, new QRPage()).commit();
        });
        return inputView;
    }
}