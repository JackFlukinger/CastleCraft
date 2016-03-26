package net.castlecraftmc.spawnhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.castlecraftmc.backhandlers.BackFunctions;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;
import net.castlecraftmc.bungeeutilcompanion.ServerTeleportProtocol;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;


public class SpawnCommand implements CommandExecutor {

    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawn") && sender instanceof Player && sender.hasPermission("bungeeutil.spawn")) {
			Player p = (Player) sender;
			String group = "default";
			for (String groupToCheck : BungeeUtilCompanion.spawnCache.keySet()) {
				if (p.hasPermission("bungeeutil.spawn." + groupToCheck)) {
					group = groupToCheck;
				}
			}
			Map<String,String> spawnSet = SpawnFunctions.getSpawn(group);
			if (spawnSet == null) {
				p.sendMessage("§6There is an issue spawning you. Please contact a staff member.");
				return true;
			}

			String server = spawnSet.get("server");
			String world = spawnSet.get("world");
			String x = spawnSet.get("x");
			String y = spawnSet.get("y");
			String z = spawnSet.get("z");
			String yaw = spawnSet.get("yaw");
			String pitch = spawnSet.get("pitch");
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
		}
		return true;
	}
}

