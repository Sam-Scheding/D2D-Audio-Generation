package com.example.work.d2daudiocommunication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.ToneGenerator;

public class MainActivity extends AppCompatActivity {

    private SoundGenerator sg;
    private Button receiverButton;
    private Button  oneButton, twoButton, threeButton,
                    fourButton, fiveButton, sixButton,
                    sevenButton, eightButton, nineButton,
                    startButton, stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sg = new SoundGenerator();
        oneButton = (Button) this.findViewById(R.id.one_button);
        twoButton = (Button) this.findViewById(R.id.two_button);
        threeButton = (Button) this.findViewById(R.id.three_button);
        fourButton = (Button) this.findViewById(R.id.four_button);
        fiveButton = (Button) this.findViewById(R.id.five_button);
        sixButton = (Button) this.findViewById(R.id.six_button);
        sevenButton = (Button) this.findViewById(R.id.seven_button);
        eightButton = (Button) this.findViewById(R.id.eight_button);
        nineButton = (Button) this.findViewById(R.id.nine_button);
        startButton = (Button) this.findViewById(R.id.start_button);
        stopButton = (Button) this.findViewById(R.id.stop_button);
        receiverButton = (Button) this.findViewById(R.id.receiver_button);

        receiverButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Receiver.class));
            }
        });


        startButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                System.out.println("Start Transmission");
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                System.out.println("Stop Transmission");
            }
        });

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
