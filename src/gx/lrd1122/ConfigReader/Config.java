package gx.lrd1122.ConfigReader;

import gx.lrd1122.LevelReward;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {
    public static void checkConfig()
    {
        List<String> list = new ArrayList<>();
        list.add("ReachedLevelMax:\"你已经超过了等级\"");
        list.add("ReachedLevelMin:\"你没有到等级\"");
        updateOption(list);
    }
    public static void updateOption(List<String> stringList)
    {
        JavaPlugin plugin = LevelReward.plugin;
        for(int i = 0 ; i < stringList.size(); i++) {
            String[] out = stringList.get(i).split(":");
            String key = out[0];
            if (plugin.getConfig().get(key) == null) {
                String value = out[1];
                plugin.getConfig().set(key, value);
            }
        }
    }
}
