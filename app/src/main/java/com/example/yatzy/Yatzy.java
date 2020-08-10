package com.example.yatzy;

import android.content.Context;
import android.widget.TextView;

public class Yatzy extends ScoreCategory {
    public Yatzy2 y2;

    Yatzy(TextView t, Context c, Yatzy2 y, Player p){
        text = t;
        player = p;
        storeLocation = player.yatzy;
        storeLocation.score = 0;
        selected = false;
        storeLocation.scored = false;
        context = c;
        y2 = y;
    }

    public ScoreCatNoText getStoreLocation(){
        return player.yatzy;
    }

    @Override
    public void setScore(Dice[] d) {
        if(!storeLocation.scored){
            // Number of each number on the dice, e.g. [number of 1s, number of 2s, ..., number of 6s]
            int[] nums = new int[] {0, 0, 0, 0, 0, 0};
            for(Dice i: d){
                nums[i.currentValue - 1] ++;
            }
            boolean yatzyScored = false;

            for(int i : nums){
                if(i == 5){
                    yatzyScored = true;
                }
            }
            if(yatzyScored){
                storeLocation.score = 50;
                text.setText(String.valueOf(storeLocation.score));
            }
            else{
                storeLocation.score = 0;
                text.setText("-");
            }
        }
    }

    @Override
    public void Score() {
        super.Score();
        y2.Enable();
    }
}