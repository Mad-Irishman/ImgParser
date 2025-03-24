/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.parser.img.conf.js;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ss.config.js.ConfJsApp;
import com.ss.config.js.ConfJsDb;
import com.ss.config.js.ExceptConf;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConfJsAppParser extends ConfJsApp {

    private String nameServer;
    private String serverType;
    private int executorPoolSize;
    private int executorTimeoutSeconds;
    private String downloadPath;

    public static final String SERVER_TYPE_DEV = "dev";
    public static final String SERVER_TYPE_TEST = "test";

    public ConfJsAppParser() {
        super(ConfJsDb.knownDb);
    }

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptConf {
        try {
            // TECHNICAL
            nameServer = getStringRequired(p_xParser, "name");
            serverType = getStringRequired(p_xParser, "server_type");
            executorPoolSize = getIntRequired(p_xParser, "executor_pool_size");
            executorTimeoutSeconds = getIntRequired(p_xParser, "executor_timeout_seconds");
            downloadPath = getStringRequired(p_xParser, "download_path");
        } catch (RuntimeException ex) {
            throw new ExceptConf("ErrConfA1", "Can't process project configuration",
                    ex.getMessage(), ex);
        }
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public int getExecutorPoolSize() {
        return executorPoolSize;
    }

    public int getExecutorTimeoutSeconds() {
        return executorTimeoutSeconds;
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getServerType() {
        return serverType;
    }

    @Override
    public String toString() {
        return "serverType" + serverType + "\n";
    }
}
