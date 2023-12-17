package com.noole.rps;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import com.juur.rps_room.R;
import com.noole.rps.room.InternalDatabase;
import com.noole.rps.room.Score;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static InternalDatabase database;
    private ConstraintLayout constraintLayout;
    private ImageView humanView, aiView;
    private TextView score, humanName;
    private String humChoice, aiChoice, result, name;
    private int humScore, aiScore = 0;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = Room.databaseBuilder(this, InternalDatabase.class, "database").allowMainThreadQueries().build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.mainView);
        score = findViewById(R.id.txtScore);
        humanName = findViewById(R.id.txtUserName);
        humanView = findViewById(R.id.imgHuman);
        aiView = findViewById(R.id.imgAi);
        random = new Random();

        //Starts with calling an alert that has an edittext so user can enter their name
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText userInput = new EditText(this);
        userInput.requestFocus();
        userInput.setHint(getResources().getString(R.string.name));
        userInput.setSingleLine();
        userInput.setGravity(Gravity.CENTER_HORIZONTAL);
        userInput.setBackgroundResource(android.R.color.transparent);
        userInput.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        linearLayout.addView(userInput);
        alert.setTitle(getResources().getString(R.string.alertTitle))
                .setMessage(getResources().getString(R.string.alertMessage))
                .setView(linearLayout)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    name = userInput.getText().toString().trim();
                    humanName.setText(name);
                })
                /* NB! Standard button click syntax:
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)

                    is replaced with lambda (dialogInterface, i) ->
                    */
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
                    //Users pushes cancel app closes
                    finishAndRemoveTask();
                }).show();
    }

    //Choices actions: starts with registering human choice then deteriming the winner by calculateWin method, assigning the correct image based on choice and then display the score
    public void onRock(View view) {
        humChoice = "rock";
        calculateWin();
        humanView.setImageResource(R.drawable.rock);
        score.setText(String.format("%s%s", name, getResources().getString(R.string.msg, humScore, aiScore)));
    }

    public void onPaper(View view) {
        humChoice = "paper";
        calculateWin();
        humanView.setImageResource(R.drawable.paper);
        score.setText(String.format("%s%s", name, getResources().getString(R.string.msg, humScore, aiScore)));
    }

    public void onScissors(View view) {
        humChoice = "scissors";
        calculateWin();
        humanView.setImageResource(R.drawable.scissors);
        score.setText(String.format("%s%s", name, getResources().getString(R.string.msg, humScore, aiScore)));
    }

    //Method starts by randomly making AI choice then comparing the player choices and determining the winner
    private void calculateWin() {
        int computer = random.nextInt(3);
        if (computer == 0){
            aiChoice = "rock";
            aiView.setImageResource(R.drawable.rock);
        } else if (computer == 1){
            aiChoice = "paper";
            aiView.setImageResource(R.drawable.paper);
        } else if (computer == 2){
            aiChoice = "scissors";
            aiView.setImageResource(R.drawable.scissors);
        }

        if (humChoice.equals("rock") && aiChoice.equals("paper")){
            result = getResources().getString(R.string.loose);
            aiScore++;
        } else if (humChoice.equals("rock") && aiChoice.equals("scissors")){
            result = getResources().getString(R.string.win);
            humScore++;
        } else if (humChoice.equals("paper") && aiChoice.equals("rock")){
            result = getResources().getString(R.string.win);
            humScore++;
        } else if (humChoice.equals("paper") && aiChoice.equals("scissors")){
            result = getResources().getString(R.string.loose);
            aiScore++;
        } else if (humChoice.equals("scissors") && aiChoice.equals("rock")){
            result = getResources().getString(R.string.loose);
            aiScore++;
        } else if (humChoice.equals("scissors") && aiChoice.equals("paper")){
            result = getResources().getString(R.string.win);
            humScore++;
        } else result = getResources().getString(R.string.tie);
        Snackbar.make(constraintLayout,result,Snackbar.LENGTH_LONG).show();
    }

    //By clicking this button game is ended, user score saved to the database and navigated to another activity
    public void onGameEnd(View view) {
        database.scoreDao().insert(new Score(this.name, this.humScore, this.aiScore, new Date()));
        startActivity(new Intent(view.getContext(), ScoreActivity.class));
    }
}