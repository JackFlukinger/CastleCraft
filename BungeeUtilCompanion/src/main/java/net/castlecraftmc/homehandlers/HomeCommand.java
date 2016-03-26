package net.castlecraftmc.homehandlers;

import java.util.HashMap;
import java.util.List;

import net.castlecraftmc.backhandlers.BackFunctions;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;
import net.castlecraftmc.bungeeutilcompanion.ServerTeleportProtocol;
import net.castlecraftmc.bungeeutilcompanion.Util;
import net.castlecraftmc.warphandlers.WarpFunctions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
    	if (cmd.getName().equalsIgnoreCase("home") && sender instanceof Player && sender.hasPermission("bungeeutil.home")) {
    		Player p = (Player) sender;
    		String uuid = p.getUniqueId().toString();
    		if (args.length == 1) {
    			if (HomeFunctions.hasHome(uuid, args[0])) {
    				HashMap<String,String> home = HomeFunctions.getHome(uuid, args[0]);
    				String server = home.get("server");
    				String world = home.get("world");
    				String x = home.get("x");
    				String y = home.get("y");
    				String z = home.get("z");
    				String yaw = home.get("yaw");
    				String pitch = home.get("pitch");
					String location = world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
    				if (BungeeUtilCompanion.getServerName().equals(server)) {
    					Location locToTeleportTo = new Location(Bukkit.getWorld(world), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch));
    					BackFunctions.setBack(p.getName(), BungeeUtilCompanion.getServerName(), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
    					p.teleport(locToTeleportTo);
    					p.sendMessage("§6Teleporting...");
    					return true;
    				} else {
    					ServerTeleportProtocol.teleportPlayer(p, server, location);
    					return true;
    				}

    			} else {
    				List<String> homeList = HomeFunctions.getHomes(uuid);
    				if (!homeList.isEmpty()) {
    					String homes = Util.join(HomeFunctions.getHomes(uuid), ", ");
    					p.sendMessage("§6Homes: §c" + homes);
    				} else {
    					p.sendMessage("§6You do not have any homes set.");
    				}
    			}
    		} else if (args.length == 0) {
    			String homes = Util.join(HomeFunctions.getHomes(uuid), ", ");
    			p.sendMessage("§6Homes: §c" + homes);
    		} else {
    			p.sendMessage("§6Correct usage is §c/home <home>§6.");
    		}
    		return true;
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