package me.sebixov.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class WantedEvents  implements Listener {

    Core plugin = Core.getInstance;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if (e.getPlayer().getKiller() != null) {
            if (!(plugin.economyImplementer.getBalance(e.getPlayer()) < 0)) {
                plugin.wantedImplementer.reward(e.getPlayer(), e.getPlayer().getKiller());
            }
            else {
                e.getPlayer().getKiller().sendMessage("§cGracz, którego zabiłeś nie posiada pieniędzy!");
            }
        }
    }
}
