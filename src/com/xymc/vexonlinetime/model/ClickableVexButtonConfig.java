package com.xymc.vexonlinetime.model;


import java.util.HashMap;
import java.util.Map;

public class ClickableVexButtonConfig {
    private static Map<Integer,ClickableVexButtonConfig> vexButtonConfigMap = new HashMap<>();
    private static String defUrl;
    private static String checkUrl;
    private static String notCheckUrl;
    private static int buttonXshow;
    private static int buttonYshow;
    private int id;
    private int x;
    private int y;

    public static Map<Integer, ClickableVexButtonConfig> getVexButtonConfigMap() {
        return vexButtonConfigMap;
    }

    public static void setVexButtonConfigMap(Map<Integer, ClickableVexButtonConfig> vexButtonConfigMap) {
        ClickableVexButtonConfig.vexButtonConfigMap = vexButtonConfigMap;
    }

    public static String getDefUrl() {
        return defUrl;
    }

    public static void setDefUrl(String defUrl) {
        ClickableVexButtonConfig.defUrl = defUrl;
    }

    public static String getCheckUrl() {
        return checkUrl;
    }

    public static void setCheckUrl(String checkUrl) {
        ClickableVexButtonConfig.checkUrl = checkUrl;
    }

    public static String getNotCheckUrl() {
        return notCheckUrl;
    }

    public static void setNotCheckUrl(String notCheckUrl) {
        ClickableVexButtonConfig.notCheckUrl = notCheckUrl;
    }

    public static int getButtonXshow() {
        return buttonXshow;
    }

    public static void setButtonXshow(int buttonXshow) {
        ClickableVexButtonConfig.buttonXshow = buttonXshow;
    }

    public static int getButtonYshow() {
        return buttonYshow;
    }

    public static void setButtonYshow(int buttonYshow) {
        ClickableVexButtonConfig.buttonYshow = buttonYshow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
