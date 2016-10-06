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


public class ReceiverActivity extends AppCompatActivity {

    private Button transmitterButton;
    private Button stopButton,recordButton;
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

        bufferSize = AudioRecord.getMinBufferSize(Constants.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
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
                recordingBool = true;
                recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, Constants.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
                System.out.println("STARTED RECORDING");

                myRecorder.execute();
                recorder.startRecording();
                recordButton.setEnabled(false);
                stopButton.setEnabled(true);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recordingBool = false;
                System.out.println("STOPPED RECORDING");
                if(recorder != null){
                    recorder.stop();
                    recorder.release();
                }

                stopButton.setEnabled(false);
                recordButton.setEnabled(true);
            }
        });
    }

    private int processSignal(byte[] buffer){

        int[] freqs = {697, 770, 852, 1209, 1336, 1477};
        double[] magnitudes;

        magnitudes = getMagnitudesFromBuffer(buffer, freqs);
        int[] dualToneFreqs = getFrequenciesFromMagnitudes(magnitudes, freqs);
        return getKeyFromFrequencies(dualToneFreqs[0], dualToneFreqs[1]);
    }

    private double[] getMagnitudesFromBuffer(byte buffer[], int freqs[]){
        Goertzel g;
        int blockSize = 205, x = 0;
        double[] magnitudes = new double[freqs.length];

        for(int freq : freqs){

            g = new Goertzel(Constants.SAMPLE_RATE, freq, blockSize);
            g.initGoertzel();
            for (int i=0; i<blockSize;i++){
                g.processSample(buffer[i]);
            }
            double[] parts = new double[2];
            parts = g.getRealImag(parts);
            double real = parts[0];
            double imag = parts[1];

            double magnitudeSquared = real*real + imag*imag;
            System.out.println("rel mag^2= " + magnitudeSquared);
            magnitudes[x] = magnitudeSquared;
            g.resetGoertzel();
            x++;
        }

        return magnitudes;
    }

    private static int[] getFrequenciesFromMagnitudes(double magnitudes[], int freqs[]){
        int largestA = 0, largestB = 0;
        int x = 0;
        for(double mag : magnitudes) {

            if(mag <350000){ continue; }// not over threshold, continue
            if(mag > largestA) {
                largestB = largestA;
                largestA = x;
            } else if (x > largestB) {
                largestB = x;
            }
            x++;
        }
        int freq1 = freqs[largestA];
        int freq2 = freqs[largestB];
        return new int[] { freq1, freq2};
    }

    private int getKeyFromFrequencies(int freq1, int freq2){

        int mult = freq1*freq2;
        if(mult == 697*1209){return 1; }
        if(mult == 697*1336){return 2; }
        if(mult == 697*1477){return 3; }
        if(mult == 770*1209){return 4; }
        if(mult == 770*1336){return 5; }
        if(mult == 770*1477){return 6; }
        if(mult == 852*1209){return 7; }
        if(mult == 852*1336){return 8; }
        if(mult == 852*1477){return 9; }
        return -1;
    }


    private class Recorder extends AsyncTask<Void, Double, Void>{


        @Override
        protected Void doInBackground(Void... params) {


            while (recordingBool) {

                recorder.read(audioData, 0, audioData.length);
                try {
                    Thread.sleep(1000);
                    int keyPressed = processSignal(audioData);
                    publishProgress((double)keyPressed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onProgressUpdate(Double... values){
//            System.out.println("Frequency is: " + values[0]);

            String buttonNum = Double.toString(values[0]);
            frequencyText.setText("The " + buttonNum + " was pressed");
        }
    }
}