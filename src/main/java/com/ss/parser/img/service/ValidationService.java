package com.ss.parser.img.service;

import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.parser.img.validation.ValidationUrlFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ValidationService {
    private final ValidationUrlFolder validationUrlFolder;

    @Autowired
    public ValidationService(ValidationUrlFolder validationUrlFolder) {
        this.validationUrlFolder = validationUrlFolder;
    }


    public void validationUrl(String url) throws ExceptInfoUser {
        if (!validationUrlFolder.isValidUrl(url)) {
            throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "Invalid URL. Please enter a valid URL."));
        }
    }

    public void validationFolder(String folderName) throws ExceptInfoUser {
        if (!validationUrlFolder.isValidFolderName(folderName)) {
            throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "Invalid folder name. Please avoid special characters: /\\:*?\"<>|"));
        }
    }
}
