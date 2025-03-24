package com.ss.parser.img.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        try (InputStream in = new URL(imageUrl).openStream()) {
            try (FileOutputStream out = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while (true) {
                    try {
                        if ((bytesRead = in.read(buffer)) == -1) {
                            break;
                        }
                    } catch (IOException e) {
                        System.err.println("Error while downloading image from URL: " + imageUrl);
                        break;
                    }
                    try {
                        out.write(buffer, 0, bytesRead);
                    } catch (IOException e) {
                        System.err.println("Error while writing to file: " + filePath);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error: Failed to create file for writing: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error: Failed to open stream from URL: " + imageUrl);
        }

    }
}
