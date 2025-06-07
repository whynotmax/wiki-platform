package dev.mzcy.minecraft.client;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WikiApiClient {

    private final String baseUrl;
    private final int timeoutMs;
    private final Gson gson = new Gson();

    public WikiApiClient(String baseUrl, int timeoutMs) {
        this.baseUrl = baseUrl;
        this.timeoutMs = timeoutMs;
    }

    public WikiEntry getWikiEntryById(String id) throws Exception {
        String urlString = baseUrl + "/" + id;

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(timeoutMs);
        conn.setReadTimeout(timeoutMs);

        int status = conn.getResponseCode();

        if (status != 200) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        WikiEntry entry = gson.fromJson(reader, WikiEntry.class);
        reader.close();
        conn.disconnect();

        return entry;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class WikiEntry {
        String id;
        String title;
        String content;
    }
}