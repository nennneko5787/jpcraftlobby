package com.nennneko5787.jpcraft.commands;

import com.nennneko5787.jpcraft.JPCraftLobbyPluginMain;

import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JPCraftLobbyCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getServer().getLogger().info(String.valueOf(args));
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    sender.sendMessage("[JPCraftLobby] plugin is reloading now...");
                    JPCraftLobbyPluginMain.chatCounterTask.cancel();
                    Bukkit.getPluginManager().getPlugin("JPCraftLobby").reloadConfig();
                    JPCraftLobbyPluginMain.config = Bukkit.getPluginManager().getPlugin("JPCraftLobby").getConfig();
                    int intervalSeconds = JPCraftLobbyPluginMain.config.getInt("anti-spam.counter-reset", 10);
                    JPCraftLobbyPluginMain.chatCounterTask.runTaskTimer(Bukkit.getServer().getPluginManager().getPlugin("JPCraftLobby"), 0, intervalSeconds*20);
                    sender.sendMessage("[JPCraftLobby] plugin reloaded successful.");
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}
