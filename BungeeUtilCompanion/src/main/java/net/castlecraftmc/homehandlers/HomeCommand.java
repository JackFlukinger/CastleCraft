package net.castlecraftmc.homehandlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
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
    					BackFunctions.setBack(p.getUniqueId().toString(), BungeeUtilCompanion.getServerName(), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
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
    		//In case I need to convert homes from essentials files again.
		} /*else if (cmd.getName().equalsIgnoreCase("converthomes") && sender instanceof Player && sender.hasPermission("bungeeutil.converthomes")) {
			YamlConfiguration config = new YamlConfiguration();
			File[] files = new File("/home/minecraft/multicraft/servers/server1/plugins/Essentials/userdata").listFiles();;
			for(File file : files){
			    try {
			        config.load(file);
			        if(config.contains("homes")){
			            for(String key : config.getConfigurationSection("homes").getKeys(false)) {
			            	String home = key;
			            	String world = config.getString("homes." + key + ".world");
			            	String x = config.getString("homes." + key + ".x");
			            	String y = config.getString("homes." + key + ".y");
			            	String z = config.getString("homes." + key + ".z");
			            	String yaw = config.getString("homes." + key + ".yaw");
			            	String pitch = config.getString("homes." + key + ".pitch");
			            	HomeFunctions.setHome(file.getName().split("\\.")[0], home, "main", world, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z), Float.parseFloat(yaw), Float.parseFloat(pitch));
			            }
			        }
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    } catch (InvalidConfigurationException e) {
			        e.printStackTrace();
			    }
			}
		}*/
    	return true;
    }

}