package com.feds201.scoutingapp2023.component;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.feds201.scoutingapp2023.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GamePieceButton implements Component {
    public ImageButton button;
    public int imageState = 0;
    private final int buttonId;
    private final String type;
    public GamePieceButton(int buttonId, View view, String type) {
        this.button = view.findViewById(buttonId);
        this.buttonId = buttonId;
        this.type = type;
    }

    public static ArrayList<GamePieceButton> generateCyclingButtonsFromPrefix(Class resClass, String prefix, View view) {
        java.lang.reflect.Field[] fields = resClass.getFields();
        ArrayList<GamePieceButton> buttons = new ArrayList<>();

        for (Field field : fields) {
            if (field.getName().contains(prefix)) {
                try {
                    int id = field.getInt(resClass);
                    GamePieceButton button = new GamePieceButton(id, view, prefix);
                    buttons.add(button);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return buttons;
    }

    public void cycleImageAll() {
        this.imageState++;
        switch(this.imageState) {
            case 1:
                this.button.setImageResource(R.drawable.cone);
                break;
            case 2:
                this.button.setImageResource(R.drawable.cube);
                break;
            case 3:
                this.button.setImageResource(R.drawable.nothing);
                this.imageState = 0;
                break;
        }
    }

    public void cycleImage() {
        this.imageState++;
        switch(this.imageState) {
            case 1:
                this.button.setImageResource(R.drawable.cube);
                break;
            case 2:
                this.button.setImageResource(R.drawable.nothing);
                this.imageState = 0;
                break;
        }
    }

    @NonNull
    public String toString() {
        return this.type + " with id " + this.buttonId;
    }


}