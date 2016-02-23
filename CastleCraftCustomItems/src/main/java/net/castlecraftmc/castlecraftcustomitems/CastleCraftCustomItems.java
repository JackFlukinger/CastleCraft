package net.castlecraftmc.castlecraftcustomitems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class CastleCraftCustomItems extends JavaPlugin implements Listener {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static List<String> affectedItems = new ArrayList<String>();
    private static List<String> loreFormat = new ArrayList<String>();
    private static List<String> types = new ArrayList<String>();
    private static HashMap<String,List<String>> prefixes = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> baseNames = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> suffixes = new HashMap<String,List<String>>();
    private static String baseXP = new String();
	private static Plugin plugin;

	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new CraftListener(), this);
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		//Add enabled items to affectedItems list
		for (String item : getConfig().getStringList("ItemsToAffect")) {
			affectedItems.add(item);
		}
		//Add default lore to lore array
		for (String loreEntry : getConfig().getStringList("LoreFormat")) {
			loreEntry = loreEntry.replace("&", "§");
			loreFormat.add(loreEntry);
		}
		//Get type list and make get-able from array
		for (String type : getConfig().getStringList("Types")) {
			type = type.replace("&", "§");
			types.add(type);
		}
		//Get prefixes for each weapon/tool
		for (String key : getConfig().getConfigurationSection("NamePrefixes").getKeys(false)) {
			//Make the item type the key in the hashmap "prefixes"
			String ItemType = key;
			//Make prefixes per item the list of prefixes stored in "prefixes"
			List<String> prefixesPerItemType = new ArrayList<String>();
			//Loop through all the prefixes for each item type and add them to the list "prefixesPerItemType"
			for (String prefix : getConfig().getStringList("NamePrefixes." + key)) {
				prefixesPerItemType.add(prefix);
			}
			//After looping through the prefixes and adding them to the list, put that in the hashmap with the key "item type"
			prefixes.put(ItemType, prefixesPerItemType);
		}
		
		//Get base names for each weapon/tool
		for (String key : getConfig().getConfigurationSection("NameBases").getKeys(false)) {
			//Make the item type the key in the hashmap "baseNames"
			String ItemType = key;
			//Make base names per item the list of prefixes stored in "baseNames"
			List<String> baseNamesPerItemType = new ArrayList<String>();
			//Loop through all the base names for each item type and add them to the list "baseNamesPerItem"
			for (String baseName : getConfig().getStringList("NameBases." + key)) {
				baseNamesPerItemType.add(baseName);
			}
			//After looping through the base names and adding them to the list, put that in the hashmap with the key "item type"
			baseNames.put(ItemType, baseNamesPerItemType);
		}
		
		//Get suffixes for each weapon/tool
		for (String key : getConfig().getConfigurationSection("NameSuffixes").getKeys(false)) {
			//Make the item type the key in the hashmap "suffixes"
			String ItemType = key;
			//Make suffixes per item the list of prefixes stored in "suffixes"
			List<String> suffixesPerItemType = new ArrayList<String>();
			//Loop through all the suffixes for each item type and add them to the list "suffixesPerItemType"
			for (String suffix : getConfig().getStringList("NameSuffixes." + key)) {
				suffixesPerItemType.add(suffix);
			}
			//After looping through the suffixes and adding them to the list, put that in the hashmap with the key "item type"
			suffixes.put(ItemType, suffixesPerItemType);
		}
		
		baseXP = getConfig().getString("BaseXP");
	}

	public static boolean isItemInConfig(ItemStack item) {
		for (String testItemMaterial : affectedItems) {
			Material testMaterial = Material.getMaterial(testItemMaterial);
			if (testMaterial != null && testMaterial == item.getType()) {
				return true;
			}
		}
		return false;
	}
	
	//Used when the plugin needs to edit the lore of an item
	public static List<String> getLoreFormat(ItemStack item) {
		List<String> lore;
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			lore = new ArrayList<String>(loreFormat);
			lore.addAll(item.getItemMeta().getLore());
		} else {
			lore = loreFormat;
		}
		//If item doesn't have lore, then just make the lore with CastleCraftCustomItem-related stuff, no need to get current lore and add unneeded stuff 
		List<String> newLore = new ArrayList<String>();
		//Counter for line replacement stuffs
		for (String line : lore) {
			//If line contains <type>, replace it with Common, because all items start at Common
			if (line.contains("<type>")) {
				line = line.replace("<type>", types.get(0));
			}
			//If line contains <level>, replace it with 1, because all items start at level 1
			if (line.contains("<level>")) {
				line = line.replace("<level>", "§31");
			}
			//If line contains <currentXP>, replace it with 0, because that's what all items start at
			if (line.contains("<currentXP>")) {
				line = line.replace("<currentXP>", "0");
			}
			//If line contains <maxXP>, replace it with the baseXP value from the config
			if (line.contains("<maxXP>")) {
				line = line.replace("<maxXP>", baseXP);
			}
			Bukkit.broadcastMessage("Line 1: " + line);
			newLore.add(line);
		}
		return newLore;
	}
	public static String getNameVariant(ItemStack item) {
		//Check if should rename (if does not have display name already)
		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			return item.getItemMeta().getDisplayName();
		} else {
			String itemKey = item.getType().toString();
			List<String> possiblePrefixes = prefixes.get(itemKey);
			List<String> possibleBaseNames = baseNames.get(itemKey);
			List<String> possibleSuffixes = suffixes.get(itemKey);
			//Chooses random value of each
			String prefix = possiblePrefixes.get((int) (Math.random() * (possiblePrefixes.size())));
			String baseName = possibleBaseNames.get((int) (Math.random() * (possibleBaseNames.size())));
			String suffix = possibleSuffixes.get((int) (Math.random() * (possibleSuffixes.size())));
			return "§f" + prefix + " " + baseName + " " + suffix;
		}
	}
	public void onDisable() {
		plugin = null;
	}
}
