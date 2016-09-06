package com.example.work.d2daudiocommunication;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.ToneGenerator;
import android.os.Handler;

public class SoundGenerator {

    private int duration;
    private int volume;
    private int streamType;

    public SoundGenerator(){
        duration = 1000; // milliseconds
        volume = 50;
        streamType = 0;
    }

    void playSound(final int dtmfTone){


        ToneGenerator dtmfGenerator = new ToneGenerator(streamType, volume);
        dtmfGenerator.startTone(dtmfTone, duration); // all types of tones are available...
        dtmfGenerator.stopTone();

    }

}
