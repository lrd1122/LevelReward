package gx.lrd1122;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import sun.swing.SwingUtilities2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LevelList {
    public static List<String> GetLevelList()
    {
        ConfigurationSection section = LevelReward.config.getConfigurationSection("Rewards");
        return new ArrayList<>(section.getKeys(true));
    }
}
