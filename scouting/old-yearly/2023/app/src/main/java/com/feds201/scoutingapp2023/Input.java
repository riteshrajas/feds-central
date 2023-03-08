package com.feds201.scoutingapp2023;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.feds201.scoutingapp2023.component.GamePieceButton;
import com.feds201.scoutingapp2023.component.Tally;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class Input extends Fragment {
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inputView = inflater.inflate(R.layout.fragment_input, container, false);
        /*
            Table of Contents
            ---------------------------------------------------
            1. Initialize all objects
                a. tabs
                b. auton page objects
                c. teleop page objects
                d. endgame page objects
            2. Find all objects with certain prefixes
                a. exclude tabs
                b. auton
                c. teleop
                d. endgame
            3. Hiding objects on tab switch
                a. objects hidden on start
                b. auton tab active
                c. teleop tab active
                d. endgame tab active
            4. Component specific code
                a. auton grid button listeners
                b. teleop grid button listeners
                c. tally event listeners
                d. teleop seek bar event listeners
                e. auton stop light
                f. endgame stop light
                g. advance page on finish button press
          */

        //READ QR PAGE BEFORE WORKING!!!!!!!!!!!!!!!!!!!!!!!!

        // 1. Initialize all objects
        // 1a. tabs
        ImageButton autontab = inputView.findViewById(R.id.autontab);
        ImageButton teleoptab = inputView.findViewById(R.id.teleoptab);
        ImageButton endgametab = inputView.findViewById(R.id.endgametab);

        // 1b. auton page objects
        ArrayList<GamePieceButton> autonButtons = GamePieceButton.generateCyclingButtonsFromPrefix(R.id.class, "auton_button", inputView);
        ImageButton autonGreen = inputView.findViewById(R.id.auton_green);
        ImageButton autonYellow = inputView.findViewById(R.id.auton_yellow);
        ImageButton autonRed = inputView.findViewById(R.id.auton_red);

        Button autonplus = inputView.findViewById(R.id.auton_plus);
        Button autonminus = inputView.findViewById(R.id.auton_minus);
        TextView tally = inputView.findViewById(R.id.auton_tally);
        Tally droppedTally = new Tally(autonplus, autonminus, tally);

        Button autonplus2 = inputView.findViewById(R.id.auton_plus2);
        Button autonminus2 = inputView.findViewById(R.id.auton_minus2);
        TextView tally2 = inputView.findViewById(R.id.auton_tally2);
        Tally acquiredTally = new Tally(autonplus2, autonminus2, tally2);

        // 1c. teleop page objects
        ArrayList<GamePieceButton> teleopButtons = GamePieceButton.generateCyclingButtonsFromPrefix(R.id.class, "teleop_button", inputView);
        SeekBar teleopstrategyseekbar = inputView.findViewById(R.id.teleop_strategybar);
        CheckBox coopertitioncheckbox = inputView.findViewById(R.id.teleop_coopertition);
        RadioButton team1 = inputView.findViewById(R.id.radioButton1);
        RadioButton team2 = inputView.findViewById(R.id.radioButton2);
        RadioButton team3 = inputView.findViewById(R.id.radioButton3);
        TextView strategyTitle = inputView.findViewById(R.id.teleop_strategyviewtext);

        // 1d. endgame page objects
        Button finish = inputView.findViewById(R.id.endgame_finish);
        ImageButton endgameGreen = inputView.findViewById(R.id.endgame_green);
        ImageButton endgameYellow = inputView.findViewById(R.id.endgame_yellow);
        ImageButton endgameRed = inputView.findViewById(R.id.endgame_red);

        Button endgameminus = inputView.findViewById(R.id.endgame_minus);
        Button endgameplus = inputView.findViewById(R.id.endgame_plus);
        TextView endtally = inputView.findViewById(R.id.endgame_tally);
        Tally linksTally = new Tally(endgameplus, endgameminus, endtally);


        // 2. Find all objects with certain prefixes
        Class resClass = R.id.class;
        Field[] fields = resClass.getFields();

        ArrayList<View> allAutonViews = new ArrayList<>();
        ArrayList<View> allTeleopViews = new ArrayList<>();
        ArrayList<View> allEndgameViews = new ArrayList<>();

        for(Field field : fields) {
            // 2a. exclude tabs
            if(!field.getName().contains("tab")) {
                // 2b. auton
                if(field.getName().contains("auton")){
                    try {
                        int id = field.getInt(resClass);
                        allAutonViews.add(inputView.findViewById(id));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } // 2c. teleop
                else if (field.getName().contains("teleop")){
                    try {
                        int id = field.getInt(resClass);
                        allTeleopViews.add(inputView.findViewById(id));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } // 2d. endgame
                else if (field.getName().contains("endgame")){
                    try {
                        int id = field.getInt(resClass);
                        allEndgameViews.add(inputView.findViewById(id));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        // 3. Hiding objects on tab switch
        // 3a. objects hidden on start
        for(View v : allTeleopViews) {
            v.setVisibility(View.GONE);
        }
        for(View v : allEndgameViews) {
            v.setVisibility(View.GONE);
        }

        // 3b. auton tab active
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
            for(View v : allEndgameViews) {
                v.setVisibility(View.GONE);
            }
        });

        // 3c. teleop tab active
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
            for(View v : allEndgameViews) {
                v.setVisibility(View.GONE);
            }
        });

        // 3c. endgame tab active
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
            for(View v : allEndgameViews) {
                v.setVisibility(View.VISIBLE);
            }
        });

        // 4. Component specific code
        // 4a. auton grid button listeners
        for(GamePieceButton gamePieceButton : autonButtons) {
                gamePieceButton.button.setOnClickListener(view -> {
                    if(gamePieceButton.getCycleAll()) {
                        gamePieceButton.cycleImageAll();
                    } else {
                        gamePieceButton.cycleImage();
                    }
               });
        }

        // 4b. teleop grid button listeners
        for(GamePieceButton gamePieceButton : teleopButtons) {
            gamePieceButton.button.setOnClickListener(view -> {
                if(gamePieceButton.getCycleAll()) {
                    gamePieceButton.cycleImageAll();
                } else {
                    gamePieceButton.cycleImage();
                }
            });
        }

        // 4c. tally event listeners
        ArrayList<Tally> tallyList = new ArrayList<>();
        tallyList.add(linksTally);
        tallyList.add(droppedTally);
        tallyList.add(acquiredTally);

        for(Tally t : tallyList) {
            t.minus.setOnClickListener(v -> {
                int x = Integer.parseInt((t.text.getText().toString()));
                x--;
                if (x < 0) { x = 0; }
                t.text.setText(Integer.toString(x));
            });
            t.plus.setOnClickListener(v -> {
                int x = Integer.parseInt((t.text.getText().toString()));
                x++;
                t.text.setText(Integer.toString(x));
            });
        }

        // 4d. teleop seek bar event listeners
        teleopstrategyseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 0) {
                    strategyTitle.setText("Please Select An Option!");
                } else if(i == 1) {
                    strategyTitle.setText("Feeding");
                } else if(i == 2) {
                    strategyTitle.setText("Placing");
                } else if (i == 3){
                    strategyTitle.setText("Both");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 4e. auton stop light event listeners
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

        // 4f. endgame stop light event listeners
        endgameRed.setOnClickListener(view -> {
            endgameRed.setImageResource(R.drawable.red);
            endgameYellow.setImageResource(R.drawable.yellow_trans);
            endgameGreen.setImageResource(R.drawable.green_trans);
        });

        endgameYellow.setOnClickListener(view -> {
            endgameRed.setImageResource(R.drawable.red_trans);
            endgameYellow.setImageResource(R.drawable.yellow);
            endgameGreen.setImageResource(R.drawable.green_trans);
        });

        endgameGreen.setOnClickListener(view -> {
            endgameRed.setImageResource(R.drawable.red_trans);
            endgameYellow.setImageResource(R.drawable.yellow_trans);
            endgameGreen.setImageResource(R.drawable.green);
        });

        // 4g. advance to QRPage on finish button press
        finish.setOnClickListener(view -> {
                getParentFragmentManager().beginTransaction().replace(R.id.body_container, new QRPage()).commit();
        });
        return inputView;
    }
}