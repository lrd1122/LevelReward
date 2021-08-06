package gx.lrd1122;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.util.List;

public class LevelListener implements Listener {
    @EventHandler
    public void AutoReward(PlayerLevelChangeEvent event)
    {
        if(LevelReward.config.getBoolean("AutoReward")) {
            try {

                Player player = event.getPlayer();
                for (int i = 0; i < LevelList.GetLevelList().size(); i++) {
                    ConfigurationSection section1 = LevelReward.config.getConfigurationSection("Rewards");
                    ConfigurationSection section2 = section1.getConfigurationSection(LevelList.GetLevelList().get(i));
                    int maxlevel = section2.getInt("MaxLevel");
                    int minlevel = section2.getInt("MinLevel");
                    if (player.getLevel() >= minlevel && player.getLevel() <= maxlevel && !GetReward.PlayerHasReward(player, LevelList.GetLevelList().get(i))) {
                        GetReward.GetReward(player, LevelList.GetLevelList().get(i));
                    }

                }
            }
            catch (Exception e){}
        }
    }
}
