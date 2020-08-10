package com.example.yatzy;

import android.os.Handler;
import android.widget.ImageView;

public class Dice {
    ImageView image;
    private int[] diceimgs = new int[]{R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6};
    boolean selected;
    int currentValue;
    Dice(ImageView im){
        image = im;
        selected = false;
        currentValue = 0;
    }

    public void roll(){
        Handler h = new Handler();
        int interval = 100;
        for(int i = 0; i < 1000; i += interval){
            h.postDelayed(new FlickDiceImage(image), i);
        }
        final int randNum = (int) (Math.random() * 6);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                image.setImageResource(diceimgs[randNum]);
            }
        }, 1000);
        currentValue = randNum + 1;
    }
    public void rollNoFlick(){
        new FlickDiceImage(image).run();
    }
}
