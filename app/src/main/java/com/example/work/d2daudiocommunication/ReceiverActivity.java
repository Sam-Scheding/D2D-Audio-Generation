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
    private AudioRecord recorder;
    private TextView frequencyText;
    public boolean recordingBool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

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
                int size = AudioRecord.getMinBufferSize(Constants.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
                recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, Constants.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, size);
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



    private double[] getMagnitudesFromBuffer(byte buffer[]){

        Goertzel g;
        double[] magnitudes = new double[Constants.DTMF_FREQUENCIES.length];
        int x = 0;
        double[] parts = new double[2];
        double real, imag, magnitudeSquared, magnitude;
        for(int freq : Constants.DTMF_FREQUENCIES){
            g = new Goertzel(Constants.SAMPLE_RATE, freq, Constants.BLOCK_SIZE);

            for(int index = 0; index < Constants.BLOCK_SIZE; index++){
                g.processSample(buffer[index]);
            }

        	/* Do the "basic Goertzel" processing. */
            parts = g.getRealImag(parts);
            real = parts[0];
            imag = parts[1];
            g.resetGoertzel();

            magnitudeSquared = real*real + imag*imag;
            magnitude = Math.sqrt(magnitudeSquared);
            magnitudes[x] = magnitude;
            System.out.println("Freq: " + freq + " Magnitude: " + magnitudes[x] + "->" + x);
            x++;
        }

        return magnitudes;
    }

    private static int[] getTwoLoudestFrequenciesFromMagnitudes(double magnitudes[]){
        double highest = Double.MIN_VALUE;
        int highestPos = 0;
        int freq1 = 0, freq2 = 0;

        for (int i = 0; i < magnitudes.length/2; i++){ // get largest vertical magnitude
            if (magnitudes[i] > highest){
                highest = magnitudes[i];
                highestPos = i;
            }
        }
        System.out.println("Largest element is at        "+ highestPos);
        if(highest > Constants.THRESHOLD){ freq1 = Constants.DTMF_FREQUENCIES[highestPos]; }
        highestPos = 0;
        highest = Double.MIN_VALUE;
        for (int i = magnitudes.length/2; i < magnitudes.length; i++){ // get largest horizontal magnitude
            if (magnitudes[i] > highest){
                highest = magnitudes[i];
                highestPos = i;
            }
        }
        System.out.println("Second largest element is at "+ highestPos);
        if(highest> Constants.THRESHOLD){ freq2 = Constants.DTMF_FREQUENCIES[highestPos]; }


        System.out.println("Choosing "+ freq1 + " as the first  frequency");
        System.out.println("Choosing "+ freq2 + " as the second frequency");

        return new int[] {freq1, freq2};
    }

    private int getKeyFromFrequencies(int freq1, int freq2){

        // utilises the fact that the product of any two dtmf frequencies is unique
        int freqProduct = freq1*freq2;
        if(freqProduct == Constants.DTMF1_PRODUCT){ return 1; }
        if(freqProduct == Constants.DTMF2_PRODUCT){ return 2; }
        if(freqProduct == Constants.DTMF3_PRODUCT){ return 3; }
        if(freqProduct == Constants.DTMF4_PRODUCT){ return 4; }
        if(freqProduct == Constants.DTMF5_PRODUCT){ return 5; }
        if(freqProduct == Constants.DTMF6_PRODUCT){ return 6; }
        if(freqProduct == Constants.DTMF7_PRODUCT){ return 7; }
        if(freqProduct == Constants.DTMF8_PRODUCT){ return 8; }
        if(freqProduct == Constants.DTMF9_PRODUCT){ return 9; }
        return -1;
    }


    private class Recorder extends AsyncTask<Void, Double, Void>{

        private byte[] audioData;
        private int bufferSize;

        @Override
        protected Void doInBackground(Void... params) {

            bufferSize = AudioRecord.getMinBufferSize(Constants.SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            audioData = new byte[bufferSize]; // maybe should be 1024 instead?


            while (recordingBool) {

                recorder.read(audioData, 0, bufferSize);
                try {
                    Thread.sleep(50);

                    double[] magnitudes = getMagnitudesFromBuffer(audioData);
                    int[] dualToneFreqs = getTwoLoudestFrequenciesFromMagnitudes(magnitudes);
                    int keyPressed =  getKeyFromFrequencies(dualToneFreqs[0], dualToneFreqs[1]);

                    publishProgress((double)keyPressed);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onProgressUpdate(Double... values){
//            System.out.println("Frequency is: " + values[0]);

            double dButtonNum = Double.valueOf(values[0]);
            int buttonNum = (int)dButtonNum;
            if(buttonNum == -1){
                frequencyText.setText("No tone detected.");
            } else {
                frequencyText.setText("The " + buttonNum + " was pressed");
            }
        }
    }
}