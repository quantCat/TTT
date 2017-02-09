package com.example.teacher.myapplication;

import android.widget.Button;

import java.util.Random;

public class AI {
    private Button[][] btArr;

    private int level;
    private static Random random = new Random();
    private String signMy;
    private String signPlaye;
    private String empty;
    private static int[][] lines;

    public AI(Button[][] btArr, int currentLevel) {
        this.btArr = btArr;
        level = currentLevel;
        signMy = MainActivity.SIGN_O;
        signPlaye = MainActivity.SIGN_X;
        empty = MainActivity.SIGN_EMPTY;
        lines = MainActivity.lines;
    }

    public AI(Button[][] btArr, int currentLevel, String signMy) {
        this.btArr = btArr;
        level = currentLevel;
        this.signMy = signMy;
    }

    public int[] nextStep() {
        int x = -1, y = -1;
        switch (level) {
            default:
                for (int i = 0; i < lines.length; i++) {
                    CharSequence first = btArr[lines[i][0]][lines[i][1]].getText();
                    CharSequence second = btArr[lines[i][2]][lines[i][3]].getText();
                    CharSequence third = btArr[lines[i][4]][lines[i][5]].getText();
                    if (first.equals(signPlaye) && second.equals(signPlaye) && third.equals(empty)) {
                        x = lines[i][4];
                        y = lines[i][5];
                        break;
                    }
                    else if (first.equals(signPlaye) && third.equals(signPlaye) && second.equals(empty)) {
                        x = lines[i][2];
                        y = lines[i][3];
                        break;
                    }
                    else if (second.equals(signPlaye) && third.equals(signPlaye) && first.equals(empty)) {
                        x = lines[i][0];
                        y = lines[i][1];
                        break;
                    }
                }
                if (x == -1 && y == -1) {
                    x = 0; y = 0;
                    while (!btArr[x][y].getText().equals(empty)) {
                        x = random.nextInt(3);
                        y = random.nextInt(3);
                    }
                }
        }
        int[] ret = {x, y};
        return ret;
    }
}
