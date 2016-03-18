package net.castlecraftmc.spawnhandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommands implements CommandExecutor {
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("setspawn") && sender instanceof Player && sender.hasPermission("bungeeutil.setspawn")) {
			final Player p = (Player) sender;
			final String group;
			if (args.length != 1) {
				group = "default";
			} else {
				group = args[0];
			}
			final String server = BungeeUtilCompanion.getServerName();
			final String world = p.getLocation().getWorld().getName();
			final double x = p.getLocation().getX();
			final double y = p.getLocation().getY();
			final double z = p.getLocation().getZ();
			final Float yaw = p.getLocation().getYaw();
			final Float pitch = p.getLocation().getPitch();
	    	SpawnFunctions.setSpawn(group, server, world, x, y, z, yaw, pitch);
			p.sendMessage("§6Spawn set.");
			return true;
		} else if (cmd.getName().equalsIgnoreCase("delspawn") && sender instanceof Player && sender.hasPermission("bungeeutil.delspawn")) {
			if (BungeeUtilCompanion.spawnCache.containsKey(args[0])) {
				Player p = (Player) sender;
				if (!args[0].equalsIgnoreCase("default")) {
					SpawnFunctions.delSpawn(args[0]);
					p.sendMessage("§6Spawn §c" + args[0] + " §6deleted.");
					return true;
				} else {
					p.sendMessage("§6You cannot delete the default spawnpoint.");
				}
			} else {
				sender.sendMessage("§6That is not a valid spawnpoint.");
			}
		}
		return true;
    }
}