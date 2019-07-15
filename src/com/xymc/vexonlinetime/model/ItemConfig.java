package com.xymc.vexonlinetime.model;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemConfig {
    private static Map<String,ItemConfig> itemConfigMap = new HashMap<>();
    private List<ItemStack> itemStackList;

    public static Map<String, ItemConfig> getItemConfigMap() {
        return itemConfigMap;
    }

    public static void setItemConfigMap(Map<String, ItemConfig> itemConfigMap) {
        ItemConfig.itemConfigMap = itemConfigMap;
    }

    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }

    public void setItemStackList(List<ItemStack> itemStackList) {
        this.itemStackList = itemStackList;
    }

    @Override
    public String toString() {
        return "ItemConfig{" +
                "itemStackList=" + itemStackList +
                '}';
    }
}
