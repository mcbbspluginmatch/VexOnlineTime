package com.xymc.vexonlinetime.Event;

import com.xymc.vexonlinetime.main.MainPlugin;
import com.xymc.vexonlinetime.model.*;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.ButtonClickEvent;
import lk.vexview.event.gui.VexGuiCloseEvent;
import lk.vexview.gui.OpenedVexGui;
import lk.vexview.gui.components.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class VexEvent implements Listener {
    public static HashMap<String,Integer> playerPages = new HashMap<>();
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        playerPages.put(player.getName(),1);
    }
    @EventHandler
    public void closeEvent(VexGuiCloseEvent event){
        Player player = event.getPlayer();
        playerPages.put(player.getName(),1);
    }
    @EventHandler
    public void clickLeftRightEvent(ButtonClickEvent event){
        Player player = event.getPlayer();
        if(event.getButtonID().equals("leftButton") || event.getButtonID().equals("rightButton")){
            //获取
            OpenedVexGui openedVexGui = VexViewAPI.getPlayerCurrentGui(player);
            //获取页数
            int page = playerPages.get(player.getName());
            //判断点击左侧
            if(event.getButtonID().equals("leftButton")){
                //点击左边
                if(playerPages.get(player.getName())>1){
                    //修改页数
                    playerPages.put(player.getName(),page-1);
                    //删除内容
                    deleteComponents(player);
//                    System.out.println("第一次-大小:"+openedVexGui.getVexGui().getComponents().size());
                    //玩家数据
                    File playerDataFile = new File(new File(MainPlugin.plugin.getDataFolder(),"Data"),player.getName()+".yml");
                    YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
                    List<String> timeList = new ArrayList<>(playerYml.getStringList("list"));
                    //修改内容
                    for(int i=1;i<=4;i++){
                        int key = (page-2)*4+i;
                        TimeConfig timeConfig;
                        if((timeConfig = TimeConfig.getTimeConfigMap().get(key))!=null){
                            //成功获取到TimeConfig
                            addComponents(player,timeConfig,timeList,i);
                        }
                    }
                }else{
                    //第一页直接返回;
                    return;
                }
//                System.out.println("第二次-大小:"+openedVexGui.getVexGui().getComponents().size());
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //判断点击右侧
            if(event.getButtonID().equals("rightButton")){
                //点击右边
                if(TimeConfig.getTimeConfigMap().size()>(page*4)){
                    //修改页数
                    playerPages.put(player.getName(),page+1);
                    //删除内容
                    deleteComponents(player);
//                    System.out.println("第一次-大小:"+openedVexGui.getVexGui().getComponents().size());
                    //玩家数据
                    File playerDataFile = new File(new File(MainPlugin.plugin.getDataFolder(),"Data"),player.getName()+".yml");
                    YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
                    List<String> timeList = new ArrayList<>(playerYml.getStringList("list"));
                    //修改内容
                    for(int i=1;i<=4;i++){
                        //获取Key
                        int key = page*4+i;
                        TimeConfig timeConfig;
                        if((timeConfig = TimeConfig.getTimeConfigMap().get(key))!=null){
                            //成功获取到TimeConfig
                            addComponents(player,timeConfig,timeList,i);
                        }else{
                            //为空说明已经获取到最后一个了,直接跳出循环
                            break;
                        }
                    }
                }else{
                    //最后一页直接返回
                    return;
                }
//                System.out.println("第二次-大小:"+openedVexGui.getVexGui().getComponents().size());
            }
//            System.out.println("当前页数:"+playerPages.get(player.getName()));
        }
    }
    @EventHandler
    public void clickTimeButtonEvent(ButtonClickEvent event){
        Player player = event.getPlayer();
        if(event.getButtonID().equals(1)||event.getButtonID().equals(2)||event.getButtonID().equals(3)||event.getButtonID().equals(4)){
            int timeId = (playerPages.get(player.getName())-1)*4;
            timeId = timeId+(int)event.getButtonID();
//            if(event.getButtonID().equals("time1")){
//                timeId = timeId+1;
//            }else if(event.getButtonID().equals("time2")){
//                timeId = timeId+2;
//            }else if(event.getButtonID().equals("time3")){
//                timeId = timeId+3;
//            }else if(event.getButtonID().equals("time4")){
//                timeId = timeId+4;
//            }
            //领取的奖励实例
            TimeConfig timeConfig;
            //没有奖励判断
            if((timeConfig = TimeConfig.getTimeConfigMap().get(timeId))==null){
                player.sendMessage("无礼包可领取");
                return;
            }
            //消息
//            player.sendMessage(timeConfig.getTitleText());
            //可操控GUI
            OpenedVexGui openedVexGui = VexViewAPI.getPlayerCurrentGui(player);
            //玩家配置
            File playerDataFile = new File(new File(MainPlugin.plugin.getDataFolder(),"Data"),player.getName()+".yml");
            YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
            //已经领取的奖励集合
            List<String> timeList = new ArrayList<>(playerYml.getStringList("list"));
            //判断是否已经领取
            if(!timeList.contains(timeConfig.getId())){
                if(MainPlugin.playerTimeMap.get(player.getName())>=timeConfig.getTime()){
                    //配置保存
                    timeList.add(timeConfig.getId());
                    playerYml.set("list",timeList);
                    try {
                        playerYml.save(playerDataFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    openedVexGui.addDynamicComponent(StateImgConfig.getSuccessVexImage());
                    openedVexGui.addDynamicComponent(MessageConfig.getVexTextMap().get("success").getVexText());
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            openedVexGui.removeDynamicComponent(StateImgConfig.getSuccessVexImage());
                            openedVexGui.removeDynamicComponent(MessageConfig.getVexTextMap().get("success").getVexText());
                            //关闭gui
                            player.closeInventory();
                            //达到要求,成功
                            //指令执行
                            for(String cmd:timeConfig.getCmdList()){
                                cmd = cmd.replace("%player%",player.getName());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd);
                            }
                            //给与道具
                            List<ItemStack> itemStackList = new ArrayList<>();
                            for(String itemId:timeConfig.getItemList()){
                                if(ItemConfig.getItemConfigMap().containsKey(itemId)){
                                    itemStackList.addAll(ItemConfig.getItemConfigMap().get(itemId).getItemStackList());
                                }
                            }
                            for(ItemStack itemStack:itemStackList){
                                player.getInventory().addItem(itemStack);
                            }
                            cancel();
                        }
                    }.runTaskLaterAsynchronously(MainPlugin.plugin, 10L*1);
                }else{
                    //未达到要求.失败
                    openedVexGui.addDynamicComponent(StateImgConfig.getFailVexImage());
                    openedVexGui.addDynamicComponent(MessageConfig.getVexTextMap().get("fail").getVexText());
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            openedVexGui.removeDynamicComponent(StateImgConfig.getFailVexImage());
                            openedVexGui.removeDynamicComponent(MessageConfig.getVexTextMap().get("fail").getVexText());
                            cancel();
                        }
                    }.runTaskLaterAsynchronously(MainPlugin.plugin, 10L*1);
                }
            }else{
                //已经领取,失败
                openedVexGui.addDynamicComponent(StateImgConfig.getFailVexImage());
                openedVexGui.addDynamicComponent(MessageConfig.getVexTextMap().get("repeat").getVexText());
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        openedVexGui.removeDynamicComponent(StateImgConfig.getFailVexImage());
                        openedVexGui.removeDynamicComponent(MessageConfig.getVexTextMap().get("repeat").getVexText());
                        cancel();
                    }
                }.runTaskLaterAsynchronously(MainPlugin.plugin, 10L*1);
            }
        }
    }
    private void deleteComponents(Player player){
        OpenedVexGui openedVexGui = VexViewAPI.getPlayerCurrentGui(player);
        List<VexComponents> vexComponentsList = new ArrayList<>(openedVexGui.getVexGui().getComponents());
        for(VexComponents vexComponents:vexComponentsList){
            if(vexComponents instanceof VexText){
                VexText vexText = (VexText) vexComponents;
                if(!vexText.getText().get(0).equals(TitleConfig.getTitleConfig().getTitle()) && !vexText.getText().get(0).contains("§0§0§0§0")){
                    openedVexGui.getVexGui().getComponents().remove(vexText);
                    openedVexGui.removeDynamicComponent(vexText);
                }
            }
        }
    }
    private void addComponents(Player player,TimeConfig timeConfig,List<String> timeList,int i){
        OpenedVexGui openedVexGui = VexViewAPI.getPlayerCurrentGui(player);
        openedVexGui.addDynamicComponent(new VexText(timeConfig.getTitleX(),timeConfig.getTitleY(), Arrays.asList(timeConfig.getTitleText())));
        openedVexGui.addDynamicComponent(new VexText(timeConfig.getDescX(),timeConfig.getDescY(),timeConfig.getDescTextList()));
        //设置是否可用
        if(timeList.contains(timeConfig.getId())){
            openedVexGui.setButtonClickable(i,false);
        }else{
            openedVexGui.setButtonClickable(i,true);
        }
    }
}
