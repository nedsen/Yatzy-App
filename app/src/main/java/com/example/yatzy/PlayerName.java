package com.example.yatzy;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PlayerName extends AppCompatActivity {

    private EditText nameText;
    private Button okButton;

    private TextView playerTextView;

    private String[] playerNames;

    private int currentPlayerNum;

    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        Bundle b = getIntent().getExtras();
        final int numPlayers = b.getInt("numPlayers");

        currentPlayerNum = 0;

        playerNames = new String[numPlayers];

        nameText = (EditText) findViewById(R.id.nameText);
        okButton = (Button) findViewById(R.id.nameOkButton);
        playerTextView = (TextView) findViewById(R.id.playerTextView);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayerNum < numPlayers) {
                    playerNames[currentPlayerNum] = nameText.getText().toString();
                    if (currentPlayerNum < numPlayers - 1) {
                        currentPlayerNum++;
                        nameText.setText("");
                        playerTextView.setText("Enter Player " + String.valueOf(currentPlayerNum + 1) + "'s Name:");
                    }
                    else {
                        launchActivity(PlayerConfirm.class, numPlayers, playerNames);
                        nameText.setSelected(false);
                    }
                }
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

    private void launchActivity(Class c, int numPlayers, String[] playerNames) {

        Intent intent = new Intent(this, c);
        intent.putExtra("numPlayers", numPlayers);
        intent.putExtra("playerNames", playerNames);
        startActivity(intent);
    }
}
