package net.castlecraftmc.portals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Portals extends JavaPlugin implements Listener,CommandExecutor {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static List<World> enabledWorlds = new ArrayList<World>();
	private static List<ProtectedRegion> portalRegions = new ArrayList<ProtectedRegion>();
	//Hashmap with region and command
	private static HashMap<String,String> commandArray = new HashMap<String,String>();
	private static Plugin plugin;
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new MoveListener(), this);
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		if (getConfig().getStringList("EnabledWorlds").isEmpty()) {
            log.warning(String.format("[%s] - Disabled: enabled worlds found!", getDescription().getName()));
			Bukkit.getPluginManager().disablePlugin(plugin);
		}
		for (String w : getConfig().getStringList("EnabledWorlds")) {
			w = w.trim();
			World world = Bukkit.getWorld(w);
			// Check if the world name is a valid world
			log.info(String.format("[%s] - Enabling world: " + w, getDescription().getName()));
			enabledWorlds.add(Bukkit.getWorld(w));
			log.info(String.format("[%s] - Enabled world: " + w, getDescription().getName()));
		}
		for (String key : getConfig().getConfigurationSection("Portals").getKeys(false)) {
			for (World world : enabledWorlds) {
				portalRegions.add(getRegion(getConfig().getString("Portals." + key + ".Region"), world));
			}
			commandArray.put(getConfig().getString("Portals." + key + ".Region"), getConfig().getString("Portals." + key + ".Command"));
		}
		//Start the portal listener
		MoveListener.checkIfPlayerIsInPortal.runTaskTimer(this, 0L, 10L);
	}
	//Portal Command Matrix
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("portals") && sender instanceof Player && sender.hasPermission("portals.*")) {
    		Player p = (Player) sender;
    		if (args.length == 0) {
    			sendHelpMenu(p);
    			return true;
    		} else if (args.length == 1) {
    			// /portal list command, lists portals currently created
    			if (args[0].equalsIgnoreCase("list")) {
    				
    				//Initialize the stringbuilder
					StringBuilder stringBuilder = new StringBuilder();

    				for (String portal : getConfig().getConfigurationSection("Portals").getKeys(false)) {
					    stringBuilder.append(portal);
					    stringBuilder.append(" ");
    				}
    				
					String portalList = stringBuilder.toString();
    				
					p.sendMessage("§6[§aC§2C§6] §cPortals: " + portalList);
					return true;
    			} else {
        			sendHelpMenu(p);
        			return true;
    			}
    		} else if (args.length == 2) {
    			if (args[0].equalsIgnoreCase("delete")) {
    				//Check if portal exists so that it can be deleted
    				if (isPortalNameInUse(args[1])) {
    					String regionName = getConfig().getString("Portals." + args[1] + ".Region");
    					getConfig().set("Portals." + args[1], null);
    					saveConfig();
    					p.sendMessage("§6[§aC§2C§6] §cYou successfully deleted " + args[1] + ".");
						commandArray.remove(regionName);
						portalRegions.remove(getRegion(args[2], p.getWorld()));
    					return true;
    				} else {
    					p.sendMessage("§6[§aC§2C§6] §cThis portal does not exist.");
    					return true;
    				}
    			} else if (args[0].equalsIgnoreCase("info")) {
    				//Checks if portal exists
    				if (isPortalNameInUse(args[1])) {
    					String command = getConfig().getString("Portals." + args[1] + ".Command");
    					String region = getConfig().getString("Portals." + args[1] + ".Region");
    					p.sendMessage("§6[§aC§2C§6] §cCommand: /" + command);
    					p.sendMessage("§6[§aC§2C§6] §cRegion: " + region);
    					return true;
    				} else {
    					p.sendMessage("§6[§aC§2C§6] §cNo portal with that name exists.");
    					return true;
    				}
    			} else {
        			sendHelpMenu(p);
        			return true;
    			}
    		} else if (args.length >= 4) {
    			if (args[0].equalsIgnoreCase("create")) {
    				//Check if player is in an enabled world
    				if (enabledWorlds.contains(p.getWorld())) {
    					//Check if the region exists
    					if (isNameARegion(args[2], p)) {
    						//Check if the portal name already exists
    						if (!isPortalNameInUse(args[1])) {
    							StringBuilder stringBuilder = new StringBuilder();

    							//Combine the args for the command section to form a single string
    							for(int i = 3; i < args.length; i++)
    							{
    								stringBuilder.append(args[i]);
    								stringBuilder.append(" ");
    							}
    							//Convert the appended strings to a single string, command
    							String command = stringBuilder.toString();
    							command = command.trim();

    							getConfig().set("Portals." + args[1] + ".Region", args[2]);
    							getConfig().set("Portals." + args[1] + ".Command", command);
    							saveConfig();
    							commandArray.put(getConfig().getString("Portals." + args[1] + ".Region"), getConfig().getString("Portals." + args[1] + ".Command"));
    							portalRegions.add(getRegion(args[2], p.getWorld()));
    							p.sendMessage("§6[§aC§2C§6] §cYou successfully created a portal named " + args[1] + " that uses the command /" + command + ".");
    							return true;
    						} else {
    							p.sendMessage("§6[§aC§2C§6] §cA portal with that name already exists.");
    							return true;
    						}
    					} else {
    						p.sendMessage(args[2] + " is not a valid region.");
    						return true;
    					}
    				} else {
    					p.sendMessage("§6[§aC§2C§6] §cPortals are not enabled in this world.");
    				}
    			} else {
    				sendHelpMenu(p);
    				return true;
    			}
    		}
    	}
		return false;
	}
	
	
    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }
    public ProtectedRegion getRegion(String regionName, World world) {
    	RegionContainer container = getWorldGuard().getRegionContainer();
    	RegionManager regions = container.get(world);
    	if (regions != null) {
    		return regions.getRegion(regionName);
    	} else {
    		return null;
    		// The world has no region support or region data failed to load
    	}
    }
    
    //Check if player is in region
    public static boolean playerIsInRegion(Player p, ProtectedRegion r) {
    	ApplicableRegionSet set = WGBukkit.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
    	if (set.getRegions().contains(r)) {
    		return true;
    	} else {
    		return false;
    	}
    }
	/*public static boolean isWithinRegion(Player p, ProtectedRegion r) {
		return isWithinRegion(p, r);
	}*/
	
    //Check if string is a valid region
	public boolean isNameARegion(String name, Player p) {
		RegionContainer container = getWorldGuard().getRegionContainer();
		RegionManager regions = container.get(p.getWorld());
		Map<String,ProtectedRegion> regionMap = regions.getRegions();
		if (regionMap.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	//Get current portal names to prevent duplicate
	public boolean isPortalNameInUse(String testname) {
		if (!getConfig().getConfigurationSection("Portals").getKeys(false).isEmpty() && getConfig().getConfigurationSection("Portals").getKeys(false).contains(testname)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void sendHelpMenu(Player p) {
		p.sendMessage("§6---------==[§aCastle§2Craft §cPortal Help§6]==---------");
		p.sendMessage("§6[§aC§2C§6] §c/portals help §b- Shows this help menu");
		p.sendMessage("§6[§aC§2C§6] §c/portals create [portal name] [region] [command] §b- Creates a portal named [portal name] that runs [command] when a player enters [region]");
		p.sendMessage("§6[§aC§2C§6] §c/portals delete [portal name] §b- Deletes portal [portal name]");
		p.sendMessage("§6[§aC§2C§6] §c/portals info [portal name] §b- Shows important info about [portal name]");
		p.sendMessage("§6[§aC§2C§6] §c/portals list §b- Lists portals");
	}
	public void onDisable() {
		plugin = null;
	}
	public static List<World> getEnabledWorlds() {
		return enabledWorlds;
	}
	public static List<ProtectedRegion> getPortalRegions() {
		return portalRegions;
	}
	public static HashMap getCommandArray() {
		return commandArray;
	}
	public static Logger getLog() {
		return log;
	}
}