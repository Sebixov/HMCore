package me.sebixov.core;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class WantedImplementer {

    Core plugin = Core.getInstance;

    public void reward(Player attacker, Player victim){
        UUID uuidVictim = victim.getUniqueId();
        double balanceVictim = plugin.playerBank.get(uuidVictim);
        double reward = balanceVictim * 0.1;
        plugin.wantedRewards ++;
        plugin.economyImplementer.depositPlayer(attacker, reward);
        attacker.sendMessage("§2[§aEkonomia§2] §aOtrzymałeś nagrodę §c" + reward);
        victim.sendMessage("§2[§aEkonomia§2] §aUtraciłeś §c" + reward);
    }
}
