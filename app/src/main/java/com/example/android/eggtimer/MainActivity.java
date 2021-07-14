package com.example.android.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    Button btCount ;
    ImageView egg;
    TextView textView;
    SeekBar seekBar ;

    // the same concept with the connect 4 game (gameIsActive)
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCount = (Button)findViewById(R.id.button);
        egg = (ImageView)findViewById(R.id.ivEgg);
        textView = (TextView)findViewById(R.id.textView);
        seekBar = (SeekBar)findViewById(R.id.seekBar);

        // for ten mins
        seekBar.setMax(600);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void go(View view){

        if (counterIsActive == false) {

            btCount.setText("Stop");
            seekBar.setEnabled(false);
            counterIsActive = true;

            // adding 100 millis to reduce delay in counter timer

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.dixie_horn);
                    mediaPlayer.start();
                    reset();

                }
            }.start();
        }else {

           reset();
        }
    }


    public void updateTimer (int secondsLeft){

       // int minutes = (int)seekBar.getProgress()/ 60;
       // int seconds = (int )seekBar.getProgress() - minutes *60;
        int minutes = (int)secondsLeft/ 60;
        int seconds = (int )secondsLeft - minutes *60;

        // Instead of the method below
        //String timeFormatted = String.format("%02d:%02d",minutes,seconds);

        String secondString = Integer.toString(seconds);
        if (secondString == "0"){

            secondString = "00";
        }
        /*
        else if (secondsLeft < 10 * seconds){

            secondString = "0:0"+seconds;
        }
        */
        else if (seconds <10){
            secondString = "0"+seconds;
        }
        //Integer.toString(minutes)
        textView.setText(minutes+":"+ (secondString));
    }

    public void reset (){

        textView.setText("0:00");
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        btCount.setText("Go");
        counterIsActive = false;
    }
}
