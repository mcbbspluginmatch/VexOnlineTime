package com.xymc.vexonlinetime.model;

import lk.vexview.gui.components.VexButton;

import java.util.HashMap;
import java.util.Map;

public class ButtonConfig {
    private static Map<String,ButtonConfig> vexButtonMap = new HashMap<>();
    private String id;
    private VexButton vexButton;

    public static Map<String, ButtonConfig> getVexButtonMap() {
        return vexButtonMap;
    }

    public static void setVexButtonMap(Map<String, ButtonConfig> vexButtonMap) {
        ButtonConfig.vexButtonMap = vexButtonMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VexButton getVexButton() {
        return vexButton;
    }

    public void setVexButton(VexButton vexButton) {
        this.vexButton = vexButton;
    }
}
