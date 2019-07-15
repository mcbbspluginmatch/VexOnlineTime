package com.xymc.vexonlinetime.command;

import com.xymc.vexonlinetime.gui.TimeGui;
import com.xymc.vexonlinetime.main.MainPlugin;
import lk.vexview.api.VexViewAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args){
        if(cmd.getName().equals("xytime")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(args.length==1 && args[0].equals("open")){
                    //打开GUI
                    VexViewAPI.openGui(player, TimeGui.getGUI(player));
                    return true;
                }
                if(player.isOp()){
                    if(args.length==3 && args[0].equals("items") && args[1].equals("create")){
                        String itemsId = args[2]+".yml";
                        File itemsFile = new File(MainPlugin.plugin.getDataFolder(),"Items");
                        List<String> itemsList = Arrays.asList(itemsFile.list());
                        if(!itemsList.contains(itemsId)){
                            File itemFile = new File(itemsFile,itemsId);
                            try {
                                itemFile.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            player.sendMessage("§7[§fVexOnlineTime§7] §a物品集合创建完成!");
                        }else{
                            player.sendMessage("§7[§fVexOnlineTime§7] §5文件已存在,请更换名称!");
                        }
                        return true;
                    }
                    if(args.length==3 && args[0].equals("items") && args[1].equals("add")){
                        String itemsId = args[2]+".yml";
                        File itemsFile = new File(MainPlugin.plugin.getDataFolder(),"Items");
                        List<String> itemsList = Arrays.asList(itemsFile.list());
                        if(itemsList.contains(itemsId)){
                            if(player.getItemInHand()!=null && player.getItemInHand().getType()!= Material.AIR){
                                File itemFile = new File(itemsFile,itemsId);
                                YamlConfiguration yml = YamlConfiguration.loadConfiguration(itemFile);
                                yml.set(String.valueOf(System.currentTimeMillis()),player.getItemInHand());
                                try {
                                    yml.save(itemFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                player.sendMessage("§7[§fVexOnlineTime§7] §a道具保存至§f[§c"+args[2]+"§f]§a成功!");
                            }else{
                                player.sendMessage("§7[§fVexOnlineTime§7] §5手持道具为空,不可保存!");
                            }
                        }else{
                            player.sendMessage("§7[§fVexOnlineTime§7] §5文件不存在,请输入正确名称!");
                        }
                        return true;
                    }
                    if(args.length==3 && args[0].equals("items") && args[1].equals("clear")){
                        String itemsId = args[2]+".yml";
                        File itemsFile = new File(MainPlugin.plugin.getDataFolder(),"Items");
                        List<String> itemsList = Arrays.asList(itemsFile.list());
                        if(itemsList.contains(itemsId)){
                            File itemFile = new File(itemsFile,itemsId);
                            YamlConfiguration yml = YamlConfiguration.loadConfiguration(itemFile);
                            for(String key:yml.getKeys(false)){
                                yml.set(key,null);
                            }
                            try {
                                yml.save(itemFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            player.sendMessage("§7[§fVexOnlineTime§7] §a清空道具集合§f[§c"+args[2]+"§f]§a成功!");
                        }else{
                            player.sendMessage("§7[§fVexOnlineTime§7] §5文件不存在,请输入正确名称!");
                        }
                        return true;
                    }
                    if(args.length==3 && args[0].equals("items") && args[1].equals("delete")){
                        String itemsId = args[2]+".yml";
                        File itemsFile = new File(MainPlugin.plugin.getDataFolder(),"Items");
                        List<String> itemsList = Arrays.asList(itemsFile.list());
                        if(itemsList.contains(itemsId)){
                            File itemFile = new File(itemsFile,itemsId);
                            itemFile.delete();
                            player.sendMessage("§7[§fVexOnlineTime§7] §a删除道具集合§f[§c"+args[2]+"§f]§a成功!");
                        }else {
                            player.sendMessage("§7[§fVexOnlineTime§7] §5文件不存在,请输入正确名称!");
                        }
                        return true;
                    }
                }
            }
            if(args.length==1 && args[0].equals("reload")){
                MainPlugin.loadConfig();
                sender.sendMessage("§7[§fVexOnlineTime§7] §a插件已重载完成~");
                return true;
            }
        }

        return false;
    }
}
