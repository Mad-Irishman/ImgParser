package com.ss.parser.img.service;

import com.ss.ExceptInfoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageProcessingFacadeService {
    private final ValidationService validationService;
    private final FileStorageService fileStorageService;

    @Autowired
    public ImageProcessingFacadeService(ValidationService validationService, FileStorageService fileStorageService) {
        this.validationService = validationService;
        this.fileStorageService = fileStorageService;
    }

    public void processImage(String url, String folderName) throws ExceptInfoUser {
        validationService.validationUrl(url);
        validationService.validationFolder(folderName);
        fileStorageService.createFolder(folderName);
    }
}
