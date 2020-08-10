package com.example.yatzy;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerConfirm extends AppCompatActivity {

    private LinearLayout linearLayout;

    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_confirm);

        Bundle b = getIntent().getExtras();
        final int numPlayers = b.getInt("numPlayers");
        final String[] playerNames = b.getStringArray("playerNames");

        linearLayout = (LinearLayout) findViewById(R.id.confirmPlayersLayout);

        for(String name : playerNames){
            TextView t = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 8);
            t.setLayoutParams(layoutParams);
            t.setText(name);
            t.setTextSize(24);
            t.setTextColor(getResources().getColor(R.color.colorAccent));
            linearLayout.addView(t);
        }


        startGameButton = (Button) findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(DiceActivity.class, numPlayers, playerNames);
            }
        });


    }

    private void launchActivity(Class c, int numPlayers, String[] playerNames) {

        Intent intent = new Intent(this, c);
        intent.putExtra("numPlayers", numPlayers);
        intent.putExtra("playerNames", playerNames);
        startActivity(intent);
        PlayerSelect.h.sendEmptyMessage(0);
        PlayerName.h.sendEmptyMessage(0);
        finish();
    }
}
