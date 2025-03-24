/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ss.parser.img.conf.js;

import com.ss.config.js.ExceptConf;
import com.ss.config.js.ConfJs;
import com.ss.config.js.ExceptCJsNoObject;
import com.ss.config.js.ExceptCJsUnsupported;

import java.io.FileNotFoundException;

/**
 * @author vlitenko
 */
public class ConfJsParser extends ConfJs {

    public static final String APP_NAME = "parser-service";
    public static final ConfJsParser instance = new ConfJsParser();
    public static final String CONF_FILE_NAME = "conf_parser_serv.json";

    private ConfJsParser() {
        super(APP_NAME, ConfJsAppFactoryParser.getInstance());
        try {
            load(CONF_FILE_NAME, "../" + CONF_FILE_NAME);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NAME, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NAME, ex);
        }
    }

    public void updateConf() {
        try {
            load(CONF_FILE_NAME, "../" + CONF_FILE_NAME);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NAME, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NAME, ex);
        }
    }

    public static ConfJsParser getInstance() {
        return instance;
    }

    public ConfJsAppParser getApp() {
        try {
            return (ConfJsAppParser) super.getApp(APP_NAME);
        } catch (ExceptCJsNoObject ex) {
            throw new ExceptConf("ErrConf3", "Can't process project configuration",
                    String.format("Cant't get app %s in file %s", APP_NAME, CONF_FILE_NAME), ex);
        }
    }
}

