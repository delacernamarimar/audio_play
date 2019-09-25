package com.example.audioplay;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRouter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

public class MainActivity extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener  {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextToSpeech tts;
    EditText et_text_to_speech;
    Button btn_play_audio;
    Switch sw_play_voice_during_calls, sw_play_over_bluetooth;
    private AudioManager audioM;
    BluetoothAdapter btAdapter;
    BluetoothManager bMgr = null;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_text_to_speech = findViewById(R.id.et_text_to_speech);
        btn_play_audio = findViewById(R.id.btn_play_audio);
        sw_play_voice_during_calls = findViewById(R.id.sw_play_voice_during_calls);
        sw_play_over_bluetooth = findViewById(R.id.sw_play_over_bluetooth);
        initApp();
    }

    private void initApp() {

        audioM = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        audioM.setMode(AudioManager.STREAM_MUSIC);
        audioM.setSpeakerphoneOn(true);
        et_text_to_speech.setText("This method should only be used by applications that replace the platform-wide management of audio settings or the main telephony application." +
                "This method should only be used by applications that replace the platform-wide management of audio settings or the main telephony application." +
                "This method should only be used by applications that replace the platform-wide management of audio settings or the main telephony application." +
                "This method should only be used by applications that replace the platform-wide management of audio settings or the main telephony application.");
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
//                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
                    speakPhrase(toSpeak);
                } else {
                    speakPhrase(toSpeak);
//                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
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
                    audioM.setMode(AudioManager.MODE_NORMAL);
                } else {
//                    audioM.setMode(AudioManager.STREAM_MUSIC);
//                    audioM.setSpeakerphoneOn(true);

                    audioM.setMode(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE);
                    audioM.setMode(audioM.STREAM_MUSIC);
                    audioM.setSpeakerphoneOn(true);

                }
            }
        });
    }

    protected void onResume() {
        initApp();
        super.onResume();
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

    private void speakPhrase(final String phrase) {
//        // bail if no tts
//        if (!isTtsReady)
//            return;
//        final long millis = System.currentTimeMillis();
//        // bail if in tts blackout
//        if (millis - lastPhraseMillis < TTS_MIN_SILENCE_MILLIS) {
//            return;
//        }
//        lastPhraseMillis = millis;
//        // Request audio focus for playback
        int result = audioM.requestAudioFocus(afChangeListener, // Use the music stream.
                AudioManager.STREAM_MUSIC, // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);
        Log.d(TAG, "speakPhrase: result:" + result);
//        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            final HashMap<String, String> params = new HashMap<String, String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, ".75");
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, phrase);
        params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC + "");
//        AudioManager audioManager = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.STREAM_MUSIC);
//        audioManager.setSpeakerphoneOn(true);


//        HashMap<String, String> myHashRender = new HashMap();
//        String wakeUpText = "Are you up yet?";
//        String destFileName = "/sdcard/wakeUps.wav";
//        myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, wakeUpText);
//        tts.synthesizeToFile(wakeUpText, myHashRender, destFileName);

//        MediaPlayer mp = MediaPlayer.create(this,);
//            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mp.start();
//        AudioManager audioManager = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.STREAM_MUSIC);
//        audioManager.setSpeakerphoneOn(true);
        tts.speak(params.get(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID), TextToSpeech.QUEUE_ADD, params);

//        tts.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, null);

//        requestAudioFocus();
//        tts.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, null);
//        override bluetooth with speaker
//        try {
//            String PATH_TO_FILE = "/sdcard/wakeUps.mp3";
//            mediaPlayer = new  MediaPlayer();
//            mediaPlayer.setDataSource(PATH_TO_FILE);
//            mediaPlayer.prepare();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        }

    }
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
                audioM.setMode(audioM.STREAM_MUSIC);
                audioM.setSpeakerphoneOn(true);
                Log.d(TAG, "onAudioFocusChange: AUDIOFOCUS_LOSS_TRANSIENT");

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
                Log.d(TAG, "onAudioFocusChange: AUDIOFOCUS_GAIN");
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                Log.d(TAG, "onAudioFocusChange: AUDIOFOCUS_LOSS");
//                audioM.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
                audioM.abandonAudioFocus(afChangeListener);
                // Stop playback
            }
        }
    };
    @Override
    public void onAudioFocusChange(int focusChange) {

    }



}
