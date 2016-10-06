package com.example.work.d2daudiocommunication;

import android.media.ToneGenerator;

public class SoundGenerator {

    private int duration;
    private int volume;
    private int streamType;

    public SoundGenerator(){
        duration = 1000; // milliseconds
        volume = 50;
        streamType = 0;
    }

    void playSound(int dtmfTone){

        ToneGenerator dtmfGenerator = new ToneGenerator(streamType, volume);
        dtmfGenerator.startTone(dtmfTone, duration); // all types of tones are available...
        try{
            Thread.sleep(duration);
        } catch (InterruptedException e){}
        dtmfGenerator.stopTone();

    }

}
