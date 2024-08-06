package com.andrew;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FileUtils;

import com.andrew.api.ApiLoader;
import com.andrew.api.ApiThread;
import com.andrew.audio.AudioThread;
import com.andrew.audio.Mp3Utils;
import com.andrew.gui.AppFrame;
import com.andrew.models.AudioFileItem;

public class App {
    private AppFrame appFrame = new AppFrame();
    private String musicPath = "";
    private Set<File> musicFiles;

    private AudioThread audioThread = new AudioThread();
    private ApiThread apiThread = new ApiThread();

    public void launch () {
        try {
            Mp3Utils.init();
            audioThread.start();
            apiThread.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(appFrame, "ffmpeg не найден!", "ffmpeg", JOptionPane.CANCEL_OPTION);
            System.exit(1);
        }
        
        appFrame.setSize(900, 700);
        appFrame.setLocationRelativeTo(null);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setVisible(true);

        appFrame.getChooseDirectoryButton().addActionListener(e -> chooseDirectory(e));
        appFrame.getPlayPauseButton().addActionListener(e -> playPauseButton(e));
        
        appFrame.getDownloadedSongsList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                appFrame.getPlayPauseButton().setText(">");
            };
        });

        appFrame.getSearchButton().addActionListener(e -> searchButtonClicked(e));
        appFrame.getdownloadButton().addActionListener(e -> downloadButtonClicked(e));
        appFrame.getDeleteButton().addActionListener(e -> deleteButtonClicked(e));

        appFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				audioThread.interrupt();
                apiThread.interrupt();

                if (musicPath != "") {
                    try {
                        FileUtils.deleteDirectory(Paths.get(musicPath, "tmp").toFile());
                    } catch (IOException exc) { }
                }

				e.getWindow().dispose();
			}
		});
    }

    private void deleteButtonClicked (ActionEvent e) {
        if (musicPath == "") {
            JOptionPane.showMessageDialog(appFrame, "Не выбрана директория сохранения!");
            return;
        }
        if (appFrame.getDownloadedSongsList().getSelectedValue() == null) {
            JOptionPane.showMessageDialog(appFrame, "Не выбрана песня!");
            return;
        }

        AudioFileItem audioFileItem = appFrame.getDownloadedSongsList().getSelectedValue();

        int sureAnswer = JOptionPane.showConfirmDialog(appFrame, 
            "Вы уверены что хотите удалить \"" + audioFileItem.getAuthor() + "-" + audioFileItem.getName() + "\"?",
            "Qusetion", JOptionPane.YES_NO_OPTION);
        if (sureAnswer == 1) return;

        File file = musicFiles.stream()
        .filter(f -> f.getName().equals(audioFileItem.getFileName()))
        .findAny()
        .orElse(null);

        if (file == null) {
            JOptionPane.showMessageDialog(appFrame, "Unexpected error");
            return;
        }

        if (file.delete()) {
            JOptionPane.showMessageDialog(appFrame, "Песня была удалена!");
            refreshDownloadedSongs();
        } else {
            JOptionPane.showMessageDialog(appFrame, "Unexpected error");
        }
    }

    private void downloadButtonClicked (ActionEvent e) {
        if (musicPath == "") {
            JOptionPane.showMessageDialog(appFrame, "Не выбрана директория сохранения!");
            return;
        }
        if (appFrame.getSearchSongsList().getSelectedValue() == null) {
            JOptionPane.showMessageDialog(appFrame, "Не выбрана песня!");
            return;
        }

        AudioFileItem audioFileItem = appFrame.getSearchSongsList().getSelectedValue();

        int sureAnswer = JOptionPane.showConfirmDialog(appFrame, 
            "Вы уверены, что хотите скачать \"" + audioFileItem.getAuthor() + "-" + audioFileItem.getName() + "\"?",
            "Question", JOptionPane.YES_NO_OPTION);
        if (sureAnswer == 1) return;

        apiThread.downloadSong(audioFileItem);
    }

    private void searchButtonClicked (ActionEvent e) {
        String search = appFrame.getSearchTextField().getText();
        if (search == "") {
            JOptionPane.showMessageDialog(appFrame, "Поле поиска пустое!");
            return;
        }

        List<AudioFileItem> items = ApiLoader.getSongs(search);
        
        if (items.size() == 0) {
            JOptionPane.showMessageDialog(appFrame, "Ни одна песня не найдена!");
        }

        Vector<AudioFileItem> list = new Vector<>();
        items.forEach(item -> {
            list.add(item);
        });

        appFrame.getSearchSongsList().setListData(list);
    }

    private void playPauseButton(ActionEvent e) {        
        audioThread.stopPlaying();
        
        if(appFrame.getPlayPauseButton().getText().equals("||")) {
            appFrame.getPlayPauseButton().setText(">");
            return;
        }

        appFrame.getPlayPauseButton().setText("||");
        
        AudioFileItem item = appFrame.getDownloadedSongsList().getSelectedValue();

        if (item == null) {
            JOptionPane.showMessageDialog(null, "Не выбрана песня!");
            return;
        }
        
        File file = musicFiles.stream()
        .filter(f -> f.getName().equals(item.getFileName()))
        .findAny()
        .orElse(null);

        audioThread.setAudio(file);
    }

    private void chooseDirectory (ActionEvent e) {
        //TODO: prettier directory chooser
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int option = fileChooser.showOpenDialog(appFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                musicPath = selectedDirectory.getPath() + "/";
                
                refreshDownloadedSongs();
            } else {
                musicPath = "";
            }

            appFrame.getCurrentDirectoryLabel().setText("Текущая директория: " + musicPath);
        }
        catch (ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (InstantiationException e1)
        {
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1)
        {
            e1.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e1)
        {
            e1.printStackTrace();
        }
    }

    public void refreshDownloadedSongs () {
        musicFiles = Stream.of(new File(musicPath).listFiles())
        .filter(file -> !file.isDirectory())
        .filter(file -> file.getName().endsWith(".mp3"))
        .collect(Collectors.toSet());

        Vector<AudioFileItem> list = new Vector<>();
        musicFiles.forEach(file -> {
            list.add(new AudioFileItem(file.getName(), Mp3Utils.getDuration(file)));
        });

        appFrame.getDownloadedSongsList().setListData(list);
    }

    public static App appInstance;
    public static void main(String[] args) {
        appInstance = new App();
        appInstance.launch();
    }

    public static App getInstance() {
        return appInstance;
    }

    public JFrame getAppFrame() {
        return appFrame;
    }

    public String getMusicPath() {
        return musicPath;
    }
}