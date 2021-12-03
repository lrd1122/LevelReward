package gx.lrd1122.ConfigReader;

import gx.lrd1122.LevelReward;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class PlayerData {
    public static void Load()
    {
        JavaPlugin plugin = LevelReward.plugin;
        File playerdatafile = new File(plugin.getDataFolder(), "playerdata.yml");
        if(playerdatafile.exists()) {
            plugin.getLogger().info("玩家数据已成功加载");
        }
        else {
            try {
                playerdatafile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            plugin.getLogger().info("载入玩家数据");
        }
        LevelReward.playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
    }
}
