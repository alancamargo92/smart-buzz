package com.ukdev.smartbuzz.backend;

import android.media.AudioManager;

public class AudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {

    private AudioManager manager;
    private int volume;

    public AudioFocusChangeListener(AudioManager manager, int volume) {
        this.manager = manager;
        this.volume = volume;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                manager.setStreamVolume(AudioManager.STREAM_ALARM,
                        1, 0);
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                manager.setStreamVolume(AudioManager.STREAM_ALARM,
                        volume,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                break;
        }
    }

}