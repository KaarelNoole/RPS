package com.noole.rps;

import android.os.Build;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.juur.rps_room.R;
import com.noole.rps.room.Score;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class ScoreActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Score> scores = MainActivity.database.scoreDao().getAll();
        TableLayout table = findViewById(R.id.table);
        for (Score score : scores)
        {
            TableRow row = new TableRow(ScoreActivity.this);
            TextView date = new TextView(ScoreActivity.this);
            TextView name = new TextView(ScoreActivity.this);
            TextView humScore = new TextView(ScoreActivity.this);
            TextView aiScore = new TextView(ScoreActivity.this);

            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            String strDate = dateFormat.format(score.date);
            date.setText(strDate);
            name.setText(score.name);
            humScore.setText(String.valueOf(score.humanScore));
            aiScore.setText(String.valueOf(score.aiScore));

            row.addView(date);
            row.addView(name);
            row.addView(humScore);
            row.addView(aiScore);

            table.addView(row);
        }
    }
}