package com.brettcarney.app.pressme;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final long INITIAL_START_TIME = 240000;
    private TextView textTimer;
    private Button brewStartPause;
    private Button brewReset;

    private CountDownTimer cdTimer;
    private boolean cdTimerIsRunning;
    private long timeRemaining = INITIAL_START_TIME;

    private RadioButton weakButton;
    private RadioButton mediumButton;
    private RadioButton strongButton;
    private Button brewPress;
    private EditText volume;
    private TextView grams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTimer=(TextView)findViewById(R.id.countdownTimer);

        brewStartPause=(Button)findViewById(R.id.startPauseButton);
        brewStartPause.setOnClickListener(this);

        brewReset=(Button)findViewById(R.id.resetButton);
        brewReset.setOnClickListener(this);

        weakButton=(RadioButton)findViewById(R.id.radioWeak);
        weakButton.setOnClickListener(this);
        weakButton.setSelected(true);
        weakButton.setChecked(true);

        mediumButton=(RadioButton)findViewById(R.id.radioMedium);
        mediumButton.setOnClickListener(this);

        strongButton=(RadioButton)findViewById(R.id.radioStrong);
        strongButton.setOnClickListener(this);

        brewPress=(Button)findViewById(R.id.brewButton);
        brewPress.setOnClickListener(this);

        volume=(EditText)findViewById(R.id.etTest);
        volume.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        grams=(TextView)findViewById(R.id.textView);
        grams.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.radioWeak:
                if(weakButton.isSelected())
                {
                    weakButton.setSelected(false);
                    weakButton.setChecked(false);
                }
                else
                {
                    weakButton.setSelected(true);
                    weakButton.setChecked(true);
                    mediumButton.setSelected(false);
                    mediumButton.setChecked(false);
                    strongButton.setSelected(false);
                    strongButton.setChecked(false);
                }
                break;
            case R.id.radioMedium:
                if(mediumButton.isSelected())
                {
                    mediumButton.setSelected(false);
                    mediumButton.setChecked(false);
                }
                else
                {
                    mediumButton.setSelected(true);
                    mediumButton.setChecked(true);
                    weakButton.setSelected(false);
                    weakButton.setChecked(false);
                    strongButton.setSelected(false);
                    strongButton.setChecked(false);
                }
                break;
            case R.id.radioStrong:
                if(strongButton.isSelected())
                {
                    strongButton.setSelected(false);
                    strongButton.setChecked(false);
                }
                else
                {
                    strongButton.setSelected(true);
                    strongButton.setChecked(true);
                    weakButton.setSelected(false);
                    weakButton.setChecked(false);
                    mediumButton.setSelected(false);
                    mediumButton.setChecked(false);
                }
                break;
            case R.id.brewButton:
                if(weakButton.isSelected())
                {

                    double vol;
                    if(TextUtils.isEmpty(volume.getText().toString()))
                    {
                        vol = 0;
                    }
                    else
                    {
                        vol = Integer.parseInt(volume.getText().toString());
                    }

                    double weight = (vol / 200) * 7;
                    String out = Double.toString(weight);
                    grams.setText(out);
                    grams.append("g");
                }
                else if(mediumButton.isSelected())
                {
                    double vol;
                    if(TextUtils.isEmpty(volume.getText().toString()))
                    {
                        vol = 0;
                    }
                    else
                    {
                        vol = Integer.parseInt(volume.getText().toString());
                    }

                    double weight = (vol / 200) * 7.5;
                    String out = Double.toString(weight);
                    grams.setText(out);
                    grams.append("g");
                }
                else if(strongButton.isSelected())
                {
                    double vol;
                    if(TextUtils.isEmpty(volume.getText().toString()))
                    {
                        vol = 0;
                    }
                    else
                    {
                        vol = Integer.parseInt(volume.getText().toString());
                    }

                    double weight = (vol / 200) * 8;
                    String out = Double.toString(weight);
                    grams.setText(out);
                    grams.append("g");
                }
                break;
            case R.id.startPauseButton:
                if(cdTimerIsRunning)
                {
                    pauseTimer();//Finish later
                }
                else
                {
                    startTimer();//Finish later
                }
                break;
            case R.id.resetButton:
                resetTimer();
                break;
                default:
                    grams.setText("ERROR");
                    break;
        }
        updateCountDownText();
    }

    private void startTimer() {
        cdTimer = new CountDownTimer(INITIAL_START_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                cdTimerIsRunning = false;
                brewStartPause.setText("Start");
                brewStartPause.setVisibility(View.INVISIBLE);
                brewReset.setVisibility(View.VISIBLE);
            }
        }.start();

        cdTimerIsRunning = true;
        brewStartPause.setText("pause");
        brewReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        cdTimer.cancel();
        cdTimerIsRunning = false;
        brewStartPause.setText("Start");
        brewReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeRemaining = INITIAL_START_TIME;
        updateCountDownText();
        brewReset.setVisibility(View.INVISIBLE);
        brewStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeRemaining / 1000) / 60;
        int seconds = (int) (timeRemaining / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textTimer.setText(timeLeftFormatted);
    }
}
