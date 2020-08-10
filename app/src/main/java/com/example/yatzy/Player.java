package com.example.yatzy;

import android.widget.TextView;

public class Player {
    public String name;

    //define all the number categories
    public ScoreCatNoText[] numbers;

    /*public ScoreCatNoText ones;
    public ScoreCatNoText twos;
    public ScoreCatNoText threes;
    public ScoreCatNoText fours;
    public ScoreCatNoText fives;
    public ScoreCatNoText sixes;*/

    //define other categories
    public ScoreCatNoText threeOfAKind;
    public ScoreCatNoText fourOfAKind;
    public ScoreCatNoText fullHouse;
    public ScoreCatNoText lowStraight;
    public ScoreCatNoText highStraight;
    public ScoreCatNoText yatzy;
    public ScoreCatNoText yatzy2;
    public ScoreCatNoText chance;

    public ScoreCategory[] allScoreCategories;

    //TextViews  and int to sum up score
    public int topSub;
    public int topBonus;
    public int topTotal;
    public int bottomTotal;
    public int grandTotal;

    public Player(String n) {
        name = n;

        //Initialise all ScoreCategory values
        numbers = new ScoreCatNoText[6];
        for(int i = 0; i < 6; i++){
            numbers[i] = new ScoreCatNoText();
        }

        threeOfAKind = new ScoreCatNoText();
        fourOfAKind = new ScoreCatNoText();
        fullHouse = new ScoreCatNoText();
        lowStraight = new ScoreCatNoText();
        highStraight = new ScoreCatNoText();
        yatzy = new ScoreCatNoText();
        yatzy2 = new ScoreCatNoText();
        chance = new ScoreCatNoText();

    }

    public int getTotal(){
        //Calculate all the totals
        topSub = 0;
        for(int i = 0; i < 6; i ++){
            topSub += numbers[i].score;
        }
        if(topSub >= 63){
            topBonus = 35;
        }
        else{
            topBonus = 0;
        }
        topTotal = topSub + topBonus;

        bottomTotal = 0;
        bottomTotal += threeOfAKind.score;
        bottomTotal += fourOfAKind.score;
        bottomTotal += fullHouse.score;
        bottomTotal += lowStraight.score;
        bottomTotal += highStraight.score;
        bottomTotal += yatzy.score;
        bottomTotal += yatzy2.score;
        bottomTotal += chance.score;

        grandTotal = topTotal + bottomTotal;
        return grandTotal;
    }



}
