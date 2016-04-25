package net.castlecraftmc.playervaults;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PlayerVaultsFunctions {
	public static List<Player> pendingInventories = new ArrayList<Player>();
	
	public static boolean hasVault(Player p, int vaultNum) {
		if (p.hasPermission("playervaults.vault." + String.valueOf(vaultNum))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void openVault(Player p, int vaultNum) {
		if (hasVault(p, vaultNum)) {
			if (!pendingInventories.contains(p)) {
				Inventory vault = parseFileToInventory(p, vaultNum);
				p.openInventory(vault);
			} else {
				p.sendMessage("Your Vault is still saving...");
			}
		}
	}
	
	public static Inventory parseFileToInventory(Player p, int vaultNum) {
		String uuid = p.getUniqueId().toString();
		Inventory vault = Bukkit.createInventory(null, PlayerVaults.getVaultSize(), "Vault " + String.valueOf(vaultNum));
		File file = new File(PlayerVaults.getPlugin().getDataFolder().toString() + "/vaults/" + uuid + ".yml");
		if(file.exists() && !file.isDirectory()) {
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		    if (yaml.contains("vaults." + String.valueOf(vaultNum))) {
		    	ItemStack[] contents = new ItemStack[]{};
		    	for (String key : yaml.getConfigurationSection("vaults." + vaultNum).getKeys(false)) {
		    		vault.setItem(Integer.parseInt(key), yaml.getItemStack("vaults." + vaultNum + "." + key));
		    	}
		    }
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return vault;
	}
	
	public static void saveVault(Player p) {
		if (p.getOpenInventory() != null && p.getOpenInventory().getTopInventory().getTitle().startsWith("Vault")) {
			pendingInventories.add(p);
			Inventory inv = p.getOpenInventory().getTopInventory();
			File file = new File(PlayerVaults.getPlugin().getDataFolder().toString() + "/vaults/" + p.getUniqueId().toString() + ".yml");
			YamlConfiguration vaultFile = YamlConfiguration.loadConfiguration(file);
			for (int i = 0; i < PlayerVaults.getVaultSize(); i++) {
				ItemStack itemToPut = inv.getItem(i);
				if (itemToPut != null && itemToPut.getType() != Material.AIR) {
					vaultFile.set("vaults." + inv.getTitle().split(" ")[1] + "." + String.valueOf(i), itemToPut);
				} else {
					vaultFile.set("vaults." + inv.getTitle().split(" ")[1] + "." + String.valueOf(i), null);
				}
			}
			try {
				vaultFile.save(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			pendingInventories.remove(p);
		}
	}
}