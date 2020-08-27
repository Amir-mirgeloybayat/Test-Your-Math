package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int SEEKBAR_STEP_INT = 5;
    private final int SEEKBAR_MAX_INT = 10; //SEEKBAR_STEP_INT * SEEKBAR_MAX
    private final int SEEKBAR_MIN_INT = 1;  //SEEKBAR_STEP_INT * SEEKBAR_MIN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView gameChoiceList = findViewById(R.id.listView_game_choice);
        final ArrayList<String> gameChoices = new ArrayList<>();
        gameChoices.add("Addition");
        gameChoices.add("Subtraction");
        gameChoices.add("Both");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, gameChoices);
        gameChoiceList.setAdapter(adapter);

        final TextView gameChoiceTextView = findViewById(R.id.textView_game_choice);

        gameChoiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameChoiceTextView.setText(gameChoices.get(position));
            }
        });


        final SeekBar gameRangeSeekBar = findViewById(R.id.seekBar_game_range);
        gameRangeSeekBar.setMax(SEEKBAR_MAX_INT);
        gameRangeSeekBar.setProgress(SEEKBAR_MIN_INT);
        final TextView gameRangeTextView = findViewById(R.id.textView_game_range);

        gameRangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int gameRangeSeekBarValue;
            int gameRangeValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gameRangeSeekBarValue = SEEKBAR_MIN_INT;
                if (progress > SEEKBAR_MIN_INT)
                    gameRangeSeekBarValue = progress;
                else
                    gameRangeSeekBarValue = SEEKBAR_MIN_INT;


                gameRangeSeekBar.setProgress(gameRangeSeekBarValue);
                gameRangeValue = gameRangeSeekBarValue * SEEKBAR_STEP_INT;
                String gameRangeString;
                if (gameRangeValue < 10)
                    gameRangeString = "0" + gameRangeValue;
                else
                    gameRangeString = Integer.toString(gameRangeValue);
                gameRangeTextView.setText(gameRangeString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button goButton = findViewById(R.id.button_go);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("game range", gameRangeTextView.getText().toString());
                intent.putExtra("game choice", gameChoiceTextView.getText().toString());
                startActivity(intent);
            }
        });
    }
}