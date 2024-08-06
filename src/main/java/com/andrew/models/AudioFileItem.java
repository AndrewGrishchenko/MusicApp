package com.andrew.models;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AudioFileItem {
    private String name;
    private String duration;
    private String author;
    
    private String fileName;
    private String audioLink; 

    public AudioFileItem (String fileName, String duration) {
        this.fileName = fileName;
        this.duration = duration;

        String[] parts = fileName.split("-");
            
        author = parts[0];
        name = Stream.of(parts).skip(1).collect(Collectors.joining(" "));
    }

    public AudioFileItem (String name, String author, String duration, String audioLink) {
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.audioLink = audioLink;
    }

    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }

    public String getDuration () {
        return duration;
    }

    public void setDuration (String duration) {
        this.duration = duration;
    }

    public String getAuthor () {
        return author;
    }

    public void setAuthor (String author) {
        this.author = author;
    }

    public String getFileName () {
        return fileName;
    }

    public void setFileName (String fileName) {
        this.fileName = fileName;
    }

    public String getAudioLink () {
        return audioLink;
    }

    public void setAudioLink (String audioLink) {
        this.audioLink = audioLink;
    }
}
