package net.minekingdom.shutdown;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Shutdown extends JavaPlugin {
	
	public void onEnable() {
		getServer().getPluginCommand("shutdown").setPermission("minekingdom.admin.shutdown");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String reason;
		StringBuilder joinedArgs = new StringBuilder();
		for (String s : args) {
			joinedArgs.append(s).append(" ");
		}
		reason = joinedArgs.toString().trim();
		
		if (reason.isEmpty()) {
			reason = "MineKingdom s'arrête pour une maintenance.";
		} else {
			reason = "MineKingdom s'arrête pour la raison suivante : " + reason + ".";
		}
		reason += "\n" + "Ne vous intquiétez pas, nous revenons bientôt ;)";
		reason += "\n\n" + ChatColor.GREEN + "A tout de suite !";
		for (Player player : getServer().getOnlinePlayers()) {
			player.kickPlayer(reason);
		}
		
		for (World world : getServer().getWorlds()) {
			world.save();
			for (Chunk c : world.getLoadedChunks()) {
				c.unload(true);
			}
		}
		
		getServer().shutdown();
		return true;
	}
}
