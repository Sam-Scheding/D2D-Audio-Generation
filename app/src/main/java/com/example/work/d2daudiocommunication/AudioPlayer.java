package com.example.work.d2daudiocommunication;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/**
 * Created by work on 6/10/16.
 */
public class AudioPlayer {


    public static void playSound(byte[] buffer){

        try{
            AudioTrack audioTrack = new  AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, 500000, AudioTrack.MODE_STATIC);
            audioTrack.write(buffer, 0, buffer.length);

        } catch(Throwable t){
            Log.d("Audio","Playback Failed");
        }
    }
}
