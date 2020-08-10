package com.example.yatzy;

import android.content.Context;
import android.widget.TextView;

public class FourOfAKind extends ScoreCategory {
    FourOfAKind(TextView t, Context c, Player p){
        text = t;
        player = p;
        storeLocation = player.fourOfAKind;
        storeLocation.score = 0;
        selected = false;
        storeLocation.scored = false;
        context = c;
    }

    public ScoreCatNoText getStoreLocation(){
        return player.fourOfAKind;
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
            int sumnums = 0;
            for(int i = 0; i < nums.length; i++){
                if(nums[i] > 3){
                    threepresent = true;
                }
                sumnums += nums[i]*(i+1);
            }
            if(threepresent){
                storeLocation.score = sumnums;
                text.setText(String.valueOf(storeLocation.score));
            }
            else{
                storeLocation.score = 0;
                text.setText("-");
            }
        }
    }
}