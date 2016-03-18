package spawnhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			String location = world + "," + x + "," + y + "," + z;
			if (BungeeUtilCompanion.getServerName().equals(server)) {
				p.teleport(new Location(Bukkit.getWorld(world), Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)));
				p.sendMessage("§6Teleporting...");
			} else {
				ServerTeleportProtocol.teleportPlayer(p, server, location);
			}
			return true;
		}
		return true;
	}
    
}

