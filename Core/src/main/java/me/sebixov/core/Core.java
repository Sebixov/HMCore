package me.sebixov.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.scheduler.BukkitRunnable;

public final class Core extends JavaPlugin {

    public static Economy econ = new EconomyImplementer();
    public static Core getInstance;
    public EconomyImplementer economyImplementer;

    public DataHandler dataHandler = new DataHandler();
    public WantedImplementer wantedImplementer;
    public LivesImplementer livesImplementer;
    public TablistManager tablistManager;
    private VaultHook vaultHook;

    public final HashMap<UUID,Double> playerBank = new HashMap<>();
    public final HashMap<UUID, Boolean> muteBank = new HashMap<>();
    public final HashMap<UUID, Integer> livesBank = new HashMap();

    public final ArrayList<String> globalData = new ArrayList<>();

    public int wantedRewards;
    public int time;

    private void instanceClasses() {
        getInstance = this;
        economyImplementer = new EconomyImplementer();
        dataHandler = new DataHandler();
        vaultHook = new VaultHook();
    }

    @Override
    public void onEnable() {
        runOnEnable();
        dataHandler.loadGlobalData();

        File dir = new File("plugins/Core");
        if (!dir.exists()){
            new File("plugins/Core").mkdir();
        }
        File playerData = new File("plugins/Core/PlayerData");
        if (!playerData.exists()){
            new File("plugins/Core/PlayerData").mkdir();
        }

        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new DataHandler(), this);
        getServer().getPluginManager().registerEvents(new TablistManager(), this);
        getServer().getPluginManager().registerEvents(new LivesEvents(), this);
        getServer().getPluginManager().registerEvents(new WantedEvents(), this);
        getServer().getPluginManager().registerEvents(new DragonEvents(), this);

        BukkitRunnable countdown = (BukkitRunnable) new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                if (time == 24){
                    time = 0;
                }
            }
        }.runTaskLaterAsynchronously(getInstance, 20*60*60);

        Bukkit.getServer().getLogger().info("|---------Plugin enabled---------|");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
        dataHandler.saveGlobalData();
        Bukkit.getServer().getLogger().info("|---------Plugin disabled--------|");
    }
    public void runOnEnable(){
        instanceClasses();
        vaultHook.hook();
        EconomyCommands ecmd = new EconomyCommands();
        this.getCommand("konto").setExecutor(ecmd);
        this.getCommand("acc").setExecutor(ecmd);
    }
}
