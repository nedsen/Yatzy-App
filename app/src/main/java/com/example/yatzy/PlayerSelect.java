package com.example.yatzy;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayerSelect extends AppCompatActivity {


    Button playerButton1;
    Button playerButton2;
    Button playerButton3;
    Button playerButton4;
    Button playerButton5;
    Button playerButton6;
    Button playerButton7;
    Button playerButton8;

    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        playerButton1 = (Button) findViewById(R.id.playerButton1);
        playerButton2 = (Button) findViewById(R.id.playerButton2);
        playerButton3 = (Button) findViewById(R.id.playerButton3);
        playerButton4 = (Button) findViewById(R.id.playerButton4);
        playerButton5 = (Button) findViewById(R.id.playerButton5);
        playerButton6 = (Button) findViewById(R.id.playerButton6);
        playerButton7 = (Button) findViewById(R.id.playerButton7);
        playerButton8 = (Button) findViewById(R.id.playerButton8);

        playerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 1);
            }
        });
        playerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 2);
            }
        });
        playerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 3);
            }
        });
        playerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 4);
            }
        });
        playerButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 5);
            }
        });
        playerButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 6);
            }
        });
        playerButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 7);
            }
        });
        playerButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(PlayerName.class, 8);
            }
        });

        //Close when the next activity tells it to
        h = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.what) {
                    case 0:
                        finish();
                        break;
                }
            }
        };


    }

    private void launchActivity(Class c, int numPlayers) {

        Intent intent = new Intent(this, c);
        intent.putExtra("numPlayers", numPlayers);

        startActivity(intent);
    }

}
