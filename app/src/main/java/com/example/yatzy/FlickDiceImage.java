package com.example.yatzy;

import android.widget.ImageView;

public class FlickDiceImage implements Runnable {
    public ImageView image;
    private int[] diceimgs = new int[]{R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6};

    FlickDiceImage (ImageView i){
        image = i;
    }

    @Override
    public void run() {
        int randNum = (int) (Math.random() * 6);
        image.setImageResource(diceimgs[randNum]);
    }
}