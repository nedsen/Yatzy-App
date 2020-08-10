package com.example.yatzy;

import android.content.Context;
import android.widget.TextView;

public class Chance extends ScoreCategory {
    Chance(TextView t, Context c, Player p){
        player = p;
        storeLocation = player.chance;
        storeLocation.score = 0;
        selected = false;
        storeLocation.scored = false;
        context = c;
        text = t;
    }

    public ScoreCatNoText getStoreLocation(){
        return player.chance;
    }

    @Override
    public void setScore(Dice[] d) {
        if(!storeLocation.scored){
            // Number of each number on the dice, e.g. [number of 1s, number of 2s, ..., number of 6s]
            int[] nums = new int[] {0, 0, 0, 0, 0, 0};
            for(Dice i: d){
                nums[i.currentValue - 1] ++;
            }
            int sumnums = 0;
            for(int i = 0; i < nums.length; i++){
                sumnums += nums[i]*(i+1);
            }
            storeLocation.score = sumnums;
            text.setText(String.valueOf(storeLocation.score));
        }
    }
}