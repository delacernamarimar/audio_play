package com.example.audioplay;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextToSpeech tts;
    EditText et_text_to_speech;
    Button btn_play_audio;
    Switch sw_play_voice_during_calls, sw_play_over_bluetooth;
    private AudioManager audioM;
    BluetoothAdapter btAdapter;
    BluetoothManager bMgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();
    }

    private void initApp() {
        et_text_to_speech = findViewById(R.id.et_text_to_speech);
        btn_play_audio = findViewById(R.id.btn_play_audio);
        sw_play_voice_during_calls = findViewById(R.id.sw_play_voice_during_calls);
        sw_play_over_bluetooth = findViewById(R.id.sw_play_over_bluetooth);
        audioM = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioM.setMode(audioM.MODE_IN_COMMUNICATION);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
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
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();

            }
        });

        sw_play_voice_during_calls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                Toast.makeText(MainActivity.this, "Test " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        sw_play_over_bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean on = isChecked;
                if (on) {
                    audioM.setBluetoothScoOn(true);
                    audioM.startBluetoothSco();
                    audioM.setSpeakerphoneOn(false);
                } else {
                    audioM.setBluetoothScoOn(false);
                    audioM.setSpeakerphoneOn(true);
                }
            }
        });
    }

    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        audioM.setMode(AudioManager.MODE_NORMAL);
        audioM.setSpeakerphoneOn(true);
        super.onDestroy();
    }
}
