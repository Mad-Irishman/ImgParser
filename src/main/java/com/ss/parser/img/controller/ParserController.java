package com.ss.parser.img.controller;

import com.ss.Except4Support;
import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.parser.img.service.ImageProcessingFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/download/")
public class ParserController {

    private final ImageProcessingFacadeService imageProcessingFacadeService;

    @Autowired
    public ParserController(ImageProcessingFacadeService imageProcessingFacadeService) {
        this.imageProcessingFacadeService = imageProcessingFacadeService;

    }

    @GetMapping(path = "/img")
    public String showFormForUrl() {
        return "parserImg";
    }

    @PostMapping(path = "/submit")
    public String handleUrlSubmission(@RequestParam String url, @RequestParam String folderName, Model model) {
        try {
            imageProcessingFacadeService.processImage(url, folderName);

            return "parserImg";

        } catch (ExceptInfoUser e) {
            model.addAttribute(ConfJs.STATE_ERROR, e.getErrorMessage());
            return "parserImg";
        } catch (Except4Support e) {
            model.addAttribute(ConfJs.STATE_ERROR, e.getMessage4User());
            System.err.println("Support error: " + e.getMessage4Support());
            return "parserImg";
        }
    }
}
