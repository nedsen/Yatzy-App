package com.example.yatzy;

import android.content.Context;
import android.widget.TextView;

public class FullHouse extends ScoreCategory {
    FullHouse(TextView t, Context c, Player p){
        text = t;
        player = p;
        storeLocation = player.fullHouse;
        storeLocation.score = 0;
        selected = false;
        storeLocation.scored = false;
        context = c;
    }

    public ScoreCatNoText getStoreLocation(){
        return player.fullHouse;
    }

    @Override
    public void setScore(Dice[] d) {
        if(!storeLocation.scored){
            // Number of each number on the dice, e.g. [number of 1s, number of 2s, ..., number of 6s]
            int[] nums = new int[] {0, 0, 0, 0, 0, 0};
            for(Dice i: d){
                nums[i.currentValue - 1] ++;
            }
            boolean threepresent = false;
            boolean twopresent = false;
            for(int i : nums){
                if(i == 2){
                    twopresent = true;
                }
                else if(i == 3){
                    threepresent = true;
                }
            }
            if(threepresent && twopresent){
                storeLocation.score = 25;
                text.setText(String.valueOf(storeLocation.score));
            }
            else{
                storeLocation.score = 0;
                text.setText("-");
            }
        }
    }
}