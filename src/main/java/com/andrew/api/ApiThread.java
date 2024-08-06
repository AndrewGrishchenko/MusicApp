package com.andrew.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

import com.andrew.App;
import com.andrew.models.AudioFileItem;

public class ApiThread extends Thread {
    private Queue<AudioFileItem> downloadQueue = new LinkedList<AudioFileItem>();
    
    public void downloadSong (AudioFileItem item) {
        downloadQueue.add(item);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            while (!downloadQueue.isEmpty()) {
                AudioFileItem audioFileItem = downloadQueue.remove();

                try {
                    URL downloadLink = new URL(ApiLoader.getDownloadLink(audioFileItem.getAudioLink()));
                    ReadableByteChannel rbc = Channels.newChannel(downloadLink.openStream());
                    FileOutputStream fos = new FileOutputStream(App.getInstance().getMusicPath() + audioFileItem.getAuthor() + "-" + audioFileItem.getName() + ".mp3");
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    fos.close();
                    rbc.close();

                    App.getInstance().refreshDownloadedSongs();

                    JOptionPane.showMessageDialog(App.getInstance().getAppFrame(), 
                        "Песня \"" + audioFileItem.getAuthor() + "-" + audioFileItem.getName() + "\" успешно добавлена!");
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(App.getInstance().getAppFrame(), "Unexpected error");
                    return;
                }
            }
        }
    }
}
