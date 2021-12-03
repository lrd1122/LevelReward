package gx.lrd1122;

import gx.lrd1122.ConfigReader.Config;
import gx.lrd1122.ConfigReader.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelReward extends JavaPlugin {
    public static YamlConfiguration playerdata;
    public static JavaPlugin plugin;
    public static FileConfiguration config;
    public static Plugin PlaceholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        PlayerData.Load();
        Config.checkConfig();
        if (PlaceholderAPI != null) {
            getLogger().info("PlaceholderAPI √");
        } else {
            getLogger().info("PlaceholderAPI ×");
        }
        Bukkit.getPluginManager().registerEvents(new LevelListener(), this);
        Bukkit.getPluginCommand("levelreward").setExecutor(new CommandListener());
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
