package me.KeybordPiano459.AntiCurse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Jail implements Listener {
	AntiCurse plugin;
	public Jail(AntiCurse plugin) {
		this.plugin = plugin;
	}
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		String msg = event.getMessage().toLowerCase();
		for (String badWord : plugin.getConfig().getStringList("blacklisted-words")) {
			if (!player.hasPermission("anticurse.exempt")) {
				if (msg.contains(badWord)) {
					event.setCancelled(true);
					double x = plugin.getConfig().getInt("jail-x");
					double y = plugin.getConfig().getInt("jail-y");
					double z = plugin.getConfig().getInt("jail-z");
					if (x != 0 && y != 0 && z != 0 ) {
						final Location loc = player.getLocation();
						final GameMode gamemode = player.getGameMode();
						player.teleport(new Location(Bukkit.getServer().getWorld(plugin.getConfig().getString("jail-world")), x, y, z));
						player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] You have been jailed for cursing.");
						player.setGameMode(GameMode.ADVENTURE);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								player.teleport(loc);
								player.setGameMode(gamemode);
								player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] You have been released!");
							}
						}, plugin.getConfig().getInt("jail-time")*60*20);
					} else {
						player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] The jail hasn't been set yet. Type /jail for help.");
					}
					Player[] players = plugin.getServer().getOnlinePlayers();
					for (Player p : players) {
						if (p.isOp() || p.hasPermission("anticurse.notify")) {
							p.sendMessage("[" + ChatColor.RED + "AntiHax" + ChatColor.RESET + "] " + plugin.getConfig().getString("admin-msg").replaceAll("%PLAYER%", player.getDisplayName()));
						}
					}
					if (plugin.getConfig().getString("broadcast-msg") == "null") {
						return;
					} else {
						Bukkit.broadcastMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] " + plugin.getConfig().getString("broadcast-msg").replaceAll("%PLAYER%", player.getDisplayName()));
					}
				}
			}
		}
	}
}