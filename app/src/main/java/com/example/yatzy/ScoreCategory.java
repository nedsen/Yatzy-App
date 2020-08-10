package com.example.yatzy;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;


public abstract class ScoreCategory {
    public TextView text;
    //public int score;
    public abstract void setScore(Dice[] d);
    public abstract ScoreCatNoText getStoreLocation();
    public boolean selected;
    public Context context;
    public Player player;
    public ScoreCatNoText storeLocation;

    public void Select(){
        if(!storeLocation.scored){
            selected = true;
            text.setBackground(context.getResources().getDrawable(R.drawable.table_border_selected));
            text.setTextColor(context.getResources().getColor(R.color.selected_score_color));
        }
    }
    public void Deselect(){
        if(!storeLocation.scored){
            selected = false;
            text.setTextColor(context.getResources().getColor(R.color.unselected_score_color));
            text.setBackground(context.getResources().getDrawable(R.drawable.table_border));
        }
    }
    public void Score(){
        selected = false;
        storeLocation.scored = true;
        text.setBackground(context.getResources().getDrawable(R.drawable.table_border_scored));
    }

    public void setPlayer(Player p){
        player = p;
        storeLocation = getStoreLocation();
        setScoredScore();
    }
    public void setScoredScore(){
        if(storeLocation.scored){
            if(storeLocation.score == 0){
                text.setText("-");
            }
            else {
                text.setText(String.valueOf(storeLocation.score));
            }
            text.setTextColor(context.getResources().getColor(R.color.selected_score_color));
            text.setBackground(context.getResources().getDrawable(R.drawable.table_border_scored));
        }
        else{
            text.setBackground(context.getResources().getDrawable(R.drawable.table_border));
            text.setTextColor(context.getResources().getColor(R.color.unselected_score_color));
        }
    }

    public void score0(){
        storeLocation.score = 0;
        text.setText("-");
        text.setBackground(context.getResources().getDrawable(R.drawable.table_border_scored));
        text.setTextColor(context.getResources().getColor(R.color.scored_score_color));
    }

}
