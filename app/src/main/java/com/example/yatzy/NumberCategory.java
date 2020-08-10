package com.example.yatzy;

import android.content.Context;
import android.widget.TextView;

public class NumberCategory extends ScoreCategory {
    public int number;
    NumberCategory(TextView t, int n, Context c, Player p){
        text = t;
        player = p;
        storeLocation = player.numbers[n-1];
        storeLocation.score = 0;
        storeLocation.scored = false;
        number = n;
        selected = false;
        context = c;
    }

    public ScoreCatNoText getStoreLocation(){
        return player.numbers[number-1];
    }

    @Override
    public void setScore(Dice[] d) {
        if(!storeLocation.scored){
            storeLocation.score = 0;
            for (Dice i:d) {
                if(i.currentValue == number){
                    storeLocation.score += number;
                }
            }
            if(storeLocation.score == 0){
                text.setText("-");
            }
            else{
                text.setText(String.valueOf(storeLocation.score));
            }
        }
    }



}
