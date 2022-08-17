package me.deetsteve00.dev.beta;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import me.deetsteve00.dev.beta.Main;

public class PlayerEventListener implements Listener {
	private Main plugin;
	
	Random rand = new Random();
	private String randStrings[] = {
		Bukkit.getServerName(),
		"[1]",
		"Dog",
		"Cat",
		"Minceraft", // Yes
		"Minecraft",
		"Pizza",
		"Hamburger",
		"Pasta",
		"Ramen"
	};
	private int whichString = rand.nextInt(randStrings.length);
	private boolean canWin = false;
	private boolean askAgain = true;
	
	public PlayerEventListener(Main instance) {
		this.plugin = instance;
	}
	
	@EventHandler
	public void onBed(PlayerBedEnterEvent e) {
		Bukkit.broadcastMessage(ChatColor.YELLOW + e.getPlayer().getName() + " is sleeping");
	}
	
	@EventHandler
	public void onLeaveBed(PlayerBedLeaveEvent e) {
	/*
		 * It's cool that is was an actual feature in Minecraft Pocket Edition 
	*/
    	Player p = e.getPlayer();
		Bukkit.getLogger().info("Regenerated " + p.getName() + "'s health.");
		p.setHealth(20);
	}
	
	@EventHandler
	public void onDamage(ExplosionPrimeEvent e) {
		if(Main.canExplode) {
			e.setRadius(0);
			e.getEntity().remove();
		}
		else return;
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		if(!Main.sneakBoom) return;
		Location tnt = e.getPlayer().getLocation();
		tnt.setY(tnt.getY()-2);
		e.getPlayer().getWorld().createExplosion(tnt, 3);
	}
	
	@EventHandler
	public void onEntitysDeath(EntityDeathEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player loss = (Player) e.getEntity();
		Bukkit.broadcastMessage(loss.getName() + " has been found dead");
		
		return;
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent e) {
		if(e.getMessage().equalsIgnoreCase(randStrings[whichString]) && canWin) {
			Bukkit.getLogger().info("Player: " + e.getPlayer().getName() + " has said \"" + randStrings[whichString] + "\" first." );
			Bukkit.broadcastMessage(e.getPlayer().getName() + " won!!!");
			canWin = false;
		}
	}
	@EventHandler
	public void onTime(PlayerMoveEvent e) {		
		if(e.getPlayer().getWorld().getTime() % Main.askInterval != 0) {
			askAgain = true;
			return;
		}
		if(!askAgain) return;
		askAgain = false;
		canWin = true;
		randStrings[1] = e.getPlayer().getName();
		whichString = rand.nextInt(randStrings.length);
		
		Bukkit.getLogger().info("Player to say " + randStrings[whichString] + " will be set to win.");
		Bukkit.broadcastMessage("First to say " + ChatColor.YELLOW + randStrings[whichString] + ChatColor.WHITE +" wins!!.");
	}
	
}