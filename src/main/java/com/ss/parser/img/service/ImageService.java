package com.ss.parser.img.service;

import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.parser.img.conf.js.ConfJsParser;
import com.ss.parser.img.repository.ImageDownloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ImageService {
    private final ExecutorService executorService;

    public ImageService() {
        this.executorService = Executors.newFixedThreadPool(ConfJsParser.getInstance().getApp().getExecutorPoolSize());
    }

    public List<String> fetchImageUrls(String websiteUrl) throws ExceptInfoUser {
        try {
            List<String> imageUrls = new ArrayList<>();
            Document doc = Jsoup.connect(websiteUrl).get();
            for (Element imgElement : doc.select("img")) {
                String imgUrl = imgElement.absUrl("src");
                if (!imgUrl.isEmpty()) {
                    imageUrls.add(imgUrl);
                }
            }
            return imageUrls;
        } catch (IOException e) {
            throw new ExceptInfoUser(
                    Collections.singletonMap(ConfJs.STATE_ERROR, "Error while getting images from the site: " + websiteUrl),
                    e
            );
        }
    }

    public void downloadImages(List<String> imageUrls, String downloadDir) {
        for (String imageUrl : imageUrls) {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            executorService.execute(new ImageDownloader(imageUrl, downloadDir + "/" + fileName));
        }
    }

    public void processWebsiteImages(String websiteUrl, String downloadDir) throws ExceptInfoUser {
        List<String> imageUrls = fetchImageUrls(websiteUrl);
        if (imageUrls.isEmpty()) {
            throw new ExceptInfoUser(Collections.singletonMap("error", "Unable to retrieve images from the site."));
        }
        downloadImages(imageUrls, downloadDir);
    }
}
