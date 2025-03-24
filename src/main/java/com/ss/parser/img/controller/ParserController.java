package com.ss.parser.img.controller;

import com.ss.Except4Support;
import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.parser.img.conf.js.ConfJsParser;
import com.ss.parser.img.service.FileStorageService;
import com.ss.parser.img.service.ImageProcessingFacadeService;
import com.ss.parser.img.service.ValidationService;
import com.ss.parser.img.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParserController {

    private final ImageService imageService;
    private final ImageProcessingFacadeService imageProcessingFacadeService;

    @Autowired
    public ParserController(ImageService imageService, ImageProcessingFacadeService imageProcessingFacadeService) {
        this.imageService = imageService;
        this.imageProcessingFacadeService = imageProcessingFacadeService;

    }

    @GetMapping(path = "/download/img")
    public String showFormForUrl() {
        return "parserImg";
    }

    @PostMapping(path = "/download/submit")
    public String handleUrlSubmission(@RequestParam String url, @RequestParam String folderName, Model model) {
        try {
            imageProcessingFacadeService.processImage(url, folderName);
            imageService.processWebsiteImages(url, ConfJsParser.getInstance().getApp().getDownloadPath() + folderName);

            return "parserImg";

        } catch (ExceptInfoUser e) {
            model.addAttribute(ConfJs.STATE_ERROR, e.getErrorMessage());
            return "parserImg";
        } catch (Except4Support e) {
            System.err.println("Support error: " + e.getMessage4Support());
            return "parserImg";
        }
    }
}
