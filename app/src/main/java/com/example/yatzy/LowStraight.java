package com.example.yatzy;

import android.content.Context;
import android.widget.TextView;

public class LowStraight extends ScoreCategory {
    LowStraight(TextView t, Context c, Player p){
        text = t;
        player = p;
        storeLocation = player.lowStraight;
        storeLocation.score = 0;
        selected = false;
        storeLocation.scored = false;
        context = c;
    }

    @Override
    public void setScore(Dice[] d) {
        if(!storeLocation.scored){
            // Number of each number on the dice, e.g. [number of 1s, number of 2s, ..., number of 6s]
            int[] nums = new int[] {0, 0, 0, 0, 0, 0};
            for(Dice i: d){
                nums[i.currentValue - 1] ++;
            }
            //All the combinations of a low straight
            boolean lowStraight = false;
            if((nums[0] > 0 && nums[1] > 0 && nums[2] > 0 && nums[3] > 0) || (nums[2] > 0 && nums[3] > 0 && nums[4] > 0 && nums[5] > 0) ||(nums[1] > 0 && nums[2] > 0 && nums[3] > 0 && nums[4] > 0)){
                lowStraight = true;
            }

            if(lowStraight){
                storeLocation.score = 30;
                text.setText(String.valueOf(storeLocation.score));
            }
            else{
                storeLocation.score = 0;
                text.setText("-");
            }
        }
    }
    public ScoreCatNoText getStoreLocation(){
        return player.lowStraight;
    }
}
