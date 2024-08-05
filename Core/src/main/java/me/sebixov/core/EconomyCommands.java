package me.sebixov.core;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


public class EconomyCommands implements CommandExecutor {

    private final Core plugin = Core.getInstance;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] arg) {
        if (!(sender instanceof Player senderP)){
            sender.sendMessage("Only players can do this!");
            return true;
        }
        if (!plugin.playerBank.containsKey(senderP.getUniqueId())){
            plugin.playerBank.put(senderP.getUniqueId(), (double) 0);
        }
        if (cmd.getName().equalsIgnoreCase("konto")) {
            if (arg.length == 0) {
                try {
                    Player player = (Player) sender;
                    double balance = plugin.economyImplementer.getBalance(player);
                    sender.sendMessage("§aTwój stan konta: " + balance);
                    return true;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (arg.length == 1){
                try {
                    Player player = Bukkit.getPlayerExact(arg[0]);
                    assert player != null;
                    double balance = plugin.economyImplementer.getBalance(player);
                    sender.sendMessage("§aStan konta gracza "+player.getName() + ": " + balance);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                sender.sendMessage("§cNiepoprawne użycie! Użyj: /konto [<gracz>]");
            }
        }
        if (cmd.getName().equalsIgnoreCase("acc")){
            if (sender.hasPermission("economy.acc")){
                if (arg.length == 2){
                    if (arg[0].equalsIgnoreCase("add")){
                        try {
                            int amount = Integer.parseInt(arg[1]);
                            plugin.economyImplementer.depositPlayer(sender.getName(), amount);
                            sender.sendMessage("§2[§aEkonomia§2] §aDodano " + amount + " do twojego konta!");
                            sender.sendMessage("§2[§aEkonomia§2] §aTwój stan konta: " + plugin.economyImplementer.getBalance((Player) sender));
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    if (arg[0].equalsIgnoreCase("remove")) {
                        try {
                            int amount = Integer.parseInt(arg[1]) * -1;
                            plugin.economyImplementer.depositPlayer(sender.getName(), amount);
                            sender.sendMessage("§2[§aEkonomia§2] §aUsunięto " + amount * -1 + " z twojego konta!");
                            sender.sendMessage("§2[§aEkonomia§2] §aTwój stan konta: " + plugin.economyImplementer.getBalance((Player) sender));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (arg[0].equalsIgnoreCase("set")){
                        try {
                            double balance = plugin.economyImplementer.getBalance((Player) sender) * -1;
                            plugin.economyImplementer.depositPlayer((Player) sender, balance);
                            int set = Integer.parseInt(arg[1]);
                            plugin.economyImplementer.depositPlayer((Player) sender, set);
                            sender.sendMessage("§2[§aEkonomia§2] §aUstawiono stan konta na: " + set);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                if (arg.length == 3) {
                    if (arg[0].equalsIgnoreCase("add")) {
                        try {
                            int deposit = Integer.parseInt(arg[1]);
                            Player target = Bukkit.getPlayerExact(arg[2]);
                            assert target != null;
                            plugin.economyImplementer.depositPlayer(target, deposit);
                            sender.sendMessage("§2[§aEkonomia§2] §aDodano " + deposit + " do konta gracza " + target.getName() + "!");
                            if (target.isOnline()) {
                                target.sendMessage("§2[§aEkonomia§2] §aAdministrator " + sender.getName() + " dodał" + deposit
                                + "do twojego konta!");
                                target.sendMessage("§2[§aEkonomia§2] §aTwój stan konta: " + plugin.economyImplementer.getBalance(target));
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (arg[0].equalsIgnoreCase("remove")) {
                        try {
                            int deposit = Integer.parseInt(arg[1]) * -1;
                            Player target = Bukkit.getPlayerExact(arg[2]);
                            assert target != null;
                            plugin.economyImplementer.depositPlayer(target, deposit);
                            sender.sendMessage("§2[§aEkonomia§2] §aZ kąta gracza " + target.getName() + " usunięto " + deposit * - 1);
                            if (target.isOnline()) {
                                target.sendMessage("§2[§aEkonomia§2] §aAdministrator §c" + sender.getName() + " §ausunął " + deposit +
                                        " z twojego konta!");
                                target.sendMessage("§2[§aEkonomia§2] §aTwój stan konta: " + plugin.economyImplementer.getBalance(target) + "!");
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (arg[0].equalsIgnoreCase("set")) {
                        try {
                            int set = Integer.parseInt(arg[1]);
                            Player target = Bukkit.getPlayerExact(arg[2]);
                            int balance = (int) plugin.economyImplementer.getBalance(target) * -1;
                            plugin.economyImplementer.depositPlayer(target, balance);
                            plugin.economyImplementer.depositPlayer(target, set);
                            sender.sendMessage("§2[§aEkonomia§2] §aAktualny stan konta gracza " + target.getName() + ": " +
                                    plugin.economyImplementer.getBalance(target));
                            if (target.isOnline()) {
                                target.sendMessage("§2[§aEkonomia§2] §aAdministrator §c" + sender.getName() + " §austawił stan twojego konta" +
                                        "na " + set + "!");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return true;
    }//TODO PAY COMMAND
}
