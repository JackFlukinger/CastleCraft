package net.castlecraftmc.castlecraftcustomitems;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;


public class CastleCraftCustomItems extends JavaPlugin implements Listener {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static List<String> affectedItems = new ArrayList<String>();
    private static List<String> loreFormat = new ArrayList<String>();
    private static List<String> types = new ArrayList<String>();
    private static HashMap<String,List<String>> prefixes = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> baseNames = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> suffixes = new HashMap<String,List<String>>();
    private static HashMap<String,List<String>> rareDropBlocks = new HashMap<String,List<String>>();
    private static HashMap<String,Map<Integer,String>> rareDrops = new HashMap<String,Map<Integer,String>>();
    private static HashMap<String,String> modifiers = new HashMap<String,String>();
    private static HashMap<String,Double> multipliers = new HashMap<String,Double>();
    private static HashMap<String,Integer> maxEXPAdd = new HashMap<String,Integer>();
    private static HashMap<String,Integer> baseEXP = new HashMap<String,Integer>();
    private static HashMap<String,Integer> expPerMobKill = new HashMap<String,Integer>();
	private static Plugin plugin;

	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new CraftListener(), this);
		getServer().getPluginManager().registerEvents(new ItemListener(), this);
		getServer().getPluginManager().registerEvents(new mobSpawnUtil(), this);
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		//Add enabled items to affectedItems list
		for (String item : getConfig().getConfigurationSection("ItemsToAffect").getKeys(false)) {
			//Get config values for each item, initialize them for later use by Monsieur Plugin
			affectedItems.add(item);
			prefixes.put(item, getConfig().getStringList("ItemsToAffect." + item + ".Prefixes"));
			baseNames.put(item, getConfig().getStringList("ItemsToAffect." + item + ".Bases"));
			suffixes.put(item, getConfig().getStringList("ItemsToAffect." + item + ".Suffixes"));
			modifiers.put(item, getConfig().getString("ItemsToAffect." + item + ".Modifier"));
			multipliers.put(item, getConfig().getDouble("ItemsToAffect." + item + ".Multiplier"));
			maxEXPAdd.put(item, getConfig().getInt("ItemsToAffect." + item + ".MaxEXPAddPerLevel"));
			baseEXP.put(item, getConfig().getInt("ItemsToAffect." + item + ".BaseEXP"));
			rareDropBlocks.put(item, getConfig().getStringList("ItemsToAffect." + item + ".RareDropBlocks"));
			HashMap<Integer,String> dropMap = new HashMap<Integer,String>(); 
			List<String> rareDropList = getConfig().getStringList("ItemsToAffect." + item + ".RareDrops");
			for (String dropString : rareDropList) {
				String[] dropValueArray = dropString.split(",");
				dropMap.put(Integer.parseInt(dropValueArray[1]),dropValueArray[0]);
			}
			rareDrops.put(item, dropMap);
		}
		Bukkit.broadcastMessage(rareDropBlocks.toString());
		getServer().broadcastMessage(rareDrops.toString());
		
		Bukkit.broadcastMessage(affectedItems.toString() + prefixes.toString() + baseNames.toString() + suffixes.toString() + modifiers.toString());
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
		for (String mobType : getConfig().getConfigurationSection("EXPPerMobKill").getKeys(false)) {
			expPerMobKill.put(mobType, getConfig().getInt("EXPPerMobKill." + mobType));
		}
		//Get prefixes for each weapon/tool
		/*for (String key : getConfig().getConfigurationSection("ItemsToAffect").getKeys(false)) {
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
		}*/
		
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
	public static List<String> getLore(ItemStack item) {
		List<String> lore;
		if (item.hasItemMeta() && (item.getItemMeta().hasLore())) {
			if (item.getItemMeta().getLore().size() > 2 && item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 2).startsWith("§aLevel:")) {
				return item.getItemMeta().getLore();
			} else {
				lore = new ArrayList<String>(item.getItemMeta().getLore());
				lore.addAll(loreFormat);
			}
		} else {
			lore = loreFormat;
		}

		List<String> newLore = new ArrayList<String>();

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
				line = line.replace("<maxXP>", String.valueOf(getBaseEXP(item)));
			}
			if (line.contains("<modifier>")) {
				line = line.replace("<modifier>", getModifier(item, false));
			}
			newLore.add(line);
		}
		Bukkit.broadcastMessage("New Lore: " + newLore);
		return newLore;
	}
	public static String getNameVariant(ItemStack item) {
		//Check if should rename (if does not have display name already)
		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			return item.getItemMeta().getDisplayName();
		} else {
			String itemKey = item.getType().toString();
			String prefix = new String();
			String baseName = new String();
			String suffix = new String();
			boolean isPrefix;
			boolean isBaseName;
			boolean isSuffix;
			List<String> possiblePrefixes = new ArrayList<String>();
			List<String> possibleBaseNames = new ArrayList<String>();
			List<String> possibleSuffixes = new ArrayList<String>();
			if (prefixes.containsKey(itemKey) && !prefixes.get(itemKey).isEmpty()) {
				possiblePrefixes = prefixes.get(itemKey);
				isPrefix = true;
			} else {
				isPrefix = false;
			}
			if (baseNames.containsKey(itemKey) && !baseNames.get(itemKey).isEmpty()) {
				possibleBaseNames = baseNames.get(itemKey);
				isBaseName = true;
			} else {
				isBaseName = false;
			}
			if (suffixes.containsKey(itemKey) && !suffixes.get(itemKey).isEmpty()) {
				possibleSuffixes = suffixes.get(itemKey);
				isSuffix = true;
			} else {
				isSuffix = false;
			}

			//Chooses random value of each
			if (isPrefix) {
				prefix = possiblePrefixes.get((int) (Math.random() * (possiblePrefixes.size())));
			}
			if (isBaseName) {
				baseName = possibleBaseNames.get((int) (Math.random() * (possibleBaseNames.size())));
			}
			if (isSuffix) {
				suffix = possibleSuffixes.get((int) (Math.random() * (possibleSuffixes.size())));
			}
			String newName = "§f" + prefix + " " + baseName + " " + suffix;
			return newName.trim();
		}
	}
	//Get Item modifier type (damage, etc.)
	public static String getModifierType(ItemStack item) {
		return modifiers.get(item.getType().toString());
	}
	
	//Return actual modifier text for use in lore
	public static String getModifier(ItemStack item, boolean LevelUp) {
		String type = getModifierType(item);
		String modifierText = new String();
		switch (type) {
		case "damage":
			DecimalFormat df = new DecimalFormat("0.0");
			String newDamage;
			if (LevelUp) {
				newDamage = df.format(((getLevel(item)) * getMultiplier(item)));
			} else {
				newDamage = df.format(((getLevel(item) - 1) * getMultiplier(item)));
			}
			modifierText = "§5+" + newDamage + " Damage";
			return modifierText;
		case "luck":
			int newLuck;
			if (LevelUp) {
				newLuck = (int) (getLevel(item) * getMultiplier(item));
			} else {
				newLuck = (int) ((getLevel(item) - 1) * getMultiplier(item));
			}
			modifierText = "§5" + String.valueOf(newLuck) + " Luck";
			return modifierText;
		}
		return modifierText;
	}
	
	public static Double getMultiplier(ItemStack item) {
		if (multipliers.containsKey(item.getType().toString())) {
			return multipliers.get(item.getType().toString());
		} else {
			return 0.0;
		}
	}
	
	public static int getLevel(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			List<String> lore = item.getItemMeta().getLore();
			for (String line : lore) {
				line = ChatColor.stripColor(line);
				if (line.contains("Level:")) {
					String[] levelPossible = line.split(" ");
					if (levelPossible.length >= 2) {
						return Integer.parseInt(levelPossible[1]);
					}
				}
			}
		}
		return 1;
	}
	//Get affected items string list
	public static List<String> getAffectedItems() {
		return affectedItems;
	}
	
	//Get lore format for util etc.
	public static List<String> getLoreFormat() {
		return loreFormat;
	}
	
	//Get type levels for leveling :3
	public static List<String> getTypes() {
		return types;
	}
	
	//Get blocks rare drops for each item
	public static HashMap<String,List<String>> getRareDropBlocks() {
		return rareDropBlocks;
	}
	
	//Get rare drop percentages, etc for each item
	public static HashMap<String, Map<Integer,String>> getRareDrops() {
		return rareDrops;
	}
	
	//Get how much xp to add to max exp per level for util etc.
	public static Integer getMaxEXPAdd(ItemStack item) {
		return maxEXPAdd.get(item.getType().toString());
	}
	
	public static Integer getBaseEXP(ItemStack item) {
		return baseEXP.get(item.getType().toString());
	}
	
	public static Integer getEXPPerMobKill(LivingEntity e) {
		if (expPerMobKill.containsKey(e.getType().toString())) {
			return expPerMobKill.get(e.getType().toString());
		} else {
			return 0;
		}
	}

	//Just stuff when disabling the plugin
	public void onDisable() {
		plugin = null;
	}
}
