package me.deetsteve00.dev.beta;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.deetsteve00.dev.beta.utils;
import me.deetsteve00.dev.beta.Main;

public class commands extends Main {
	
	private Main plugin;
	public commands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("mosh").setExecutor(this);
		plugin.getCommand("hide").setExecutor(this);
		plugin.getCommand("show").setExecutor(this);
		plugin.getCommand("despawn").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*-------------------------Mosh-command------------------*/
		if(cmd.getName().equals("mosh")) {
			
			if(!(sender instanceof Player)) return true;
			ItemStack dee = new ItemStack(Material.APPLE);
			Player victim = (Player) sender;
			
			// Really inefficient code here, really trash, I still can't find any solutions to this, I am really tired.
			if(args.length != 0) {
				if(utils.isNumeric(args[0])) {
					if(Integer.valueOf(args[0]) > 16 || Integer.valueOf(args[0]) < 1) return false;
					dee.setAmount(Integer.valueOf(args[0]));
					sender.sendMessage("Hello " + sender.getName() + ", have some apples.");
					victim.setItemInHand(dee);
				} else {
					dee.setAmount(moshApples);
					victim.setItemInHand(dee);
					return false;
				}
			} else {
				dee.setAmount(moshApples);
				victim.setItemInHand(dee);
				return false;
			}
			return true;
			
		}
		/*---------------------Hide-command----------------------*/
		if(cmd.getName().equals("hide")) {
			
			if(!(sender instanceof Player)) return true;
			Player hider = (Player) sender;
			
			if(!hider.isOp()) {
				hider.sendMessage("No permissions to hide");
				return true;
			}
			if(args.length == 0) {
				hider.sendMessage("You need to enter the player you want to hide from.");
				return false;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			if(target.canSee(hider)) target.hidePlayer(hider);
			hider.sendMessage("Successfully hidden from " + target.getName());
			return true;
		}
		/*--------------------Show-command------------------------*/
		if(cmd.getName().equals("show")) {
			
			if(!(sender instanceof Player)) return true;
			Player shower = (Player) sender;
			
			if(!shower.isOp()) {
				shower.sendMessage("No permissions to show");
				return true;
			}
			if(args.length == 0) {
				shower.sendMessage("You need to enter the player you want to show yourself to.");
				return false;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			if(!target.canSee(shower)) target.showPlayer(shower);
			shower.sendMessage("Succesfully showed to " + target.getName());
			return true;
		}
		if(cmd.getName().equals("despawn")) {
			if(!(sender instanceof Player)) return true;
			if(args.length != 0) {
				if(!sender.isOp()) return false;
				Player victim = Bukkit.getPlayer(args[0]);
				victim.remove();
				return true;
			} else {
				Player victim = (Player) sender;
				victim.sendMessage("Despawned! You may no longer chat or interact with much of anything, rejoin to fix.");
				victim.remove();
				return true;
			}
			
		}
		return false;
	}
}
