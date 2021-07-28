package gx.lrd1122;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CommandListener implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length < 1)
        {
            commandSender.sendMessage(LevelReward.colorstring("&7========" + LevelReward.config.getString("prefix") + "&7========" +
                    "\n&a/levelreward reward <Level> 领取等级礼包 &7lr.reward" +
                    "\n&a/levelreward reload 重载插件 &7lr.reload"));
            return true;
        }
        if(strings[0].equals("reward") && strings.length == 2 && commandSender.hasPermission("lr.reward")) {
            String getname = strings[1];
            Player player = (Player) commandSender;
            try
            {
                ConfigurationSection onesection = LevelReward.config.getConfigurationSection("Rewards");
                ConfigurationSection section = onesection.getConfigurationSection(getname);
                int maxlevel = section.getInt("MaxLevel");
                int minlevel = section.getInt("MinLevel");
                List<String> getreward = section.getStringList("Commands");
                if(player.getLevel() >= minlevel && player.getLevel() <= maxlevel) {
                    if(LevelReward.playerdata.get(player.getName())== null) {
                        FileConfiguration playerdata = new YamlConfiguration();
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
                    if(!getdata(data, getname)) {
                        for (int i = 0; i < getreward.size(); i++) {
                            String getcommand = getreward.get(i);
                            String[] commandfix = getcommand.split(":");
                            String holder = LevelReward.PlaceholderAPI != null ? PlaceholderAPI.setPlaceholders(player, commandfix[1]) : commandfix[1];
                            FileConfiguration playerdata = new YamlConfiguration();
                            String dataset = data + getname + ",";
                            playerdata.set(player.getName(), dataset);
                            File playerdatafile = new File(LevelReward.plugin.getDataFolder(), "playerdata.yml");
                            LevelReward.playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
                            try {
                                playerdata.save(new File(LevelReward.plugin.getDataFolder(), "playerdata.yml"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
                            if(LevelReward.config.getBoolean("LevelConsume"))
                            {
                                player.setLevel(player.getLevel() - Integer.valueOf(getname));
                            }

                        }
                    }
                    if(getdata(data, getname)){
                        player.sendMessage(LevelReward.colorstring(LevelReward.config.getString("prefix") + LevelReward.config.getString("HasRewardMessage")));
                    }
                }
            }
            catch (Exception e)
            {
                player.sendMessage(LevelReward.colorstring(LevelReward.config.getString("prefix") + LevelReward.config.getString("NotFoundMessage")));
            }

            return true;
        }
        if(strings[0].equals("reload") && commandSender.hasPermission("lr.reload"))
        {
            LevelReward.plugin.reloadConfig();
            LevelReward.config = LevelReward.plugin.getConfig();
            commandSender.sendMessage(LevelReward.colorstring(LevelReward.config.getString("prefix") + "插件已重载完成"));
            File playerdatafile = new File(LevelReward.plugin.getDataFolder(), "playerdata.yml");
            LevelReward.playerdata = YamlConfiguration.loadConfiguration(playerdatafile);
            return true;
        }
        return false;
    }
    private boolean getdata(String string,String current)
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
}
