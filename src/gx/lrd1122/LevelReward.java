package gx.lrd1122;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class LevelReward extends JavaPlugin {
    public static YamlConfiguration playerdata;
    public static JavaPlugin plugin;
    public static FileConfiguration config;
    public static Plugin PlaceholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
    @Override
    public void onEnable() {
        saveDefaultConfig();
        File playerdatafile = new File(getDataFolder(), "playerdata.yml");
        if(playerdatafile.exists()) {
            getLogger().info("玩家数据已成功加载");
        }
        else {
            try {
                playerdatafile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getLogger().info("载入玩家数据");
        }
        playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
        if (PlaceholderAPI != null) {
            getLogger().info("PlaceholderAPI √");
        } else {
            getLogger().info("PlaceholderAPI ×");
        }
        Bukkit.getPluginManager().registerEvents(new LevelListener(), this);
        Bukkit.getPluginCommand("levelreward").setExecutor(new CommandListener());
        plugin = this;
        config = getConfig();
        getLogger().info("插件已加载,Bug反馈+QQ1794325461");
    }

    @Override
    public void onDisable() {
        getLogger().info("插件已卸载,Bug反馈+QQ1794325461");
    }
    public static String colorstring(String string)
    {
        return string.replace("&", "§");
    }
}
