package com.example.audioplay;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    TextToSpeech tts;
    EditText et_text_to_speech;
    Button btn_play_audio;
    Switch sw_play_over_bluetooth, sw_1, sw_2, sw_3;
    private AudioManager audioM;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();
    }

    private void initApp() {
        et_text_to_speech = findViewById(R.id.et_text_to_speech);
        btn_play_audio = findViewById(R.id.btn_play_audio);
        sw_play_over_bluetooth = findViewById(R.id.sw_play_over_bluetooth);
        sw_1 = findViewById(R.id.sw_1);
        sw_2 = findViewById(R.id.sw_2);
        sw_3 = findViewById(R.id.sw_3);
        sw_play_over_bluetooth.setOnCheckedChangeListener(this);
        sw_1.setOnCheckedChangeListener(this);
        sw_2.setOnCheckedChangeListener(this);
        sw_3.setOnCheckedChangeListener(this);
        audioM = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
//        audioM.setMode(audioM.MODE_IN_COMMUNICATION);
        tts = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        btn_play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = et_text_to_speech.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
                Toast.makeText(getBaseContext(), toSpeak, Toast.LENGTH_SHORT).show();
            }
        });



//        sw_play_over_bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                boolean on = isChecked;
//                Log.d("MainActivity", "onCheckedChanged: " + audioM.getMode());
//                Log.d("MainActivity", "onCheckedChanged: " + Arrays.toString(audioM.getDevices(1)));
//
//
//                if (on) {
//                    audioM.setBluetoothScoOn(true);
//                    audioM.startBluetoothSco();
//                    audioM.setSpeakerphoneOn(false);
//                    audioM.setMode(AudioManager.STREAM_SYSTEM);
//                } else {
//                    audioM.setBluetoothScoOn(false);
//                    audioM.setSpeakerphoneOn(true);
//                }
//            }
//        });
    }
//
//    protected void onPause() {
//        if (tts != null) {
//            tts.stop();
//            tts.shutdown();
//        }
//        super.onPause();
//    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        audioM.setMode(AudioManager.MODE_NORMAL);
        audioM.setSpeakerphoneOn(true);
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.sw_1:
                Log.d("MainActivity", "onCheckedChanged: " + audioM.getMode());
                Log.d("MainActivity", "onCheckedChanged: " + Arrays.toString(audioM.getDevices(1)));


                if (isChecked) {
                    audioM.setBluetoothScoOn(true);
                    audioM.startBluetoothSco();
                    audioM.setSpeakerphoneOn(false);
                } else {
                    audioM.setBluetoothScoOn(false);
                    audioM.setSpeakerphoneOn(true);
                }

                break;
            case R.id.sw_2:
                break;
            case R.id.sw_3:
                if (isChecked) {
                    audioM.setMode(AudioManager.STREAM_MUSIC);
                    audioM.setSpeakerphoneOn(true);
                } else {
                    audioM.setMode(AudioManager.STREAM_VOICE_CALL);
                    audioM.setSpeakerphoneOn(false);
                    audioM.setBluetoothScoOn(true);
                    audioM.startBluetoothSco();
                }
                break;
            case R.id.sw_play_over_bluetooth:
                break;
                default:
        }
    }

}

/* will test
 generateAudioSessionId
getActivePlaybackConfigurations
getDevices

 */