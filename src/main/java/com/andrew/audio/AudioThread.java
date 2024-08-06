package com.andrew.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioThread extends Thread {
    private boolean play = false;
    private File file;
    private Clip clip;

    public void stopPlaying() {
        play = false;
        if (clip != null) {
            clip.stop();
        }
    }

    public void setAudio (File file) {
        stopPlaying();
        this.file = file;
        play = true;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (play) {
                play = false;
                try {
                    File convertedFile = Mp3Utils.convert(file);
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(convertedFile);
        
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) { }
            }
        }
    }
}
