package com.example.work.d2daudiocommunication;

/**
 * Created by work on 6/10/16.
 */
public class Constants {

    public static final int SAMPLE_RATE = 8000;
    public static final int THRESHOLD = 500;
    public static final int BLOCK_SIZE = 105;

    public static final int DTMF_697 = 0;
    public static final int DTMF_770 = 1;
    public static final int DTMF_852 = 2;
    public static final int DTMF_1209 = 3;
    public static final int DTMF_1336 = 4;
    public static final int DTMF_1477 = 5;

    public static final int[] DTMF_FREQUENCIES = {697, 770, 852, 1209, 1336, 1477};


    public static final int DTMF1_PRODUCT = 697*1209;
    public static final int DTMF2_PRODUCT = 697*1336;
    public static final int DTMF3_PRODUCT = 697*1477;
    public static final int DTMF4_PRODUCT = 770*1209;
    public static final int DTMF5_PRODUCT = 770*1336;
    public static final int DTMF6_PRODUCT = 770*1477;
    public static final int DTMF7_PRODUCT = 852*1209;
    public static final int DTMF8_PRODUCT = 852*1336;
    public static final int DTMF9_PRODUCT = 852*1477;


}
