package com.example.work.d2daudiocommunication;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.locks.ReentrantLock;


public class Receiver extends AppCompatActivity {

    private Button transmitterButton;
    private Button stopButton,recordButton;
    private final int SAMPLE_RATE = 44100;
    private byte[] audioData;
    private int bufferSize, numWrittenBytes;
    private AudioRecord recorder;
    private Thread recordingThread = null;
    private TextView frequencyText;
    private ReentrantLock lock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioData = new byte[bufferSize]; // maybe should be 1024 instead?
        lock = new ReentrantLock();
        frequencyText = (TextView)findViewById(R.id.frequency_text);
        transmitterButton = (Button) this.findViewById(R.id.transmitter_button);
        transmitterButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        stopButton=(Button)findViewById(R.id.pause_button);
        recordButton=(Button)findViewById(R.id.record_button);
        stopButton.setEnabled(false);


        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE_RATE,AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
                    recorder.startRecording();
                    recordingThread = new Thread(new Runnable() {
                        public void run() {
                            processAudio();
                        }
                    }, "AudioRecorder Thread");
                    recordingThread.start();


                } catch (IllegalStateException e) { e.printStackTrace(); }



                recordButton.setEnabled(false);
                stopButton.setEnabled(true);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recorder != null){

                    recorder.stop();
                    recorder.release();
                }
                recordingThread = null;
                stopButton.setEnabled(false);
                recordButton.setEnabled(true);
            }
        });
    }

    private void processAudio() {
        // Write the output audio in byte

        while (recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {

            int x;
            lock.lock();
            numWrittenBytes = recorder.read(audioData, 0, bufferSize);
            lock.unlock();

            for(x=0; x<numWrittenBytes; x++){
                lock.lock();
                System.out.print(audioData[x]);
                lock.unlock();
            }
            System.out.print("\n:::");

        }

    }
}
