package gx.lrd1122;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class GetReward {
    public static void GetReward(Player player, String getname) {
        try {
            ConfigurationSection onesection = LevelReward.config.getConfigurationSection("Rewards");
            ConfigurationSection section = onesection.getConfigurationSection(getname);
            int maxlevel = section.getInt("MaxLevel");
            int minlevel = section.getInt("MinLevel");
            List<String> getreward = section.getStringList("Commands");
            if (player.getLevel() >= minlevel && player.getLevel() <= maxlevel) {
                if (LevelReward.playerdata.get(player.getName()) == null) {
                    FileConfiguration playerdata = LevelReward.playerdata;
                    playerdata.set(player.getName(), "");
                    try {
                        playerdata.save(new File(LevelReward.plugin.getDataFolder(), "playerdata.yml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    File playerdatafile = new File(LevelReward.plugin.getDataFolder(), "playerdata.yml");
                    LevelReward.playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
                }
                String data = String.valueOf(LevelReward.playerdata.get(player.getName()));
                if (!getdata(data, getname)) {
                    for (int i = 0; i < getreward.size(); i++) {
                        String getcommand = getreward.get(i);
                        String[] commandfix = getcommand.split(":");
                        String holder = LevelReward.PlaceholderAPI != null ? PlaceholderAPI.setPlaceholders(player, commandfix[1]) : commandfix[1];
                        FileConfiguration playerdata = LevelReward.playerdata;
                        String dataset = data + getname + ",";
                        playerdata.set(player.getName(), dataset);
                        try {
                            playerdata.save(new File(LevelReward.plugin.getDataFolder(), "playerdata.yml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        File playerdatafile = new File(LevelReward.plugin.getDataFolder(), "playerdata.yml");
                        LevelReward.playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
                        if (commandfix[0].equals("player")) {
                            player.performCommand(holder);
                        }
                        if (commandfix[0].equals("op")) {
                            if (!player.isOp()) {
                                player.setOp(true);
                                player.performCommand(holder);
                                player.setOp(false);
                            } else {
                                player.performCommand(holder);
                            }
                        }
                        if (LevelReward.config.getBoolean("LevelConsume")) {
                            player.setLevel(player.getLevel() - Integer.valueOf(getname));
                        }

                    }
                }
                if (getdata(data, getname)) {
                    player.sendMessage(LevelReward.colorstring(LevelReward.config.getString("prefix") + LevelReward.config.getString("HasRewardMessage")));
                }
            }
        } catch (Exception e) {
            player.sendMessage(LevelReward.colorstring(LevelReward.config.getString("prefix") + LevelReward.config.getString("NotFoundMessage")));
        }
    }
    public static boolean getdata(String string,String current)
    {
        String[] data = string.split(",");
        for(int i = 0; i< data.length; i++)
        {
            if(current.equals(data[i]))
            {
                return true;
            }
        }
        return false;
    }
    public static boolean PlayerHasReward(Player player, String levelname)
    {
        String data = String.valueOf(LevelReward.playerdata.get(player.getName()));
        return getdata(data, levelname);
    }
}
