/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.parser.img.conf.js;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ss.config.js.ExceptConf;
import com.fasterxml.jackson.databind.JsonNode;
import com.ss.config.js.ConfJsApp;
import com.ss.config.js.ConfJsDb;

/**
 * @author vlitenko
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConfJsAppParser extends ConfJsApp {

    private String nameServer;
    private String serverType;
    private int executorPoolSize;
    private int executorTimeoutSeconds;
    private String downloadPath;

    private int errorsSize;
    private int errorsInterval;
    private int errorsSleepInterval;


    public static final String SERVER_TYPE_DEV = "dev";
    public static final String SERVER_TYPE_TEST = "test";
    public static final String SERVER_TYPE_PREPROD = "preprod";
    public static final String SERVER_TYPE_PROD = "prod";

    public ConfJsAppParser() {
        super(ConfJsDb.knownDb);
    }

    public ConfJsAppParser(ConfJsApp p_kCopy) {
        super(p_kCopy);
    }

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptConf {
        try {

            // TECHNICAL
            nameServer = getStringRequired(p_xParser, "name");
            serverType = getStringRequired(p_xParser, "server_type");
            downloadPath = getStringRequired(p_xParser, "download_path");
            //ERRORS
            errorsSize = getIntRequired(p_xParser, "attack_errors_size");
            errorsInterval = getIntRequired(p_xParser, "attack_errors_interval_sec");
            errorsSleepInterval = getIntRequired(p_xParser, "attack_errors_sleep_interval_sec");
            //Pool
            executorPoolSize = getIntRequired(p_xParser, "executor_pool_size");
            executorTimeoutSeconds = getIntRequired(p_xParser, "executor_timeout_seconds");

        } catch (RuntimeException ex) {
            throw new ExceptConf("ErrConfA1", "Can't process project configuration",
                    ex.getMessage(), ex);
        }
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getServerType() {
        return serverType;
    }

    public int getErrorsSize() {
        return errorsSize;
    }

    public int getErrorsInterval() {
        return errorsInterval;
    }

    public int getErrorsSleepInterval() {
        return errorsSleepInterval;
    }

    public int getExecutorPoolSize() {
        return executorPoolSize;
    }

    public int getExecutorTimeoutSeconds() {
        return executorTimeoutSeconds;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    @Override
    public String toString() {
        return
                "serverType=" + serverType + "\n";

    }
}
