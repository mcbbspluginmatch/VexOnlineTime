package com.xymc.vexonlinetime.main;

import com.xymc.vexonlinetime.Event.PlayerEvent;
import com.xymc.vexonlinetime.Event.VexEvent;
import com.xymc.vexonlinetime.command.PlayerCommand;
import com.xymc.vexonlinetime.model.*;
import lk.vexview.gui.components.VexImage;
import lk.vexview.gui.components.VexText;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainPlugin extends JavaPlugin {
    public static MainPlugin plugin;
    public static HashMap<String,Integer> playerTimeMap = new HashMap<>();
    private static int day;
    private static int month;
    @Override
    public void onEnable(){
        System.out.println("[VexOnlineTime] 插件加载中......");
        if(!hasVV()){
            Bukkit.getConsoleSender().sendMessage("[VexOnlineTime] §c未找到VexView插件,插件自动卸载！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }else{
            Bukkit.getConsoleSender().sendMessage("[VexOnlineTime] §a已找到VexView插件,关联成功！");
            MainPlugin.plugin = this;
            /*指令注册*/
            Bukkit.getPluginCommand("xytime").setExecutor(new PlayerCommand());
            /*事件注册*/
            Bukkit.getPluginManager().registerEvents(new VexEvent(),this);
            Bukkit.getPluginManager().registerEvents(new PlayerEvent(),this);
            /*config*/
            File configFile = new File(getDataFolder(),"config.yml");
            if(!configFile.exists()){
                saveDefaultConfig();
                File playerDataFile = new File(getDataFolder(),"Data");
                playerDataFile.mkdirs();
                File itemFile = new File(getDataFolder(),"Items");
                itemFile.mkdirs();
                saveResource("message.yml",true);
            }
            /*load*/
            loadConfig();
        }
        /*玩家加载*/
        for(Player player:getServer().getOnlinePlayers()){
            File playerDataFile = new File(new File(MainPlugin.plugin.getDataFolder(),"Data"),player.getName()+".yml");
            YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
            MainPlugin.playerTimeMap.put(player.getName(),playerYml.getInt("time"));
        }
        /*time*/
        new BukkitRunnable(){
            @Override
            public void  run(){
                Date date = new Date();
                if(date.getDate()==MainPlugin.day && ((date.getMonth()+1)==MainPlugin.month)){
                    for(Player player:getServer().getOnlinePlayers()){
                        if(MainPlugin.playerTimeMap.containsKey(player.getName())){
                            MainPlugin.playerTimeMap.put(player.getName(),MainPlugin.playerTimeMap.get(player.getName())+1);
                        }
                    }
                }else{
                    MainPlugin.day = date.getDate();
                    MainPlugin.month = date.getMonth()+1;
                    System.out.println("[VexOnlineTime] 日期变更,当前时间"+month+"月"+day+"日");
                    for(Player player:getServer().getOnlinePlayers()){
                        //在线玩家time设为0
                        MainPlugin.playerTimeMap.put(player.getName(),0);
                        //配置文件中的time和list设为0
                        File data = new File(MainPlugin.plugin.getDataFolder(),"Data");
                        File playerDataFile = new File(data,player.getName()+".yml");
                        YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
                        playerYml.set("list",null);
                        playerYml.set("time",0);
                        try {
                            playerYml.save(playerDataFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this,0,60*20);

    }
    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage("[VexOnlineTime] §e插件卸载,保存数据中...");
        for(Player player:getServer().getOnlinePlayers()){
            File playerDataFile = new File(new File(MainPlugin.plugin.getDataFolder(),"Data"),player.getName()+".yml");
            YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
            if(MainPlugin.playerTimeMap.containsKey(player.getName())){
                playerYml.set("time",MainPlugin.playerTimeMap.get(player.getName()));
                try {
                    playerYml.save(playerDataFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage("[VexOnlineTime] §e玩家数据保存完成...");
    }
    private boolean hasVV(){
        if(getServer().getPluginManager().getPlugin("VexView")!=null){
            return true;
        }else{
            return false;
        }
    }
    public static void loadConfig(){
        File file = new File(MainPlugin.plugin.getDataFolder(),"config.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        /*gui设置*/
        GuiConfig guiConfig = GuiConfig.getGuiConfig();
        guiConfig.setUrl(yml.getString("Gui.url"));
        guiConfig.setX(yml.getInt("Gui.x"));
        guiConfig.setY(yml.getInt("Gui.y"));
        guiConfig.setWidth(yml.getInt("Gui.width"));
        guiConfig.setHeight(yml.getInt("Gui.height"));
        guiConfig.setXshow(yml.getInt("Gui.xshow"));
        guiConfig.setYshow(yml.getInt("Gui.yshow"));
//        System.out.println(GuiConfig.getGuiConfig().toString());
        /*title设置*/
        TitleConfig titleConfig = TitleConfig.getTitleConfig();
        titleConfig.setX(yml.getInt("Title.x"));
        titleConfig.setY(yml.getInt("Title.y"));
        titleConfig.setTitle(yml.getString("Title.text"));
//        System.out.println(TitleConfig.getTitleConfig().toString());
        /*playertitle设置*/
        PlayerTitleConfig playerTitleConfig = PlayerTitleConfig.getPlayerTitleConfig();
        playerTitleConfig.setTitle(yml.getString("PlayerTitle.text"));
        playerTitleConfig.setX(yml.getInt("PlayerTitle.x"));
        playerTitleConfig.setY(yml.getInt("PlayerTitle.y"));
        /*image设置*/
        String successUrl = yml.getString("StateImage.success.url");
        int successX = yml.getInt("StateImage.success.x");
        int successY = yml.getInt("StateImage.success.y");
        int successXshow = yml.getInt("StateImage.success.xshow");
        int successYshow = yml.getInt("StateImage.success.yshow");
        StateImgConfig.setSuccessVexImage(new VexImage(successUrl,successX,successY,successXshow,successYshow));
        String failUrl = yml.getString("StateImage.fail.url");
        int failX = yml.getInt("StateImage.fail.x");
        int failY = yml.getInt("StateImage.fail.y");
        int failXshow = yml.getInt("StateImage.fail.xshow");
        int failYshow = yml.getInt("StateImage.fail.yshow");
        StateImgConfig.setFailVexImage(new VexImage(failUrl,failX,failY,failXshow,failYshow));
        /*time设置*/
        int i = 1;
        for(String key:yml.getConfigurationSection("TimeList").getKeys(false)){
            TimeConfig timeConfig = new TimeConfig();
            String timePath = "TimeList."+key;
            timeConfig.setId(key);
            timeConfig.setTitleX(yml.getInt(timePath+".title.x"));
            timeConfig.setTitleY(yml.getInt(timePath+".title.y"));
            timeConfig.setTitleText(yml.getString(timePath+".title.text"));
            timeConfig.setDescX(yml.getInt(timePath+".desc.x"));
            timeConfig.setDescY(yml.getInt(timePath+".desc.y"));
            timeConfig.setTime(yml.getInt(timePath+".time"));
            timeConfig.setCmdList(yml.getStringList(timePath+".command"));
            timeConfig.setItemList(yml.getStringList(timePath+".item"));
            timeConfig.setDescTextList(yml.getStringList(timePath+".desc.text"));
//            System.out.println(timeConfig.toString());
            TimeConfig.getTimeConfigMap().put(i,timeConfig);
            i++;
        }
        /*items设置*/
        File itemsFile = new File(MainPlugin.plugin.getDataFolder(),"Items");
        for(String itemFileString:itemsFile.list()){
            File itemFile = new File(itemsFile,itemFileString);
            YamlConfiguration itemYml = YamlConfiguration.loadConfiguration(itemFile);
            //获取id
            String itemId = itemFileString.replace(".yml","");
            //获取道具集合
            ItemConfig itemConfig = new ItemConfig();
            List<ItemStack> itemStackList = new ArrayList<>();
            for(String key:itemYml.getKeys(false)){
                itemStackList.add(itemYml.getItemStack(key));
            }
            itemConfig.setItemStackList(itemStackList);
            //存储
            ItemConfig.getItemConfigMap().put(itemId,itemConfig);
//            System.out.println(itemId+"-"+itemConfig.toString());
        }
        /*message设置*/
        File messageFile = new File(MainPlugin.plugin.getDataFolder(),"message.yml");
        YamlConfiguration msgYml = YamlConfiguration.loadConfiguration(messageFile);
        int x = yml.getInt("Message.x");
        int y = yml.getInt("Message.y");
        for(String key:msgYml.getKeys(false)){
            String message = msgYml.getString(key).replace("&","§");
            MessageConfig messageConfig = new MessageConfig();
            messageConfig.setId(key);
            messageConfig.setVexText(new VexText(x,y, Arrays.asList(message)));
            MessageConfig.getVexTextMap().put(key,messageConfig);
//            System.out.println(key+MessageConfig.getVexTextMap().get(key).getVexText().getText().get(0));
        }
        /*按钮设置*/
        ClickableVexButtonConfig.setDefUrl(yml.getString("Button.defaultUrl"));
        ClickableVexButtonConfig.setCheckUrl(yml.getString("Button.checkedUrl"));
        ClickableVexButtonConfig.setNotCheckUrl(yml.getString("Button.unclickable"));
        ClickableVexButtonConfig.setButtonXshow(yml.getInt("Button.xshow"));
        ClickableVexButtonConfig.setButtonYshow(yml.getInt("Button.yshow"));
        for(String key:yml.getConfigurationSection("Button.buttonLocation").getKeys(false)){
            int id = Integer.parseInt(key.replace("button",""));
            ClickableVexButtonConfig clickableVexButtonConfig = new ClickableVexButtonConfig();
            clickableVexButtonConfig.setId(id);
            clickableVexButtonConfig.setX(yml.getInt("Button.buttonLocation."+key+".x"));
            clickableVexButtonConfig.setY(yml.getInt("Button.buttonLocation."+key+".y"));
            ClickableVexButtonConfig.getVexButtonConfigMap().put(id,clickableVexButtonConfig);
        }
        /*time加载*/
        Date date = new Date();
        MainPlugin.day = date.getDate();
        MainPlugin.month = date.getMonth()+1;
    }
}
