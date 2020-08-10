package com.example.yatzy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {

    private TableRow[] rows;

    private int[] rowids = new int[]{
            R.id.endRow1,
            R.id.endRow2,
            R.id.endRow3,
            R.id.endRow4,
            R.id.endRow5,
            R.id.endRow6,
            R.id.endRow7,
            R.id.endRow8,
    };

    private TextView[] names;

    private int[] nameids = new int[]{
            R.id.endTextPlayer1,
            R.id.endTextPlayer2,
            R.id.endTextPlayer3,
            R.id.endTextPlayer4,
            R.id.endTextPlayer5,
            R.id.endTextPlayer6,
            R.id.endTextPlayer7,
            R.id.endTextPlayer8,
    };

    private TextView[] scores;

    private int[] scoreids = new int[]{
            R.id.endScorePlayer1,
            R.id.endScorePlayer2,
            R.id.endScorePlayer3,
            R.id.endScorePlayer4,
            R.id.endScorePlayer5,
            R.id.endScorePlayer6,
            R.id.endScorePlayer7,
            R.id.endScorePlayer8,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        //Get number of Players and player names
        Bundle b = getIntent().getExtras();
        final int numPlayers = b.getInt("numPlayers");
        final String[] playerNames = b.getStringArray("playerNames");
        final int[] playerScores = b.getIntArray("playerScores");

        rows = new TableRow[numPlayers];
        scores = new TextView[numPlayers];
        names = new TextView[numPlayers];

        sort(numPlayers, playerScores, playerNames);

        for(int i = 0; i < numPlayers; i ++){
            rows[i] = findViewById(rowids[i]);
            names[i] = findViewById(nameids[i]);
            scores[i] = findViewById(scoreids[i]);

            rows[i].setVisibility(View.VISIBLE);
            names[i].setText(orderedNames[i]);
            scores[i].setText(String.valueOf(orderedScores[i]));
        }




    }
    private int[] orderedScores;
    private String[] orderedNames;
    //possible algorithm to sort players' scores
    private void sort(int len, int[] nums, String[] strs){
        int[] orderedNums = new int[len];
        String[] orderedStrs = new String[len];

        orderedNums[0] = nums[0];
        orderedStrs[0] = strs[0];
        for(int i = 1; i < len; i ++){
            orderedNums[i] = 0;
            orderedStrs[i] = " ";
        }

        for(int i = 1; i < len; i ++){
            for(int j = 0; j <= i; j ++){
                if(nums[i] > orderedNums[j]){
                    for(int k = len - 1; k > j; k--){
                        orderedNums[k] = orderedNums[k - 1];
                        orderedStrs[k] = orderedStrs[k - 1];
                    }
                    orderedNums[j] = nums[i];
                    orderedStrs[j] = strs[i];
                    break;
                }
            }
        }
        orderedScores = orderedNums;
        orderedNames = orderedStrs;
    }
}
