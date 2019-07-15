package com.xymc.vexonlinetime.model;

public class GuiConfig {
    private static GuiConfig guiConfig = new GuiConfig();
    private String url;
    private int x;
    private int y;
    private int width;
    private int height;
    private int xshow;
    private int yshow;

    private GuiConfig(){}

    public static GuiConfig getGuiConfig() {
        return guiConfig;
    }

    public static void setGuiConfig(GuiConfig guiConfig) {
        GuiConfig.guiConfig = guiConfig;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getXshow() {
        return xshow;
    }

    public void setXshow(int xshow) {
        this.xshow = xshow;
    }

    public int getYshow() {
        return yshow;
    }

    public void setYshow(int yshow) {
        this.yshow = yshow;
    }

    @Override
    public String toString() {
        return "GuiConfig{" +
                "url='" + url + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", xshow=" + xshow +
                ", yshow=" + yshow +
                '}';
    }
}
