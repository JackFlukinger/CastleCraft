package net.castlecraftmc.warphandlers;

import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;
import net.castlecraftmc.spawnhandlers.SpawnFunctions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
    	if (cmd.getName().equalsIgnoreCase("setwarp") && sender instanceof Player && sender.hasPermission("bungeeutil.setwarp")) {
    		if (args.length == 1) {
    			final Player p = (Player) sender;
    			final String name = args[0];
    			final String server = BungeeUtilCompanion.getServerName();
    			final String world = p.getLocation().getWorld().getName();
    			final double x = p.getLocation().getX();
    			final double y = p.getLocation().getY();
    			final double z = p.getLocation().getZ();
    			final Float yaw = p.getLocation().getYaw();
    			final Float pitch = p.getLocation().getPitch();
    			WarpFunctions.setWarp(name, server, world, x, y, z, yaw, pitch);
    			p.sendMessage("§6Warp §c" + name + " §6set.");
    			return true;
    		} else {
				sender.sendMessage("§6Correct usage is /setwarp <warp>.");
    		}
		} else if (cmd.getName().equalsIgnoreCase("delwarp") && sender instanceof Player && sender.hasPermission("bungeeutil.delwarp")) {
			if (args.length == 1) {
				if (BungeeUtilCompanion.warpCache.containsKey(args[0])) {
					Player p = (Player) sender;
					WarpFunctions.delWarp(args[0]);
					p.sendMessage("§6Warp §c" + args[0] + " §6deleted.");
					return true;
				}
			} else {
				sender.sendMessage("§6Correct usage is /delwarp <warp>.");
			}
		}
    	return true;
    }
}
