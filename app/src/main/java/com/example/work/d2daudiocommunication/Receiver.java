package com.example.work.d2daudiocommunication;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class Receiver extends AppCompatActivity {

    private Button transmitterButton;
    private Button stopButton,recordButton;
    private final int SAMPLE_RATE = 44100;
    private byte[] audioData;
    private int bufferSize, numWrittenBytes;
    private AudioRecord recorder;
    private double frequency;
    private TextView frequencyText;
    public boolean recordingBool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioData = new byte[bufferSize]; // maybe should be 1024 instead?
        recordingBool = false;
        frequencyText = (TextView)findViewById(R.id.frequency_text);
        transmitterButton = (Button) this.findViewById(R.id.transmitter_button);
        transmitterButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                recordingBool = false;
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



        stopButton=(Button)findViewById(R.id.pause_button);
        recordButton=(Button)findViewById(R.id.record_button);
        stopButton.setEnabled(false);


        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recorder myRecorder = new Recorder();

                double peak;
                int x = 0;
                recordingBool = true;

                recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
                System.out.println("THIS IS RECORDING NOW");

                myRecorder.execute();

                recordButton.setEnabled(false);
                stopButton.setEnabled(true);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recordingBool = false;
                System.out.println("STOP BUTTON PRESSED");
                if(recorder != null){
                    recorder.stop();
                    recorder.release();
                }
                System.out.println("RECORDER PROB NOT BEING RELEASED?");

                stopButton.setEnabled(false);
                recordButton.setEnabled(true);
            }
        });
    }


    private class Recorder extends AsyncTask<Void, Double, Void>{


        @Override
        protected Void doInBackground(Void... params) {

            double peak = -1.0;
            numWrittenBytes = recorder.read(audioData, 0, bufferSize); //.read() not guaranteed to write the whole buffer
            frequency = (SAMPLE_RATE * peak) / bufferSize;

            while (recordingBool) {

                try {
                    Thread.sleep(1000);
//                    for(int i = 0; i < numWrittenBytes; i++){
//                        // Process magnitude here
//
//                    }
                    System.out.println("Still here for some reason");
                    publishProgress(frequency);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("DOES IT EVER GET HERE?");
            return null;
        }

        protected void onProgressUpdate(Double... values){
            System.out.println("Frequncy is: " + values[0]);

            String frequencyString = Double.toString(values[0]);
            frequencyText.setText("Frequency is: " + frequencyString);
        }
    }
}