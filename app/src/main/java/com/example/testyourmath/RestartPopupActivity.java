package com.example.testyourmath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class RestartPopupActivity extends AppCompatActivity {

    private int secondsRemained = 0;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.7));

//        AlertDialog
        intent = getIntent();
        secondsRemained = intent.getIntExtra("secondsRemained", 0);
        TextView timeRemainedTextView = findViewById(R.id.textView_timeReminder);
        timeRemainedTextView.setText("You have " + secondsRemained + " seconds left.");

    }

    public void yesButton(View view) {
        intent = new Intent(RestartPopupActivity.this, MainActivity.class);

        startActivity(intent);
    }

    public void noButton(View view) {
        finish();
    }

}