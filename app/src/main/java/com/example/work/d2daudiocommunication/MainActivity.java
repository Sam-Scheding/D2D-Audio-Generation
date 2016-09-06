package com.example.work.d2daudiocommunication;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private SoundGenerator sg;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sg = new SoundGenerator();
        handler = new Handler();
        Button oneButton = (Button) this.findViewById(R.id.one_button);
        Button twoButton = (Button) this.findViewById(R.id.two_button);
        Button threeButton = (Button) this.findViewById(R.id.three_button);
        Button fourButton = (Button) this.findViewById(R.id.four_button);
        Button fiveButton = (Button) this.findViewById(R.id.five_button);
        Button sixButton = (Button) this.findViewById(R.id.six_button);
        Button sevenButton = (Button) this.findViewById(R.id.seven_button);
        Button eightButton = (Button) this.findViewById(R.id.eight_button);
        Button nineButton = (Button) this.findViewById(R.id.nine_button);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.one);

        oneButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                AssetFileDescriptor afd = v.getContext().getResources().openRawResourceFd(R.raw.one);
                if (afd == null) return;
                mp.reset();
                try{
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mp.prepare();
                } catch(IOException e){
                    System.out.println("Something went wrong with the asset file descriptor");
                    System.out.println(e.getStackTrace());
                }
                mp.start();
            }
        });

        twoButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                AssetFileDescriptor afd = v.getContext().getResources().openRawResourceFd(R.raw.two);
                if (afd == null) return;
                mp.reset();
                try{
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mp.prepare();
                } catch(IOException e){
                    System.out.println("Something went wrong with the asset file descriptor");
                    System.out.println(e.getStackTrace());
                }
                mp.start();
            }
        });

        threeButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                // Use a new tread as this can take a while
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        sg.genTone();
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
