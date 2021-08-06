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
            GetReward.GetReward(player, getname);

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
}
