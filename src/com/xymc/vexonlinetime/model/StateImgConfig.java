package com.xymc.vexonlinetime.model;

import lk.vexview.gui.components.VexImage;

public class StateImgConfig {
    public static VexImage successVexImage;
    public static VexImage failVexImage;

    public static VexImage getSuccessVexImage() {
        return successVexImage;
    }

    public static void setSuccessVexImage(VexImage successVexImage) {
        StateImgConfig.successVexImage = successVexImage;
    }

    public static VexImage getFailVexImage() {
        return failVexImage;
    }

    public static void setFailVexImage(VexImage failVexImage) {
        StateImgConfig.failVexImage = failVexImage;
    }
}
