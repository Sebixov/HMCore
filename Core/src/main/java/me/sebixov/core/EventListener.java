package me.sebixov.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class EventListener implements Listener {
    public static ArrayList<Player> AntylogoutPlayers = new ArrayList<>();
    public static BossBar bar;

    @EventHandler
    public static void onMineDiamonds(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.DIAMOND_ORE) {
            String miner = e.getPlayer().getName();
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("xray.admin")) {
                    online.sendMessage("§c[§4Anty X-ray§c] §6Gracz §4" + miner + " §6wykopał rudę diamentów!");
                }
            }
        }
    }

    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§9[§bHardcoreMC§9] §9Gracz §e" + e.getPlayer().getName() + " §9wyszedł z serwera!");
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("§9[§bHardcoreMC§9] §9Gracz §e" + e.getPlayer().getName() + " §9dołączył na serwer!");
    }

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent e) {
        Location loc = e.getPlayer().getLocation();
        e.getPlayer().sendMessage("§9[§bHardcoreMC§9] §6Koordynaty twojej śmierci: §c" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ());
    }
}



