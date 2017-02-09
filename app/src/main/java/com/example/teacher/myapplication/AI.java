package com.example.teacher.myapplication;

/**
 * Created by Teacher on 1/19/2017.
 */

import android.widget.Button;

import java.util.Random;

public class AI {
    private Button[][] btArr;

    private int level;
    private static Random random = new Random();
    private final String signMy;

    public AI(Button[][] btArr, int currentLevel) {
        this.btArr = btArr;
        level = currentLevel;
        signMy = MainActivity.SIGN_O;
    }

    public AI(Button[][] btArr, int currentLevel, String signMy) {
        this.btArr = btArr;
        level = currentLevel;
        this.signMy = signMy;
    }

    public boolean nextStep() {
        switch (level){
            default:
                for(int i=0;i<10;i++) {
                    if (changeValue(random.nextInt(3), random.nextInt(3))) {
                        return true;
                    }
                }
            case 0:
                for (int i = 0; i < btArr.length; i++) {
                    for (int j = 0; j < btArr[i].length; j++) {
                        if(changeValue(i, j)){
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    private boolean changeValue(int i, int j){
        if(btArr[i][j].getText().equals(MainActivity.SIGN_EMPTY)){
            btArr[i][j].setText(signMy);
            return true;
        }
        return false;
    }
}