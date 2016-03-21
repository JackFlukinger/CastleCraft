package net.castlecraftmc.backhandlers;

import java.util.HashMap;
import java.util.Map;

import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;
import net.castlecraftmc.bungeeutilcompanion.ServerTeleportProtocol;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("back") && sender instanceof Player && sender.hasPermission("bungeeutil.back")) {
			Player p = (Player) sender;
			HashMap<String,String> backSet = BackFunctions.getBack(p.getName());
			if (backSet.isEmpty()) {
				p.sendMessage("§6There is no saved location to teleport you to.");
				return true;
			}
			String server = backSet.get("server");
			String world = backSet.get("world");
			String x = backSet.get("x");
			String y = backSet.get("y");
			String z = backSet.get("z");
			String yaw = backSet.get("yaw");
			String pitch = backSet.get("pitch");
			String location = world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
			if (BungeeUtilCompanion.getServerName().equals(server)) {
				Location locToTeleportTo = new Location(Bukkit.getWorld(world), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch));
				BackFunctions.setBack(p.getName(), BungeeUtilCompanion.getServerName(), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
				p.teleport(locToTeleportTo);
				p.sendMessage("§6Teleporting...");
			} else {
				ServerTeleportProtocol.teleportPlayer(p, server, location);
			}
			return true;
		}
		return true;
	}
}
