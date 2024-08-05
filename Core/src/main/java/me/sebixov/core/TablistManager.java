package me.sebixov.core;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TablistManager implements Listener {

    Core plugin = Core.getInstance;

    public void setTablist(Player player){
        player.setPlayerListHeader("§bHardcoreMC");
        player.setPlayerListFooter("§cPozostałe życia: " + plugin.livesBank.get(player.getUniqueId()));
    }

    public void refreshList(Player player){
        player.setPlayerListHeader("§bHardcoreMC");
        player.setPlayerListFooter("§cPozostałe życia: " + plugin.livesBank.get(player.getUniqueId()));
    }
    //TODO MUlTIPLE LINES OF TAB
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e){
        setTablist(e.getPlayer());
    }
}
