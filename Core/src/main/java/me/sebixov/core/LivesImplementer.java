package me.sebixov.core;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class LivesImplementer {
    Core plugin = Core.getInstance;

    public int getLives(UUID uuid){
        int lives;
        lives = plugin.livesBank.get(uuid);
        return lives;
    }
    public int getLives(Player player){
        int lives;
        UUID uuid = player.getUniqueId();
        lives = plugin.livesBank.get(uuid);
        return lives;
    }
    public void addLives(UUID uuid, int add){
        int lives = plugin.livesBank.get(uuid) + add;
        plugin.livesBank.replace(uuid, lives);
    }
    public void addLives(Player player, int add){
        UUID uuid = player.getUniqueId();
        int lives = plugin.livesBank.get(uuid) + add;
        plugin.livesBank.replace(uuid, lives);
    }
}
