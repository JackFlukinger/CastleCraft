package net.castlecraftmc.warphandlers;

import java.util.Map;

import net.castlecraftmc.backhandlers.BackFunctions;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;
import net.castlecraftmc.bungeeutilcompanion.ServerTeleportProtocol;
import net.castlecraftmc.bungeeutilcompanion.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WarpCommand implements CommandExecutor {
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("warp") && sender instanceof Player && sender.hasPermission("bungeeutil.warp")) {
			if (args.length == 1) {
				Player p = (Player) sender;
				String name = args[0];
				if (BungeeUtilCompanion.warpCache.containsKey(name)) {
					Map<String,String> warpSet = WarpFunctions.getWarp(name);
					if (warpSet == null) {
						p.sendMessage("§6There is an issue warping you. Please contact a staff member.");
						return true;
					}

					String server = warpSet.get("server");
					String world = warpSet.get("world");
					String x = warpSet.get("x");
					String y = warpSet.get("y");
					String z = warpSet.get("z");
					String yaw = warpSet.get("yaw");
					String pitch = warpSet.get("pitch");
					String location = world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
					if (BungeeUtilCompanion.getServerName().equals(server)) {
						Location locToTeleportTo = new Location(Bukkit.getWorld(world), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch));
						BackFunctions.setBack(p.getUniqueId().toString(), BungeeUtilCompanion.getServerName(), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
						p.teleport(locToTeleportTo);
						p.sendMessage("§6Teleporting...");
					} else {
						ServerTeleportProtocol.teleportPlayer(p, server, location);
					}
					return true;
				} else {
					p.sendMessage("§c" + name + " §6is not a valid warp.");
				}
			} else {
    			String warpList = new String();
    			warpList = Util.join(BungeeUtilCompanion.warpCache.keySet(), ", ");
				sender.sendMessage("§6Warps: §c" + warpList);
			}
		}
		return true;
	}
}
