package com.xymc.vexonlinetime.model;

import lk.vexview.gui.components.VexText;

import java.util.HashMap;
import java.util.Map;

public class MessageConfig {
    private static Map<String,MessageConfig> vexTextMap = new HashMap<>();
    private static int x;
    private static int y;
    private String id;
    private VexText vexText;

    public static Map<String, MessageConfig> getVexTextMap() {
        return vexTextMap;
    }

    public static void setVexTextMap(Map<String, MessageConfig> vexTextMap) {
        MessageConfig.vexTextMap = vexTextMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VexText getVexText() {
        return vexText;
    }

    public void setVexText(VexText vexText) {
        this.vexText = vexText;
    }
}
