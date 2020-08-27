package com.example.testyourmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Random random = new Random();

    private final long LAG = 300;  //0.3 seconds for lag
    private final long TIME_OF_GAME_LONG = 30000 + LAG; //30
    private final int GAME_ACCURACY_INT = 12;

    private int gameRangeInt;
    private int answerLocationInt;
    private int score;
    private int numberOfRounds;
    private int secondsRemained = 0;

    private String gameChoiceString;
    private String gameRangeString;

    private TextView timerTextView;
    private TextView scoreTextView;
    private TextView mathPromptTextView;
    private TextView resultTextView;

    private Button pauseButton;

    private boolean oneOperationGame;
    private boolean gamePaused = false;

    private final String CORRECT_PROMPT = "Correct";
    private final String INCORRECT_PROMPT = "Incorrect";

    private CountDownTimer timer;

    private ArrayList<Integer> buttonPromptsIntegers = new ArrayList<>();
    private Button[] promptButtons = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        gameChoiceString = getIntent().getStringExtra("game choice");
        gameRangeString = getIntent().getStringExtra("game range");
        gameRangeInt = Integer.valueOf(gameRangeString);

        mathPromptTextView = findViewById(R.id.textView_math_prompt);
        resultTextView = findViewById(R.id.textView_result);
        scoreTextView = findViewById(R.id.textView_score);
        timerTextView = findViewById(R.id.textView_timer);

        pauseButton = findViewById(R.id.button_pause);

        promptButtons[0] = findViewById(R.id.button_00);
        promptButtons[1] = findViewById(R.id.button_01);
        promptButtons[2] = findViewById(R.id.button_10);
        promptButtons[3] = findViewById(R.id.button_11);

        if (gameChoiceString.equals("Both")) {
            oneOperationGame = false;
        } else {
            oneOperationGame = true;
        }

        newPrompt();

        timerControl(TIME_OF_GAME_LONG);

    }

    private void timerControl(long timeRemained) {
        timer = new CountDownTimer(timeRemained, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timerText;
                if ((millisUntilFinished / 1000) < 10) {
                    timerText = "0" + (millisUntilFinished / 1000) + "/ 30";
                } else {
                    timerText = (millisUntilFinished / 1000) + "/ 30";
                }
                timerTextView.setText(timerText);
            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(GameActivity.this, EndGameActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("numberOfRounds", numberOfRounds);
                startActivity(intent);
            }
        }.start();
    }

    public void pauseGame(View view) {

        if (!gamePaused) {
            gamePaused = true;
            pauseButton.setText("Continue");
            timer.cancel();
            //timerText to get the timeRemained
            //timerText.string can 0-30 -> 2digits
            // <9 "0"0 >10 "00"

            secondsRemained = getSecondsRemained();

            resultTextView.setVisibility(View.INVISIBLE);
            mathPromptTextView.setVisibility(View.INVISIBLE);
            for (Button button : promptButtons) {
                button.setVisibility(View.INVISIBLE);
            }

        } else {
            gamePaused = false;
            pauseButton.setText("Pause");
            long timeRemained = (((long) secondsRemained) * 1000) + 300;
            timerControl(timeRemained);

            resultTextView.setVisibility(View.VISIBLE);
            mathPromptTextView.setVisibility(View.VISIBLE);
            for (Button button : promptButtons) {
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    private int getSecondsRemained() {

        int secondsRemained = 0;

        String timerText = timerTextView.getText().toString();

        if (timerText.compareTo("10") < 0) {
            secondsRemained = Integer.parseInt(timerText.substring(1, 2));
        } else {
            secondsRemained = Integer.parseInt(timerText.substring(0, 2));
        }

        return secondsRemained;
    }

    public void restartGame(View view) {
        pauseGame(view);
        Intent intent = new Intent(GameActivity.this, RestartPopupActivity.class);
        int secondsRemained = getSecondsRemained();

        intent.putExtra("secondsRemained", secondsRemained);
        startActivity(intent);
    }

    public void chooseAnswer(View view) {
        Button buttonPressed = (Button) view;

        if (Integer.toString(answerLocationInt).equals(buttonPressed.getTag().toString())) {

            resultTextView.setText(CORRECT_PROMPT);
            score++;

        } else {
            resultTextView.setText(INCORRECT_PROMPT);
        }
        numberOfRounds++;
        scoreTextView.setText(score + " / " + numberOfRounds);

        newPrompt();
    }

    private void newPrompt() {
        buttonPromptsIntegers.clear();
        int first = random.nextInt(gameRangeInt) + 1;
        int second = random.nextInt(gameRangeInt) + 1;

        char operation = getOperation();

        mathPromptTextView.setText(getMathSign(first, second, operation));

        answerLocationInt = random.nextInt(4);
        int answer = getResult(first, second, operation);
        for (int i = 0; i < 4; i++) {
            if (i == answerLocationInt) {
                buttonPromptsIntegers.add(answer);
            } else {
                int wrongAnswer = getWrongAnswer(first, second, operation, answer);

                while (wrongAnswer == answer)
                    wrongAnswer = getWrongAnswer(first, second, operation, answer);

                buttonPromptsIntegers.add(wrongAnswer);
            }
        }

        for (int i = 0; i < 4; i++)
            promptButtons[i].setText(Integer.toString(buttonPromptsIntegers.get(i)));


    }

    private int getWrongAnswer(int first, int second, char operation, int answer) {
        int lower, higher;
        if (first <= second) {
            lower = first;
            higher = second;
        } else {
            lower = second;
            higher = first;
        }

        lower -= GAME_ACCURACY_INT;
        higher += GAME_ACCURACY_INT;
        return random.nextInt(higher - lower) + lower;
    }

    private int getResult(int first, int second, char operation) {
        return (operation == '+') ? first + second : first - second;
    }

    private String getMathSign(int first, int second, char mathSign) {
        String mathPrompt;
        if (mathSign == '+')
            mathPrompt = first + " + " + second;
        else
            mathPrompt = first + " - " + second;
        return mathPrompt;
    }

    private char getOperation() {
        char operation;
        if (oneOperationGame) {
            if (gameChoiceString.equals("Addition"))
                operation = '+';
            else
                operation = '-';
        } else {
            int randomChooseSign = random.nextInt(2);
            if (randomChooseSign == 0)
                operation = '+';
            else
                operation = '-';
        }
        return operation;
    }
}