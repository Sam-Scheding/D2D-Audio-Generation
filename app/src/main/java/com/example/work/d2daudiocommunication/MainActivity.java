package com.example.work.d2daudiocommunication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.ToneGenerator;

public class MainActivity extends AppCompatActivity {

    private SoundGenerator sg;
    Handler handler;

    private Button  oneButton, twoButton, threeButton,
                    fourButton, fiveButton, sixButton,
                    sevenButton, eightButton, nineButton;
//    private int freqX1 = 1209, freqX2 = 1336, freqX3 = 1477;
//    private int freqY1 = 697, freqY2 = 770, freqY3 = 852;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sg = new SoundGenerator();
        handler = new Handler();
        oneButton = (Button) this.findViewById(R.id.one_button);
        twoButton = (Button) this.findViewById(R.id.two_button);
        threeButton = (Button) this.findViewById(R.id.three_button);
        fourButton = (Button) this.findViewById(R.id.four_button);
        fiveButton = (Button) this.findViewById(R.id.five_button);
        sixButton = (Button) this.findViewById(R.id.six_button);
        sevenButton = (Button) this.findViewById(R.id.seven_button);
        eightButton = (Button) this.findViewById(R.id.eight_button);
        nineButton = (Button) this.findViewById(R.id.nine_button);
        
        oneButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_1);
            }
        });

        twoButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_2);
            }
        });


        threeButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_3);
            }
        });

        fourButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_4);
            }
        });

        fiveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_5);
            }
        });

        sixButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_6);
            }
        });

        sevenButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_7);
            }
        });

        eightButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_8);
            }
        });

        nineButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                sg.playSound(ToneGenerator.TONE_DTMF_9);
            }
        });
    }
}
