package com.xymc.vexonlinetime.model;

public class PlayerTitleConfig {
    private static PlayerTitleConfig playerTitleConfig = new PlayerTitleConfig();
    private int x;
    private int y;
    private String title;

    public static PlayerTitleConfig getPlayerTitleConfig() {
        return playerTitleConfig;
    }

    public static void setPlayerTitleConfig(PlayerTitleConfig playerTitleConfig) {
        PlayerTitleConfig.playerTitleConfig = playerTitleConfig;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
