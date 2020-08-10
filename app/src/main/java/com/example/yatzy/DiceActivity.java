package com.example.yatzy;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.TestLooperManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.os.Handler;
import android.widget.TextView;

public class DiceActivity extends AppCompatActivity {

    private Button rollButton;

    private Dice[] dice;

    private Button scoreButton;

    private Button endTurnButton;

    private ScrollView scoresheet;
    //Is scoresheet visible/
    private boolean scoresheetVisible = false;

    private Player[] players;

    private TextView playerTurnText;

    //define all the number categories
    private NumberCategory ones;
    private NumberCategory twos;
    private NumberCategory threes;
    private NumberCategory fours;
    private NumberCategory fives;
    private NumberCategory sixes;

    //define other categories
    private ThreeOfAKind threeOfAKind;
    private FourOfAKind fourOfAKind;
    private FullHouse fullHouse;
    private LowStraight lowStraight;
    private HighStraight highStraight;
    private Yatzy yatzy;
    private Yatzy2 yatzy2;
    private Chance chance;

    private ScoreCategory[] allScoreCategories;

    //TextViews  and int to sum up score
    private TextView topSubText;
    private TextView topBonusText;
    private TextView topTotalText;
    private TextView bottomTotalText;
    private TextView grandTotalText;
    private int topSub;
    private int topBonus;
    private int topTotal;
    private int bottomTotal;
    private int grandTotal;

    //Variable to track the number of rolls made this turn
    private int numRolls;

    //Current Player number
    private int currentPlayer;

    //Current round number
    private int currentRound;

    //Screen Height and Width
    private int screenHeight;
    private int screenWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        //Get number of Players and player names
        Bundle b = getIntent().getExtras();
        final int numPlayers = b.getInt("numPlayers");
        final String[] playerNames = b.getStringArray("playerNames");

        //Debug playerNames string
        String playerNameString = " ";
        for(String i : playerNames){
            playerNameString += i + "  ";
        }

        Log.d("Player Names", playerNameString);

        currentPlayer = 0;
        currentRound = 0;


        //define all the dice and the buttons

        dice = new Dice[]{
                new Dice((ImageView) findViewById(R.id.dice1)),
                new Dice((ImageView) findViewById(R.id.dice2)),
                new Dice((ImageView) findViewById(R.id.dice3)),
                new Dice((ImageView) findViewById(R.id.dice4)),
                new Dice((ImageView) findViewById(R.id.dice5))
        };
        //dice start invisible
        setDice(false);

        scoreButton = (Button) findViewById(R.id.score_button);

        endTurnButton = (Button) findViewById(R.id.end_turn_button);
        setEndTurnButton(false);



        scoresheet = (ScrollView) findViewById(R.id.scoresheet_scroll);
        //set scoresheet size to 3/4 of screen height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        //scoresheet.setLayoutParams
        ConstraintLayout.LayoutParams scrollparams = (ConstraintLayout.LayoutParams) scoresheet.getLayoutParams();
        scrollparams.height = (screenHeight * 3) / 4;
        scoresheet.setLayoutParams(scrollparams);

        rollButton = (Button) findViewById(R.id.rollButton);

        //Define all the players
        players = new Player[numPlayers];
        for(int i = 0; i < numPlayers; i ++){
            players[i] = new Player(playerNames[i]);
        }
        //Set text to player 1's turn
        playerTurnText = (TextView) findViewById(R.id.playerTurnText);
        playerTurnText.setText("It is " + players[currentPlayer].name + "'s Turn");


        //define number categories
        ones = new NumberCategory((TextView) findViewById(R.id.ones_text), 1, this, players[0]);
        twos = new NumberCategory((TextView) findViewById(R.id.twos_text), 2, this, players[0]);
        threes = new NumberCategory((TextView) findViewById(R.id.threes_text), 3, this, players[0]);
        fours = new NumberCategory((TextView) findViewById(R.id.fours_text), 4, this, players[0]);
        fives = new NumberCategory((TextView) findViewById(R.id.fives_text), 5, this, players[0]);
        sixes = new NumberCategory((TextView) findViewById(R.id.sixes_text), 6, this, players[0]);

        //define other categories
        threeOfAKind=  new ThreeOfAKind((TextView) findViewById(R.id.three_kind_text), this, players[0]);
        fourOfAKind=  new FourOfAKind((TextView) findViewById(R.id.four_kind_text), this, players[0]);
        fullHouse = new FullHouse((TextView) findViewById(R.id.full_house_text), this, players[0]);
        lowStraight = new LowStraight((TextView) findViewById(R.id.low_straight_text), this, players[0]);
        highStraight = new HighStraight((TextView) findViewById(R.id.high_straight_text), this, players[0]);
        yatzy2 = new Yatzy2((TextView) findViewById(R.id.yatzy2_text), this, players[0]);
        yatzy = new Yatzy((TextView) findViewById(R.id.yatzy_text), this, yatzy2, players[0]);
        chance = new Chance((TextView) findViewById(R.id.chance_text), this, players[0]);

        //create and array with all the scorecategories
        allScoreCategories = new ScoreCategory[]{ones, twos, threes, fours, fives, sixes, threeOfAKind, fourOfAKind, fullHouse, lowStraight, highStraight, yatzy, yatzy2, chance};

        //TextViews to sum up score
        topSubText = (TextView) findViewById(R.id.subtotal_top_text);
        topBonusText = (TextView) findViewById(R.id.bonus_text);
        topTotalText = (TextView) findViewById(R.id.total_top_text);
        bottomTotalText = (TextView) findViewById(R.id.total_bottom_text);
        grandTotalText = (TextView) findViewById(R.id.total_grand_text);



        //Variable to track the number of rolls made this turn
        numRolls = 0;


        //Define onclicklistener for all score categories and initialise player
        for(final ScoreCategory category : allScoreCategories){
            category.score0();

            category.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(scoresheetVisible && !category.storeLocation.scored && numRolls > 0){
                        for(ScoreCategory desCat : allScoreCategories){
                            desCat.Deselect();
                        }
                        category.Select();
                        setEndTurnButton(true);
                    }
                }
            });
        }


        //Make dice have random values to start with
        /*for(int i = 0; i < dice.length; i++){
            dice[i].rollNoFlick();
        }*/

        //select dice when clicked
        for(int i = 0; i < dice.length; i ++){

            final Dice d = dice[i];
            d.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!scoresheetVisible) {
                        if (d.selected) {
                            d.image.setBackgroundResource(R.drawable.background_white);
                            d.selected = false;
                        } else {
                            d.image.setBackgroundResource(R.drawable.background_yellow);
                            d.selected = true;
                        }
                    }
                }
            });
        }

        //Make selected dice roll when button is clicked
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!scoresheetVisible) {
                    //If this is the first roll
                    if(numRolls == 0){
                        setDice(true);
                        numRolls ++;
                    }
                    //If this is not the first roll
                    else if(numRolls < 3){
                        boolean rolled = false;
                        for (Dice d : dice) {
                            if (d.selected) {
                                d.selected = false;
                                d.roll();
                                d.image.setBackgroundResource(R.drawable.background_white);
                                rolled = true;
                            }
                        }
                        if(rolled){
                            numRolls ++;
                        }
                    }

                    if(numRolls == 3){
                        setRollButton(false);
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showScoreSheet(2000);
                            }
                        }, 1000);
                    }

                    //Set score category values
                    for(ScoreCategory category : allScoreCategories){
                        if(!category.storeLocation.scored){
                            category.setScore(dice);
                            category.Deselect();
                        }
                    }
                }
            }
        });
        endTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ScoreCategory category : allScoreCategories){
                    if(category.selected){
                        category.Score();
                    }
                }
                setEndTurnButton(false);
                setRollButton(true);
                numRolls = 0;
                hideScoreSheet(1000);
                addTotals();

                if(currentPlayer < numPlayers - 1){
                    currentPlayer ++;
                }
                else{
                    currentPlayer = 0;
                    currentRound ++;
                    if(currentRound == 14){
                        launchActivity(EndGame.class, numPlayers, playerNames, players);
                    }
                }


                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(ScoreCategory category : allScoreCategories){
                            category.setPlayer(players[currentPlayer]);
                            if(!category.storeLocation.scored){
                                category.Deselect();
                                category.score0();
                            }
                        }
                        addTotals();
                    }
                }, 1000);



                playerTurnText.setText("It is " + players[currentPlayer].name + "'s Turn");
                setDice(false);
            }
        });
        //Show/hide scoresheet when button is pressed
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scoresheetVisible){
                    hideScoreSheet(2000);

                }
                else{
                    showScoreSheet(2000);
                }
            }
        });
    }

    //Create a dialog to show when back button is pressed
    @Override
    public void onBackPressed(){
        final Dialog backDialog = new Dialog(this);
        backDialog.setContentView(R.layout.back_dialog);
        Button yesButton = (Button) backDialog.findViewById(R.id.backDialogYes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //***Save the game***//

                //Finish activity
                finish();
            }
        });
        Button noButton = (Button) backDialog.findViewById(R.id.backDialogNo);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Finish activity
                finish();
            }
        });
        Button cancelButton = (Button) backDialog.findViewById(R.id.backDialogCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closeDialog
                backDialog.hide();
            }
        });

        backDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        backDialog.show();
    }

    //Show/Hide scoresheet when the user swipes
    private float swipeY1;
    private float swipeY2;
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                swipeY1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                swipeY2 = event.getY();
                if(swipeY2 > swipeY1){
                    hideScoreSheet(2000);
                }
                else if(swipeY2 < swipeY1){
                    showScoreSheet(2000);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    void addTotals(){
        //Calculate all the totals
        topSub = 0;
        for(int i = 0; i < 6; i ++){
            if(allScoreCategories[i].storeLocation.scored){
                topSub += allScoreCategories[i].storeLocation.score;
            }
        }
        if(topSub >= 63){
            topBonus = 35;
        }
        else{
            topBonus = 0;
        }
        topTotal = topSub + topBonus;

        bottomTotal = 0;
        for(int i = 6; i < allScoreCategories.length; i ++){
            if(allScoreCategories[i].storeLocation.scored) {
                bottomTotal += allScoreCategories[i].storeLocation.score;
            }
        }

        grandTotal = topTotal + bottomTotal;

        //write the totals in the textViews
        topSubText.setText(String.valueOf(topSub));
        topBonusText.setText(String.valueOf(topBonus));
        topTotalText.setText(String.valueOf(topTotal));
        bottomTotalText.setText(String.valueOf(bottomTotal));
        grandTotalText.setText(String.valueOf(grandTotal));
    }
    void setEndTurnButton(boolean vis){
        if(vis){
            endTurnButton.setVisibility(View.VISIBLE);
            endTurnButton.setClickable(true);
        }
        else{
            endTurnButton.setVisibility(View.INVISIBLE);
            endTurnButton.setClickable(false);
        }
    }
    void setDice(boolean vis){
        for(Dice d : dice){
            if(vis){
                d.image.setVisibility(View.VISIBLE);
                d.image.setClickable(true);
                d.roll();
                d.image.setBackgroundResource(R.drawable.background_white);
            }
            else{
                d.image.setVisibility(View.INVISIBLE);
                d.image.setClickable(false);
            }
        }
    }
    void setRollButton(boolean clickable){
        rollButton.setClickable(clickable);
        for(Dice d : dice){
            d.image.setClickable(clickable);
        }
        if(clickable){
            rollButton.setBackgroundResource(R.drawable.button_background);
        }
        else {
            rollButton.setBackgroundResource(R.drawable.button_background_grey);
        }
    }

    private boolean scoreButtonClickable = true;

    void hideScoreSheet(int duration) {
        if(scoreButtonClickable){

            //Temporarily make the button unclickable
            scoreButtonClickable = false;



            //moveSheet and button Down
            ObjectAnimator moveButtonDown = ObjectAnimator.ofFloat(scoreButton, "translationY", 0);
            moveButtonDown.setDuration(duration);
            ObjectAnimator moveScoreSheetDown = ObjectAnimator.ofFloat(scoresheet, "translationY", 0);
            moveScoreSheetDown.setDuration(duration);
            moveScoreSheetDown.start();
            moveButtonDown.start();
            scoreButton.setText(R.string.scorebutton_down_text);
            scoresheetVisible = false;

            //Move dice back to their original positions
            ObjectAnimator d1Down = ObjectAnimator.ofFloat(dice[0].image, "translationY", 0);
            ObjectAnimator d1Right = ObjectAnimator.ofFloat(dice[0].image, "translationX", 0);
            ObjectAnimator d1SizeX = ObjectAnimator.ofFloat(dice[0].image, "scaleX", 1);
            ObjectAnimator d1SizeY = ObjectAnimator.ofFloat(dice[0].image, "scaleY", 1);

            ObjectAnimator d2Down = ObjectAnimator.ofFloat(dice[1].image, "translationY", 0);
            ObjectAnimator d2Left = ObjectAnimator.ofFloat(dice[1].image, "translationX", 0);
            ObjectAnimator d2SizeX = ObjectAnimator.ofFloat(dice[1].image, "scaleX", 1);
            ObjectAnimator d2SizeY = ObjectAnimator.ofFloat(dice[1].image, "scaleY", 1);

            ObjectAnimator d3Down = ObjectAnimator.ofFloat(dice[2].image, "translationY", 0);
            ObjectAnimator d3Right = ObjectAnimator.ofFloat(dice[2].image, "translationX", 0);
            ObjectAnimator d3SizeX = ObjectAnimator.ofFloat(dice[2].image, "scaleX", 1);
            ObjectAnimator d3SizeY = ObjectAnimator.ofFloat(dice[2].image, "scaleY", 1);

            ObjectAnimator d4Down = ObjectAnimator.ofFloat(dice[3].image, "translationY", 0);
            ObjectAnimator d4SizeX = ObjectAnimator.ofFloat(dice[3].image, "scaleX", 1);
            ObjectAnimator d4SizeY = ObjectAnimator.ofFloat(dice[3].image, "scaleY", 1);

            ObjectAnimator d5Down = ObjectAnimator.ofFloat(dice[4].image, "translationY", 0);
            ObjectAnimator d5Left = ObjectAnimator.ofFloat(dice[4].image, "translationX", 0);
            ObjectAnimator d5SizeX = ObjectAnimator.ofFloat(dice[4].image, "scaleX", 1);
            ObjectAnimator d5SizeY = ObjectAnimator.ofFloat(dice[4].image, "scaleY", 1);

            d1Down.setDuration(duration);
            d1Right.setDuration(duration);
            d1SizeX.setDuration(duration);
            d1SizeY.setDuration(duration);

            d2Down.setDuration(duration);
            d2Left.setDuration(duration);
            d2SizeX.setDuration(duration);
            d2SizeY.setDuration(duration);

            d3Down.setDuration(duration);
            d3Right.setDuration(duration);
            d3SizeX.setDuration(duration);
            d3SizeY.setDuration(duration);

            d4Down.setDuration(duration);
            d4SizeX.setDuration(duration);
            d4SizeY.setDuration(duration);

            d5Down.setDuration(duration);
            d5Left.setDuration(duration);
            d5SizeX.setDuration(duration);
            d5SizeY.setDuration(duration);


            //Start all animations
            d1Down.start();
            d1Right.start();
            d1SizeX.start();
            d1SizeY.start();

            d2Down.start();
            d2Left.start();
            d2SizeX.start();
            d2SizeY.start();

            d3Down.start();
            d3Right.start();
            d3SizeX.start();
            d3SizeY.start();

            d4Down.start();
            d4SizeX.start();
            d4SizeY.start();

            d5Down.start();
            d5Left.start();
            d5SizeX.start();
            d5SizeY.start();

            setEndTurnButton(false);

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rollButton.setVisibility(View.VISIBLE);
                    playerTurnText.setVisibility(View.VISIBLE);
                }
            }, duration - 300);

            for (Dice d : dice) {
                d.image.setClickable(false);
            }
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (Dice d : dice) {
                        d.image.setClickable(true);
                    }
                }
            }, duration);

            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scoreButtonClickable = true;
                }
            }, duration + 10);
        }
    }
    void showScoreSheet(int duration){
        if(scoreButtonClickable) {

            //make button temporarily unclickable
            scoreButtonClickable = false;
            //Move Sheet and button up
            ObjectAnimator moveButtonUp = ObjectAnimator.ofFloat(scoreButton, "translationY", -(float) scoresheet.getHeight());
            moveButtonUp.setDuration(duration);
            ObjectAnimator moveScoreSheetUp = ObjectAnimator.ofFloat(scoresheet, "translationY", -(float) scoresheet.getHeight());
            moveScoreSheetUp.setDuration(duration);

            //Move Dice up
            ObjectAnimator d1Up = ObjectAnimator.ofFloat(dice[0].image, "translationY", -(float) (screenHeight / 2 - 176));
            ObjectAnimator d1Left = ObjectAnimator.ofFloat(dice[0].image, "translationX", -8);
            ObjectAnimator d1SizeX = ObjectAnimator.ofFloat(dice[0].image, "scaleX", 0.6f);
            ObjectAnimator d1SizeY = ObjectAnimator.ofFloat(dice[0].image, "scaleY", 0.6f);

            ObjectAnimator d2Up = ObjectAnimator.ofFloat(dice[1].image, "translationY", -(float) (screenHeight / 2 - 176));
            ObjectAnimator d2Right = ObjectAnimator.ofFloat(dice[1].image, "translationX", 8);
            ObjectAnimator d2SizeX = ObjectAnimator.ofFloat(dice[1].image, "scaleX", 0.6f);
            ObjectAnimator d2SizeY = ObjectAnimator.ofFloat(dice[1].image, "scaleY", 0.6f);

            ObjectAnimator d3Up = ObjectAnimator.ofFloat(dice[2].image, "translationY", -(float) (screenHeight / 2));
            ObjectAnimator d3Left = ObjectAnimator.ofFloat(dice[2].image, "translationX", -28);
            ObjectAnimator d3SizeX = ObjectAnimator.ofFloat(dice[2].image, "scaleX", 0.6f);
            ObjectAnimator d3SizeY = ObjectAnimator.ofFloat(dice[2].image, "scaleY", 0.6f);

            ObjectAnimator d4Up = ObjectAnimator.ofFloat(dice[3].image, "translationY", -(float) (screenHeight / 2));
            ObjectAnimator d4SizeX = ObjectAnimator.ofFloat(dice[3].image, "scaleX", 0.6f);
            ObjectAnimator d4SizeY = ObjectAnimator.ofFloat(dice[3].image, "scaleY", 0.6f);

            ObjectAnimator d5Up = ObjectAnimator.ofFloat(dice[4].image, "translationY", -(float) (screenHeight / 2));
            ObjectAnimator d5Right = ObjectAnimator.ofFloat(dice[4].image, "translationX", 28);
            ObjectAnimator d5SizeX = ObjectAnimator.ofFloat(dice[4].image, "scaleX", 0.6f);
            ObjectAnimator d5SizeY = ObjectAnimator.ofFloat(dice[4].image, "scaleY", 0.6f);

            d1Up.setDuration(duration);
            d1Left.setDuration(duration);
            d1SizeX.setDuration(duration);
            d1SizeY.setDuration(duration);

            d2Up.setDuration(duration);
            d2Right.setDuration(duration);
            d2SizeX.setDuration(duration);
            d2SizeY.setDuration(duration);

            d3Up.setDuration(duration);
            d3Left.setDuration(duration);
            d3SizeX.setDuration(duration);
            d3SizeY.setDuration(duration);

            d4Up.setDuration(duration);
            d4SizeX.setDuration(duration);
            d4SizeY.setDuration(duration);

            d5Up.setDuration(duration);
            d5Right.setDuration(duration);
            d5SizeX.setDuration(duration);
            d5SizeY.setDuration(duration);


            //Start all animations
            d1Up.start();
            d1Left.start();
            d1SizeX.start();
            d1SizeY.start();

            d2Up.start();
            d2Right.start();
            d2SizeX.start();
            d2SizeY.start();

            d3Up.start();
            d3Left.start();
            d3SizeX.start();
            d3SizeY.start();

            d4Up.start();
            d4SizeX.start();
            d4SizeY.start();

            d5Up.start();
            d5Right.start();
            d5SizeX.start();
            d5SizeY.start();

            moveScoreSheetUp.start();
            moveButtonUp.start();


            scoreButton.setText(R.string.scorebutton_up_text);
            scoresheetVisible = true;
            scoresheet.scrollTo(0, 0);

            playerTurnText.setVisibility(View.INVISIBLE);


            rollButton.setVisibility(View.INVISIBLE);

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scoreButtonClickable = true;
                }
            }, duration + 10);
        }
    }

    private void launchActivity(Class c, int numPlayers, String[] playerNames, Player[] players) {
        Intent intent = new Intent(this, c);
        intent.putExtra("numPlayers", numPlayers);
        intent.putExtra("playerNames", playerNames);

        int[] playerScores = new int[numPlayers];
        for(int i = 0; i < numPlayers; i ++){
            playerScores[i] = players[i].getTotal();
        }
        intent.putExtra("playerScores", playerScores);

        startActivity(intent);
    }
}
