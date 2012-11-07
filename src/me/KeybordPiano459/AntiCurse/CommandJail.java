package me.KeybordPiano459.AntiCurse;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJail implements CommandExecutor {
	AntiCurse plugin;
	public CommandJail(AntiCurse plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Logger log = Logger.getLogger("Minecraft");
		if (cmd.getName().equalsIgnoreCase("jail")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					player.sendMessage(ChatColor.RED + "AntiCurse Jail Commands:");
					player.sendMessage("- " + ChatColor.GREEN + "/jail [set]");
					player.sendMessage("- " + ChatColor.GREEN + "/jail [player] [name]");
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("set")) {
						Location loc = player.getLocation();
						plugin.getConfig().set("jail-x", loc.getX());
						plugin.getConfig().set("jail-y", loc.getY());
						plugin.getConfig().set("jail-z", loc.getZ());
						plugin.getConfig().set("jail-world", player.getWorld());
						player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] You have successfully set the jail!");
					} else if (args[0].equalsIgnoreCase("player")) {
						player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] Incorrect usage! Type /jail for help.");
					} else {
						player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] Incorrect usage! Type /jail for help.");
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("player")) {
						if (sender.getServer().getPlayer(args[0]) != null) {
							final Player targetplayer = sender.getServer().getPlayer(args[0]);
							double x = plugin.getConfig().getInt("jail-x");
							double y = plugin.getConfig().getInt("jail-y");
							double z = plugin.getConfig().getInt("jail-z");
							if (x != 0 && y != 0 && z != 0 ) {
								final Location loc = targetplayer.getLocation();
								final GameMode gamemode = targetplayer.getGameMode();
								targetplayer.teleport(new Location(Bukkit.getServer().getWorld(plugin.getConfig().getString("jail-world")), x, y, z));
								targetplayer.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] You have been jailed by " + player.getDisplayName());
								targetplayer.setGameMode(GameMode.ADVENTURE);
								player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] You have successfully jailed " + targetplayer.getDisplayName());
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									public void run() {
										targetplayer.teleport(loc);
										targetplayer.setGameMode(gamemode);
										targetplayer.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] You have been released!");
									}
								}, plugin.getConfig().getInt("jail-time")*60*20);
							} else {
								player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] The jail hasn't been set yet. Type /jail for help.");
							}
						} else {
							player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] That player may be offline.");
						}
					} else {
						player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] Incorrect usage! Type /jail for help.");
					}
				} else {
					player.sendMessage("[" + ChatColor.RED + "AntiCurse" + ChatColor.RESET + "] Incorrect usage! Type /jail for help.");
				}
			} else {
				log.info("AntiCurse Jail Commands:");
			}
		}
		return false;
	}
}