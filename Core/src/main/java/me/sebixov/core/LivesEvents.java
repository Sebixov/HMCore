package me.sebixov.core;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class LivesEvents implements Listener {
    Core plugin = Core.getInstance;
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        plugin.livesImplementer.addLives(e.getPlayer().getUniqueId(), -1);
        plugin.tablistManager.refreshList(e.getPlayer());
        if (plugin.livesImplementer.getLives(e.getPlayer().getUniqueId()) == 0) {
            plugin.livesBank.replace(e.getPlayer().getUniqueId(), 1);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/tempban " + e.getPlayer().getName() + " 1d Straciłeś wszytskie życia!");
            plugin.tablistManager.refreshList(e.getPlayer());
        }
    }
}
