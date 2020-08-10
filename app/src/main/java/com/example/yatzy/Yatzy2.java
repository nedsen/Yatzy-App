package com.example.yatzy;

import android.content.Context;
import android.widget.TextView;

public class Yatzy2 extends ScoreCategory {
    boolean firstYatzyScored;

    Yatzy2(TextView t, Context c, Player p){
        text = t;
        player = p;
        storeLocation = player.yatzy2;
        storeLocation.score = 0;
        selected = false;
        storeLocation.scored = false;
        context = c;
        firstYatzyScored = false;
    }
    public void Enable(){
        firstYatzyScored = true;
    }
    @Override
    public void setScore(Dice[] d) {
        if(!storeLocation.scored){
            boolean yatzyScored = false;
            if(firstYatzyScored){
                // Number of each number on the dice, e.g. [number of 1s, number of 2s, ..., number of 6s]
                int[] nums = new int[] {0, 0, 0, 0, 0, 0};
                for(Dice i: d){
                    nums[i.currentValue - 1] ++;
                }

                for(int i : nums){
                    if(i == 5){
                        yatzyScored = true;
                    }
                }
            }
            if(yatzyScored){
                storeLocation.score = 100;
                text.setText(String.valueOf(storeLocation.score));
            }
            else{
                storeLocation.score = 0;
                text.setText("-");
            }
        }
    }
    public ScoreCatNoText getStoreLocation(){
        return player.yatzy2;
    }
}