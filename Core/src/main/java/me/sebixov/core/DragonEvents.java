package me.sebixov.core;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DragonEvents implements Listener {

    Core plugin = Core.getInstance;
    int dragonLevel;

     @EventHandler
    public void onDragonDeath(EntityDeathEvent e){
         if (e.getEntityType() == EntityType.ENDER_DRAGON){
             dragonLevel = Integer.parseInt(plugin.globalData.get(0));
             dragonLevel++;
         }
     }
     //TODO ELYTRA DROP
     @EventHandler
     public void onDragonRespawn(CreatureSpawnEvent e){
         if (e.getEntityType() == EntityType.ENDER_DRAGON){
             e.getEntity().setHealth(50 * dragonLevel + 200);
             e.getEntity().setCustomName("Smok lvl. " + dragonLevel);
             e.getEntity().setCustomNameVisible(true);
         }
     }
}
