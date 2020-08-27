package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);


        TextView resultTextView = findViewById(R.id.textView_endgame_result);

        Intent intent = getIntent();

        int score = intent.getIntExtra("score", 0);
        int numberOfRounds = intent.getIntExtra("numberOfRounds", 0);

        resultTextView.setText("You answered " + score + " out of " + numberOfRounds + " questions");

        MediaPlayer mp = MediaPlayer.create(EndGameActivity.this, R.raw.endgame);
        mp.start();
    }

    public void restart(View view) {
        Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
        startActivity(intent);
    }

}