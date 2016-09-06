package com.example.work.d2daudiocommunication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SoundGenerator sg;
    Handler handler;

    private Button  oneButton, twoButton, threeButton,
                    fourButton, fiveButton, sixButton,
                    sevenButton, eightButton, nineButton;
    private int freqX1 = 1209, freqX2 = 1336, freqX3 = 1477;
    private int freqY1 = 697, freqY2 = 770, freqY3 = 852;

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

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX1, freqY1);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        twoButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX2, freqY1);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });


        threeButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX3, freqY1);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        fourButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX1, freqY2);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        fiveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX2, freqY2);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        sixButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX3, freqY2);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        sevenButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX1, freqY3);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        eightButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX2, freqY3);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        nineButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone(freqX3, freqY3);
                        handler.post(new Runnable() {

                            public void run() {
                                sg.playSound();
                            }
                        });
                    }
                });
                thread.start();
            }
        });
    }
}
