package com.xymc.vexonlinetime.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeConfig {
    private static Map<Integer,TimeConfig> timeConfigMap = new HashMap<>();
    private String id;
    private int titleX;
    private int titleY;
    private String titleText;
    private int descX;
    private int descY;
    private int time;
    private List<String> descTextList;
    private List<String> cmdList;
    private List<String> itemList;

    public static Map<Integer, TimeConfig> getTimeConfigMap() {
        return timeConfigMap;
    }

    public static void setTimeConfigMap(Map<Integer, TimeConfig> timeConfigMap) {
        TimeConfig.timeConfigMap = timeConfigMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTitleX() {
        return titleX;
    }

    public void setTitleX(int titleX) {
        this.titleX = titleX;
    }

    public int getTitleY() {
        return titleY;
    }

    public void setTitleY(int titleY) {
        this.titleY = titleY;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public int getDescX() {
        return descX;
    }

    public void setDescX(int descX) {
        this.descX = descX;
    }

    public int getDescY() {
        return descY;
    }

    public void setDescY(int descY) {
        this.descY = descY;
    }

    public List<String> getDescTextList() {
        return descTextList;
    }

    public void setDescTextList(List<String> descTextList) {
        this.descTextList = descTextList;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<String> getCmdList() {
        return cmdList;
    }

    public void setCmdList(List<String> cmdList) {
        this.cmdList = cmdList;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "TimeConfig{" +
                "id='" + id + '\'' +
                ", titleX=" + titleX +
                ", titleY=" + titleY +
                ", titleText='" + titleText + '\'' +
                ", descX=" + descX +
                ", descY=" + descY +
                ", time=" + time +
                ", descTextList=" + descTextList +
                ", cmdList=" + cmdList +
                ", itemList=" + itemList +
                '}';
    }
}
