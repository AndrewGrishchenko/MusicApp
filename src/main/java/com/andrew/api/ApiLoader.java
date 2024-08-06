package com.andrew.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.andrew.models.AudioFileItem;

public class ApiLoader {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static List<AudioFileItem> getSongs (String search) {
        String body = get("https://ru.drivemusic.me/?do=search&subaction=search&story=" + search);

        List<AudioFileItem> items = new ArrayList<>();
        
        Document doc = Jsoup.parse(body);
        
        Elements audios = doc.getElementsByClass("music-popular-wrapper");

        audios.forEach(audio -> {
            String name = audio.getElementsByClass("popular-play-author").first().ownText();

            String author = audio.getElementsByClass("popular-play-composition").first()
                                 .getElementsByTag("a").first().ownText();

            String duration = audio.getElementsByClass("popular-download-number").first().ownText();

            String audioLink = audio.getElementsByClass("popular-download-link").first().attribute("href").getValue();

            items.add(new AudioFileItem(name, author, duration, audioLink));
        });

        return items;
    }

    public static String get (String query) {
        try {
            URL url = new URL(new URI(query).toASCIIString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return null;
            }
        } catch (IOException | URISyntaxException e) {
            return null;
        }
    }

    public static String getDownloadLink (String audioLink) {
        String body = get("https://ru.drivemusic.me" + audioLink);

        Document doc = Jsoup.parse(body);

        String downloadLink = doc.getElementsByClass("btn-download").first().attribute("href").getValue();
        return downloadLink;
    }
}
