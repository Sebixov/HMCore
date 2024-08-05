package me.sebixov.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class DataHandler implements Listener {
    private Core plugin = Core.getInstance;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        try {
            onEnable(e.getPlayer());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        try {
            onDisable(e.getPlayer());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void onEnable(Player player) throws IOException {
        UUID uuid = player.getUniqueId();
        String playerPath = "plugins/Core/PlayerData/" + player.getName() + ".txt";
        File f = new File(playerPath);
        ArrayList<String> playerData = new ArrayList<>();
        if (f.exists()){
            String line;
            try {
                BufferedReader br = new BufferedReader(new FileReader(playerPath));
                while ((line = br.readLine()) != null){
                    String[] dataItems = line.split(",");
                    for (String item : dataItems) {
                        playerData.add(item);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        if (!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            playerData.add("100"); //Balance
            playerData.add("3"); //Lives
            String res = "";
            res = String.join(",", playerData);
            write(playerPath, res, "w");
        }
        plugin.economyImplementer.registerAccount(uuid, Double.parseDouble(playerData.get(0)));
        plugin.livesBank.put(uuid, Integer.parseInt(playerData.get(2)));
        plugin.muteBank.put(uuid, Boolean.parseBoolean(playerData.get(3)));
        Bukkit.getServer().getLogger().info("[DataHandler] Data loaded for player " + player.getName());
    }
    public void onDisable(Player player) throws IOException{
        String playerPath = "plugins/Core/PlayerData/" + player.getName() + ".txt";
        UUID uuid = player.getUniqueId();
        ArrayList<String> playerData = new ArrayList<>();
        double bal = plugin.playerBank.get(uuid);
        int lives = plugin.livesBank.get(uuid);
        playerData.add(String.valueOf(bal));
        playerData.add(String.valueOf(lives));
        String res = "";
        res = String.join(",", playerData);
        write(playerPath, res, "w");
    }
    public void loadGlobalData(){
        File dir = new File("plugins/Core/global_data.txt");
        if (!dir.exists()){
            new File("plugins/Core/global_data.txt");
            try {
                dir.createNewFile();
                plugin.globalData.add("1");
                String res = "";
                res = String.join(",", plugin.globalData);
                write("plugins/Core/global_data.txt", res, "w");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (dir.exists()){
            String line;
            try {
                BufferedReader br = new BufferedReader(new FileReader("plugins/Core/global_data.txt"));
                while ((line = br.readLine()) != null){
                    String[] dataItems = line.split(",");
                    for (String item : dataItems) {
                        plugin.globalData.add(item);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void saveGlobalData(){
        String path = "plugins/Core/global_data.txt";
        String res = "";
        res = String.join(",", plugin.globalData);
        write(path, res, "w");
    }
    public void write(String path, String Content, String mode){
        try {
            if (mode.equals("w")) {
                FileWriter writer = new FileWriter(path);
                writer.write(Content);
                writer.close();
            }
            if (mode.equals("a")) {
                File file = new File(path);
                FileWriter fr = new FileWriter(file, true);
                fr.append(Content);

                fr.close();
            }
        }
        catch (IOException i){
            i.printStackTrace();
        }
    }
}
