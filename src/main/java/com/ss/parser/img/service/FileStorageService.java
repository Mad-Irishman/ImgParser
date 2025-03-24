package com.ss.parser.img.service;

import com.ss.Except4SupportDocumented;
import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.parser.img.conf.js.ConfJsParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Service
public class FileStorageService {

    public void createFolder(String folderName) throws ExceptInfoUser {
        String downloadPath = ConfJsParser.getInstance().getDownloadPath();
        Path directoryPath = Paths.get(downloadPath, folderName);

        if (Files.exists(directoryPath)) {
            throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "The folder already exists"));
        }

        try {
            Files.createDirectories(directoryPath);
        } catch (IOException e) {
            throw new Except4SupportDocumented(
                    "FILE_STORAGE_ERROR",
                    "Error creating folders",
                    "Error while trying to create folder: " + directoryPath.toString(),
                    e
            );
        }
    }
}
