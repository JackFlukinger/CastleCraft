package net.castlecraftmc.delayedcommands;

import java.io.Console;
import java.util.HashMap;

import net.castlecraftmc.backhandlers.BackFunctions;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;
import net.castlecraftmc.bungeeutilcompanion.ServerTeleportProtocol;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelayedCommands implements CommandExecutor {
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("delcommand") && sender instanceof Player && sender.hasPermission("bungeeutil.delcommand")) {
			if (BungeeUtilCompanion.delayedCommands.containsKey(args[0])) {
				String command = BungeeUtilCompanion.delayedCommands.get(args[0]).get("command");
				String server = BungeeUtilCompanion.delayedCommands.get(args[0]).get("server");
				ServerTeleportProtocol.callTeleport(sender.getName(), server);
				DelayedCommandsFunctions.addDelayedCommand(server, sender.getName(), command);
				return true;
			} else {
				sender.sendMessage("§c" + args[0] + " §6is not a valid delayed command.");
				return true;
			}
		}
		return true;
	}
}
