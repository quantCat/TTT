package com.example.teacher.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int[][] btArrIds =
            {
                    {R.id.bt00, R.id.bt01, R.id.bt02},
                    {R.id.bt10, R.id.bt11, R.id.bt12},
                    {R.id.bt20, R.id.bt21, R.id.bt22},
            };
    public static final String SIGN_X = "X";
    public static final String SIGN_O = "0";
    public static final String SIGN_EMPTY = " ";

    private Button[][] btArr = new Button[3][3];

    public static int[][] lines = {{0,0,0,1,0,2},
            {1,0,1,1,1,2},
            {2,0,2,1,2,2},
            {0,0,1,0,2,0},
            {0,1,1,1,2,1},
            {0,2,1,2,2,2},
            {0,0,1,1,2,2},
            {0,2,1,1,2,0}};

    private com.example.teacher.myapplication.AI ai;

    /**
     * current game state
     */
    private int gameState;
    private static final int GAME_IN_PROCESS = 0;
    private static final int GAME_OVER_LOS = 1;
    private static final int GAME_OVER_WIN = 2;
    private static final int GAME_OVER_NONE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * init variables for game. Enough do it one time
     */
    private void init() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameState == GAME_IN_PROCESS) {
                    if(((Button) v).getText().equals(SIGN_EMPTY)) {
                        ((Button) v).setText(SIGN_X);
                        afterStep();
                    } else {
                        return;
                    }
                }
                if(gameState == GAME_IN_PROCESS) {
                    int[] step = {0, 0};
                    step = ai.nextStep();
                    btArr[step[0]][step[1]].setText(SIGN_O);
                    afterStep();
                }
            }
        };


        for (int i = 0; i < btArr.length; i++) {
            for (int j = 0; j < btArr[i].length; j++) {
                btArr[i][j] = (Button) findViewById(btArrIds[i][j]);
                btArr[i][j].setOnClickListener(listener);
            }
        }

        ai = new AI(btArr, 0);

        //use SeekBar for set haw smart is our AI
        ((SeekBar)findViewById(R.id.seek_bar_level)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ai = new AI(btArr, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        startGame();
    }

    /**
     * Click on button "New Game"
     */
    public void newGame(View view) {
        startGame();
    }

    private void startGame() {
        gameState = 0;
        postMessage("Your turn!!!");

        for (int i = 0; i < btArr.length; i++) {
            for (int j = 0; j < btArr[i].length; j++) {
                btArr[i][j].setText(SIGN_EMPTY);
            }
        }
    }

    private void afterStep() {
        gameState = checkGameState();
        switch (gameState) {
            case GAME_OVER_LOS:
                postMessage(getString(R.string.game_over_los));
                break;
            case GAME_OVER_WIN:
                postMessage(getString(R.string.game_over_win));
                break;
            case GAME_OVER_NONE:
                postMessage("There no winners!!!");
                break;
        }
    }

    /**
     * check if there is winners
     */
    private int checkGameState() {

        for (int i = 0; i < lines.length; i++) {
            CharSequence first = btArr[lines[i][0]][lines[i][1]].getText();
            CharSequence second = btArr[lines[i][2]][lines[i][3]].getText();
            CharSequence third = btArr[lines[i][4]][lines[i][5]].getText();
            if(first.equals(second) && second.equals(third) && !third.equals(SIGN_EMPTY)){
                if(first.equals(SIGN_O)) {
                    return GAME_OVER_LOS;
                } else {
                    return GAME_OVER_WIN;
                }
            }
        }
        //check for available steps
        for (int i = 0; i < btArr.length; i++) {
            for (int j = 0; j < btArr[i].length; j++) {
                if(btArr[i][j].getText().equals(SIGN_EMPTY)){
                    return GAME_IN_PROCESS;
                }
            }
        }
        return GAME_OVER_NONE;
    }

    /**
     * post message
     */
    private void postMessage(String message){
        //set message to TextView
        ((TextView)findViewById(R.id.info_txt)).setText(message);
        //show toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
