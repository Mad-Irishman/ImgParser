package com.ss.parser.img.repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader implements Runnable {
    private final String imageUrl;
    private final String filePath;

    public ImageDownloader(String imageUrl, String filePath) {
        this.imageUrl = imageUrl;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed to connect to the URL: " + imageUrl);
                return;
            }

            try (InputStream in = url.openStream()) {
                try (FileOutputStream out = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.err.println("Can't write to file: " + filePath);
                }
            } catch (IOException e) {
                System.err.println("Can't download image from URL: " + imageUrl);
            }
        } catch (IOException e) {
            System.err.println("Can't connect to URL or no internet access.");
        }
    }

}
