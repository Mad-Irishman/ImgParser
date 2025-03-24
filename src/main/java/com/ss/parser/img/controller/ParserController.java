package com.ss.parser.img.controller;

import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.parser.img.service.FileStorageService;
import com.ss.parser.img.service.ValidationService;
import com.ss.parser.img.service.ImageService;
import com.ss.parser.img.validation.ValidationUrlFoleder;
import com.ss.Except4SupportDocumented;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParserController {

    private final ImageService imageService;
    private final FileStorageService fileStorageService;
    private final ValidationService imageProcessingService;

    @Autowired
    public ParserController(ImageService imageService, FileStorageService fileStorageService, ValidationService imageProcessingService) {
        this.imageService = imageService;
        this.fileStorageService = fileStorageService;
        this.imageProcessingService = imageProcessingService;
    }

    @GetMapping(path = "/download/img")
    public String showFormForUrl() {
        return "parserImg";
    }

    @PostMapping(path = "/download/submit")
    public String handleUrlSubmission(@RequestParam String url, @RequestParam String folderName, Model model) {
        try {
            imageProcessingService.validationUrlFolder(url, folderName);
            fileStorageService.createFolder(folderName);
            imageService.processWebsiteImages(url, "downloads/" + folderName);

            return "parserImg";

        } catch (ExceptInfoUser e) {
            model.addAttribute(ConfJs.STATE_ERROR, e.getErrorMessage());
            return "parserImg";
        } catch (Except4SupportDocumented e) {
            System.err.println("Support error: " + e.getMessage4Support());
            model.addAttribute(ConfJs.STATE_ERROR, "Support error: " + e.getMessage4Support());
            return "parserImg";
        }
    }
}
