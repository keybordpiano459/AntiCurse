package me.KeybordPiano459.AntiCurse;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiCurse extends JavaPlugin {	
	public void onEnable() {
		getLogger().info("AntiCurse 1.1 has been enabled!");
		if (getConfig().getString("deal-with-cursing").equalsIgnoreCase("KICK")) {
			getServer().getPluginManager().registerEvents(new Kick(this), this);
		} else if (getConfig().getString("deal-with-cursing").equalsIgnoreCase("JAIL")) {
			getServer().getPluginManager().registerEvents(new Jail(this), this);
		}
		getServer().getPluginCommand("jail").setExecutor(new CommandJail(this));
		
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void onDisable() {
		getLogger().info("AntiCurse 1.1 has been disabled.");
	}
}
