package me.KeybordPiano459.AntiCurse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Kick implements Listener {
	AntiCurse plugin;
	public Kick(AntiCurse plugin) {
		this.plugin = plugin;
	}
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String msg = event.getMessage().toLowerCase();
		for(String badWord : plugin.getConfig().getStringList("blacklisted-words")) {
			if (!player.hasPermission("anticurse.exempt")) {
				if(msg.contains(badWord)) {
		        	event.setCancelled(true);
		        	player.kickPlayer(plugin.getConfig().getString("kick-msg").replaceAll("%PLAYER%", player.getDisplayName()));
		        	Player[] players = plugin.getServer().getOnlinePlayers();
	        		for (Player p : players) {
	        			if (p.isOp() || p.hasPermission("anticurse.notify")) {
	        				p.sendMessage("[" + ChatColor.RED + "AntiHax" + ChatColor.RESET + "] " + plugin.getConfig().getString("admin-msg").replaceAll("%PLAYER%", player.getDisplayName()));
	        			}
	        		}
		        	if (plugin.getConfig().getString("broadcast-msg") == "null") {
		        		return;
		        	} else {
		        		Bukkit.broadcastMessage(plugin.getConfig().getString("broadcast-msg").replaceAll("%PLAYER%", player.getDisplayName()));
		        	}
		    	}
			}
		}
	}
}
