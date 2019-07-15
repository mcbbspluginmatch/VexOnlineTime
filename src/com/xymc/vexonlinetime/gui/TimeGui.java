package com.xymc.vexonlinetime.gui;

import com.xymc.vexonlinetime.main.MainPlugin;
import com.xymc.vexonlinetime.model.*;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexText;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TimeGui {
    public static VexGui getGUI(Player p){
        //设置gui
        GuiConfig guiConfig = GuiConfig.getGuiConfig();
        VexGui gui = new VexGui(guiConfig.getUrl(),guiConfig.getX(),guiConfig.getY(),guiConfig.getWidth(),guiConfig.getHeight(),guiConfig.getXshow(),guiConfig.getYshow());
        //设置title
        TitleConfig titleConfig = TitleConfig.getTitleConfig();
        gui.addComponent(new VexText(titleConfig.getX(),titleConfig.getY(), Arrays.asList(titleConfig.getTitle())));
        //设置playerTitle
        PlayerTitleConfig playerTitleConfig = PlayerTitleConfig.getPlayerTitleConfig();
        String title = playerTitleConfig.getTitle().replace("%time%", ""+MainPlugin.playerTimeMap.get(p.getName()));
        title = "§0§0§0§0"+title;
        gui.addComponent(new VexText(playerTitleConfig.getX(),playerTitleConfig.getY(), Arrays.asList(title)));
        //设置内容
        for(Integer key: TimeConfig.getTimeConfigMap().keySet()){
            if(key>4){
                break;
            }
            TimeConfig timeConfig = TimeConfig.getTimeConfigMap().get(key);
            gui.addComponent(new VexText(timeConfig.getTitleX(),timeConfig.getTitleY(), Arrays.asList(timeConfig.getTitleText())));
            gui.addComponent(new VexText(timeConfig.getDescX(),timeConfig.getDescY(),timeConfig.getDescTextList()));
        }
        //按钮
//        String defButtonUrl = "https://s2.ax1x.com/2019/07/14/Z59sKJ.png";
//        String clickButtonUrl = "https://s2.ax1x.com/2019/07/14/Z596bR.png";
//        VexButton vexButton1 = new VexButton("time1","",defButtonUrl,clickButtonUrl,26,167,50,16);
//        VexButton vexButton2 = new VexButton("time2","",defButtonUrl,clickButtonUrl,126,167,50,16);
//        VexButton vexButton3 = new VexButton("time3","",defButtonUrl,clickButtonUrl,226,167,50,16);
//        VexButton vexButton4 = new VexButton("time4","",defButtonUrl,clickButtonUrl,326,167,50,16);
//        gui.addComponent(vexButton1);
//        gui.addComponent(vexButton2);
//        gui.addComponent(vexButton3);
//        gui.addComponent(vexButton4);
        for(String key:ButtonConfig.getVexButtonMap().keySet()){
            ButtonConfig buttonConfig = ButtonConfig.getVexButtonMap().get(key);
            VexButton vexButton = buttonConfig.getVexButton();
            gui.addComponent(vexButton);
        }
        //切换按钮
        String leftDefButtonUrl = "https://s2.ax1x.com/2019/07/14/Z5FMZj.png";
//        String leftClickButtonUrl = "https://s2.ax1x.com/2019/07/14/Z5Flon.png";
        VexButton leftButton = new VexButton("leftButton", "", leftDefButtonUrl, leftDefButtonUrl, 28, 10, 20, 22);
        String rightDefButtonUrl = "https://s2.ax1x.com/2019/07/14/Z5FQds.png";
//        String rightClickButtonUrl = "https://s2.ax1x.com/2019/07/14/Z5FuLQ.png";
        VexButton rightButton = new VexButton("rightButton", "", rightDefButtonUrl, rightDefButtonUrl, 352, 10, 20, 22);
        gui.addComponent(leftButton);
        gui.addComponent(rightButton);
        return gui;
    }
}
