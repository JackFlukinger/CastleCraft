package net.castlecraftmc.homehandlers;

import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommands implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
    	if (cmd.getName().equalsIgnoreCase("sethome") && sender instanceof Player && sender.hasPermission("bungeeutil.sethome")) {
    		if (args.length == 1) {
    			final Player p = (Player) sender;
    			final String uuid = p.getUniqueId().toString();
				final String home = args[0];
				final String server = BungeeUtilCompanion.getServerName();
				final String world = p.getLocation().getWorld().getName();
				final double x = p.getLocation().getX();
				final double y = p.getLocation().getY();
				final double z = p.getLocation().getZ();
				final Float yaw = p.getLocation().getYaw();
				final Float pitch = p.getLocation().getPitch();
    			if (HomeFunctions.hasHome(uuid, args[0])) {
    				HomeFunctions.setHome(uuid, home, server, world, x, y, z, yaw, pitch);
    				p.sendMessage("§6Home §c" + home + " §6set.");
    				return true;
    			} else {
    				HomeFunctions.delHome(uuid, home);
    				HomeFunctions.setHome(uuid, home, server, world, x, y, z, yaw, pitch);
    				p.sendMessage("§6Home §c" + home + " §6set.");
    				return true;
    			}
    		} else {
				sender.sendMessage("§6Correct usage is /sethome <home>.");
    		}
		} else if (cmd.getName().equalsIgnoreCase("delhome") && sender instanceof Player && sender.hasPermission("bungeeutil.delhome")) {
			if (args.length == 1) {
					Player p = (Player) sender;
					String uuid = p.getUniqueId().toString();
				if (HomeFunctions.hasHome(uuid, args[0])) {
					HomeFunctions.delHome(uuid, args[0]);
					p.sendMessage("§6Home §c" + args[0] + " §6deleted.");
					return true;
				} else {
					p.sendMessage("§6You don't have a home named §c" + args[0] + "§6.");
				}
			} else {
				sender.sendMessage("§6Correct usage is /delhome <home>.");
			}
		}
    	return true;
    }
}
