package com.xymc.vexonlinetime.gui;

import com.xymc.vexonlinetime.main.MainPlugin;
import com.xymc.vexonlinetime.model.*;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexSlot;
import lk.vexview.gui.components.VexText;
import lk.vexview.gui.components.expand.VexClickableButton;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class TimeGui {
    public static VexGui getGUI(Player p){
        //玩家文件
        File data = new File(MainPlugin.plugin.getDataFolder(),"Data");
        File playerDataFile = new File(data,p.getName()+".yml");
        YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
        List<String> playerList = new ArrayList<>(playerYml.getStringList("list"));
        //配置文件
        File file = new File(MainPlugin.plugin.getDataFolder(),"config.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        Set<String> timeSet = yml.getConfigurationSection("TimeList").getKeys(false);
        Iterator<String> iterator = timeSet.iterator();
        //设置gui
        GuiConfig guiConfig = GuiConfig.getGuiConfig();
        VexGui gui = new VexGui(guiConfig.getUrl(),guiConfig.getX(),guiConfig.getY(),guiConfig.getWidth(),guiConfig.getHeight(),guiConfig.getXshow(),guiConfig.getYshow());
        //设置title
        TitleConfig titleConfig = TitleConfig.getTitleConfig();
        gui.addComponent(new VexText(titleConfig.getX(),titleConfig.getY(), Arrays.asList(titleConfig.getTitle()),1.5));
        //设置playerTitle
        PlayerTitleConfig playerTitleConfig = PlayerTitleConfig.getPlayerTitleConfig();
        String title = playerTitleConfig.getTitle().replace("%time%", ""+MainPlugin.PLAYER_TIME_MAP.get(p.getName()));
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
        //按钮设置
        int i = 1;
        while(i<=4){
            ClickableVexButtonConfig clickableVexButtonConfig = ClickableVexButtonConfig.getVexButtonConfigMap().get(i);
            if(iterator.hasNext()){
                String time = iterator.next();
                if(!playerList.contains(time)){
                    VexClickableButton vexClickableButton = new VexClickableButton(i,"",ClickableVexButtonConfig.getDefUrl(),ClickableVexButtonConfig.getCheckUrl(),ClickableVexButtonConfig.getNotCheckUrl(),clickableVexButtonConfig.getX(),clickableVexButtonConfig.getY(),ClickableVexButtonConfig.getButtonXshow(),ClickableVexButtonConfig.getButtonYshow(),true);
                    gui.addComponent(vexClickableButton);
                }else{
                    VexClickableButton vexClickableButton = new VexClickableButton(i,"",ClickableVexButtonConfig.getDefUrl(),ClickableVexButtonConfig.getCheckUrl(),ClickableVexButtonConfig.getNotCheckUrl(),clickableVexButtonConfig.getX(),clickableVexButtonConfig.getY(),ClickableVexButtonConfig.getButtonXshow(),ClickableVexButtonConfig.getButtonYshow(),false);
                    gui.addComponent(vexClickableButton);
                }
            }else{
                VexClickableButton vexClickableButton = new VexClickableButton(i,"",ClickableVexButtonConfig.getDefUrl(),ClickableVexButtonConfig.getCheckUrl(),ClickableVexButtonConfig.getNotCheckUrl(),clickableVexButtonConfig.getX(),clickableVexButtonConfig.getY(),ClickableVexButtonConfig.getButtonXshow(),ClickableVexButtonConfig.getButtonYshow(),false);
                gui.addComponent(vexClickableButton);
            }
            i++;
        }
        //切换按钮
        String leftDefButtonUrl = "https://s2.ax1x.com/2019/07/14/Z5FMZj.png";
        VexButton leftButton = new VexButton("leftButton", "", leftDefButtonUrl, leftDefButtonUrl, 28, 10, 20, 22);
        String rightDefButtonUrl = "https://s2.ax1x.com/2019/07/14/Z5FQds.png";
        VexButton rightButton = new VexButton("rightButton", "", rightDefButtonUrl, rightDefButtonUrl, 352, 10, 20, 22);
        gui.addComponent(leftButton);
        gui.addComponent(rightButton);
        return gui;
    }
}
