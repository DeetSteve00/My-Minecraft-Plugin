package me.deetsteve00.dev.beta;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Main extends JavaPlugin {
	
	private PlayerEventListener listened = new PlayerEventListener(this);
	public static Configuration conf;
	
	public static boolean canExplode;
	public static boolean sneakBoom;
	public static int moshApples;
	public static long askInterval;
	
	public void loadConfigs() {
		conf.load();
		canExplode = conf.getBoolean("cancel-explosions", true);
		sneakBoom = conf.getBoolean("sneak-explosion", false);
		moshApples = conf.getInt("mosh-default", 10);
		askInterval = conf.getInt("word-interval", 1200);
		saveConfig();
	}
	public void saveConfig() {
		conf.setProperty("cancel-explosions", canExplode);
		conf.setProperty("mosh-default", moshApples);
		conf.setProperty("sneak-explosion", sneakBoom);
		conf.setProperty("word-interval", askInterval);
		conf.save();
	}
	
	@Override
	public void onEnable() {
		conf = getConfiguration();
		if(!getDataFolder().exists()) getDataFolder().mkdirs();
		loadConfigs();
		
		new commands(this);
		getServer().getPluginManager().registerEvents(listened, this);
		Bukkit.getLogger().info("[DeetPlugin] Deet's plugin loaded successfully.");
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger().info("[DeetPlugin] Goodbye!");
	}
}
