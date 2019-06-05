package com.onarandombox.MultiverseCore.utils.webpaste;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class GithubPasteService implements PasteService {

    private final boolean isPrivate;

    public GithubPasteService(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public String encodeData(String data) {
        Map<String, String> mapData = new HashMap<>();
        mapData.put("multiverse.txt", data);
        return this.encodeData(mapData);
    }

    @Override
    public String encodeData(Map<String, String> files) {
        JsonObject root = new JsonObject();
        root.add("description", new JsonPrimitive("Multiverse-Core Debug Info"));
        root.add("public", new JsonPrimitive(!this.isPrivate));
        JsonObject fileList = new JsonObject();
        for (Map.Entry<String, String> entry : files.entrySet()) {
            JsonObject fileObject = new JsonObject();
            fileObject.add("content", new JsonPrimitive(entry.getValue()));
            fileList.add(entry.getKey(), fileObject);
        }
        root.add("files", fileList);
        return root.toString();
    }

    @Override
    public URL getPostURL() {
        try {
            return new URL("https://api.github.com/gists");
            //return new URL("http://jsonplaceholder.typicode.com/posts");
        } catch (MalformedURLException e) {
            return null; // should never hit here
        }
    }

    @Override
    public String postData(String encodedData, URL url) throws PasteFailedException {
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        try {
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(encodedData);
            wr.flush();

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String pastieUrl = "";
            //Pattern pastiePattern = this.getURLMatchingPattern();
            StringBuilder responseString = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                responseString.append(line);
            }
            return new JsonParser().parse(responseString.toString()).getAsJsonObject().get("html_url").getAsString();
        } catch (Exception e) {
            throw new PasteFailedException(e);
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException ignore) {
                }
            }
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    @Override
    public boolean supportsMultiFile() {
        return true;
    }
}
