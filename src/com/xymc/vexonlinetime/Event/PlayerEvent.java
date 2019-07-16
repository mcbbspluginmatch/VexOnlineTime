package com.xymc.vexonlinetime.Event;

import com.xymc.vexonlinetime.main.MainPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sun.applet.Main;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class PlayerEvent implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        File data = new File(MainPlugin.plugin.getDataFolder(),"Data");
        File playerDataFile = new File(data,player.getName()+".yml");
        YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
        if(!playerDataFile.exists()){
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            playerYml.set("date.day", date.getDate());
            playerYml.set("date.month",date.getMonth()+1);
            playerYml.set("time",0);
            try {
                playerYml.save(playerDataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Date date = new Date();
        if(date.getDate()==playerYml.getInt("date.day") && ((date.getMonth()+1)==playerYml.getInt("date.month"))){
            MainPlugin.PLAYER_TIME_MAP.put(player.getName(),playerYml.getInt("time"));
        }else{
            playerYml.set("date.day", date.getDate());
            playerYml.set("date.month",date.getMonth()+1);
            playerYml.set("time",0);
            playerYml.set("list",null);
            try {
                playerYml.save(playerDataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MainPlugin.PLAYER_TIME_MAP.put(player.getName(),0);
        }
    }
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();
        File data = new File(MainPlugin.plugin.getDataFolder(),"Data");
        File playerDataFile = new File(data,player.getName()+".yml");
        YamlConfiguration playerYml = YamlConfiguration.loadConfiguration(playerDataFile);
        if(MainPlugin.PLAYER_TIME_MAP.containsKey(player.getName()) && MainPlugin.PLAYER_TIME_MAP.get(player.getName())>=0){
            playerYml.set("time",MainPlugin.PLAYER_TIME_MAP.get(player.getName()));
            try {
                playerYml.save(playerDataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MainPlugin.PLAYER_TIME_MAP.remove(player.getName());
    }
}
