package dev.mzcy.minecraft.client;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WikiApiClient {

    private final String baseUrl;
    private final int timeoutMs;
    private final Gson gson = new Gson();
    // Simple in-memory cache
    private final Map<String, WikiEntry> entryCache = new ConcurrentHashMap<>();

    public WikiApiClient(String baseUrl, int timeoutMs) {
        this.baseUrl = baseUrl;
        this.timeoutMs = timeoutMs;
    }

    public WikiEntry getWikiEntryById(String id) throws Exception {
        // Check cache first
        if (entryCache.containsKey(id)) {
            return entryCache.get(id);
        }

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

        // Cache the entry
        if (entry != null && entry.getId() != null) {
            entryCache.put(entry.getId(), entry);
        }

        return entry;
    }

    /**
     * Fetches a subentry by splitting id by # and traversing sub entries.
     * Example: "main#sub1#sub2"
     */
    public WikiEntry getWikiEntryByPath(String path) throws Exception {
        String[] parts = path.split("#");
        if (parts.length == 0) return null;
        WikiEntry entry = getWikiEntryById(parts[0]);
        for (int i = 1; i < parts.length && entry != null; i++) {
            entry = getSubEntry(entry, parts[i]);
        }
        return entry;
    }

    /**
     * Dummy subentry resolution: assumes subentries are embedded in content as "## SubEntryTitle"
     * In a real API, you would fetch subentries via a dedicated endpoint.
     */
    private WikiEntry getSubEntry(WikiEntry parent, String subTitle) {
        if (parent == null || parent.getContent() == null) return null;
        String marker = "## " + subTitle;
        int idx = parent.getContent().indexOf(marker);
        if (idx == -1) return null;
        int nextIdx = parent.getContent().indexOf("## ", idx + marker.length());
        String subContent = nextIdx == -1
                ? parent.getContent().substring(idx + marker.length()).trim()
                : parent.getContent().substring(idx + marker.length(), nextIdx).trim();
        WikiEntry subEntry = new WikiEntry();
        subEntry.setId(parent.getId() + "#" + subTitle);
        subEntry.setTitle(subTitle);
        subEntry.setContent(subContent);
        return subEntry;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class WikiEntry {
        String id;
        boolean subEntry; // Is this a subentry?
        String parentId; // Only set if this is a subentry
        String title;
        String content;
    }
}
