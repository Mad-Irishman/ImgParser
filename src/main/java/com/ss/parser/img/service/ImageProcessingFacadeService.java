package com.ss.parser.img.service;

import com.ss.ExceptInfoUser;
import com.ss.parser.img.conf.js.ConfJsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageProcessingFacadeService {
    private final ValidationService validationService;
    private final FileStorageService fileStorageService;
    private final ImageService imageService;

    @Autowired
    public ImageProcessingFacadeService(ValidationService validationService, FileStorageService fileStorageService, ImageService imageService) {
        this.validationService = validationService;
        this.fileStorageService = fileStorageService;
        this.imageService = imageService;
    }

    public void processImage(String url, String folderName) throws ExceptInfoUser {
        validationService.validationUrl(url);
        validationService.validationFolder(folderName);
        fileStorageService.createFolder(folderName);
        imageService.processWebsiteImages(url, ConfJsParser.getInstance().getApp().getDownloadPath() + folderName);
    }
}
