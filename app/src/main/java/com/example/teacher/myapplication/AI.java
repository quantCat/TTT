package com.example.teacher.myapplication;

import android.widget.Button;

import java.util.Random;

public class AI {
    private Button[][] btArr;

    private int level;
    private static Random random = new Random();
    private String signMy = MainActivity.SIGN_O;
    private String signPlayer = MainActivity.SIGN_X;;
    private String empty = MainActivity.SIGN_EMPTY;
    private static int[][] lines = MainActivity.lines;

    public AI(Button[][] btArr, int currentLevel, String signMy) {
        this.btArr = btArr;
        level = currentLevel;
        this.signMy = signMy;
    }

    public int[] nextStep() {
        int x = -1, y = -1;
        switch (level) {
            case 0:
                x = random.nextInt(3);
                y = random.nextInt(3);
                while (!btArr[x][y].getText().equals(empty)) {
                    x = random.nextInt(3);
                    y = random.nextInt(3);
                }
                break;
            case 1:
                for (int i = 0; i < lines.length; i++) {
                    CharSequence first = btArr[lines[i][0]][lines[i][1]].getText();
                    CharSequence second = btArr[lines[i][2]][lines[i][3]].getText();
                    CharSequence third = btArr[lines[i][4]][lines[i][5]].getText();
                    if (first.equals(signPlayer) && second.equals(signPlayer) && third.equals(empty)) {
                        x = lines[i][4];
                        y = lines[i][5];
                        break;
                    }
                    else if (first.equals(signPlayer) && third.equals(signPlayer) && second.equals(empty)) {
                        x = lines[i][2];
                        y = lines[i][3];
                        break;
                    }
                    else if (second.equals(signPlayer) && third.equals(signPlayer) && first.equals(empty)) {
                        x = lines[i][0];
                        y = lines[i][1];
                        break;
                    }
                }
                if (x == -1 && y == -1) {
                    x = random.nextInt(3);
                    y = random.nextInt(3);
                    while (!btArr[x][y].getText().equals(empty)) {
                        x = random.nextInt(3);
                        y = random.nextInt(3);
                    }
                }
                break;
            default:
                for (int i = 0; i < lines.length; i++) {
                    CharSequence first = btArr[lines[i][0]][lines[i][1]].getText();
                    CharSequence second = btArr[lines[i][2]][lines[i][3]].getText();
                    CharSequence third = btArr[lines[i][4]][lines[i][5]].getText();
                    if (!first.equals(empty) && !second.equals(empty) && first.equals(second) && third.equals(empty)) {
                        x = lines[i][4];
                        y = lines[i][5];
                        break;
                    }
                    else if (!first.equals(empty) && !third.equals(empty) && first.equals(third) && second.equals(empty)) {
                        x = lines[i][2];
                        y = lines[i][3];
                        break;
                    }
                    else if (!second.equals(empty) && !third.equals(empty) && second.equals(third) && first.equals(empty)) {
                        x = lines[i][0];
                        y = lines[i][1];
                        break;
                    }
                }
                if (x == -1 && y == -1) {
                    x = random.nextInt(3);
                    y = random.nextInt(3);
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