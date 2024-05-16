package com.nennneko5787.jpcraft.events;

import com.nennneko5787.jpcraft.JPCraftLobbyPluginMain;
import com.nennneko5787.jpcraft.utils.ColorUtil;
import org.bukkit.Bukkit;

import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;

public class onPlayerChatEvent implements Listener {
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Integer chatcount;
		if (JPCraftLobbyPluginMain.chatCount.containsKey(e.getPlayer().getUniqueId())){
			chatcount = JPCraftLobbyPluginMain.chatCount.get(e.getPlayer().getUniqueId());
		}else{
			chatcount = 0;
		}
		Bukkit.getServer().getLogger().info("Chat "+chatcount);
		if (chatcount > JPCraftLobbyPluginMain.config.getInt("anti-spam.chat-count",6)-1) {
			e.setCancelled(true);
			if (JPCraftLobbyPluginMain.config.getBoolean("anti-spam.notifications.enable", true)) {
				String message = JPCraftLobbyPluginMain.config.getString("anti-spam.notifications.message")
					.replace("{{counter-reset}}", JPCraftLobbyPluginMain.config.getString("anti-spam.counter-reset", "10"))
					.replace("{{chat-count}}", JPCraftLobbyPluginMain.config.getString("anti-spam.chat-count", "6"));
				String parsed = ColorUtil.replaceColorCode(message);
				e.getPlayer().sendMessage(parsed);
			}
		}
		Bukkit.getServer().getLogger().info("Chat "+chatcount);
		final Integer _count = chatcount + 1;
		JPCraftLobbyPluginMain.chatCount.put(e.getPlayer().getUniqueId(), _count);
	}
}
