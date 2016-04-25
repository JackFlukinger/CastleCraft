package net.castlecraftmc.playervaults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*public class ConvertCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("convertvaults") && sender instanceof Player && sender.hasPermission("playervaults.convertvaults")) {
			File[] files = new File("/home/minecraft/multicraft/servers/server1/plugins/EnderVault2/vaults").listFiles();
			for (File file : files){
					try {
						YamlConfiguration config = new YamlConfiguration();
						config.load(file);
						HashMap<Integer, HashMap<Integer, ItemStack>> itemMap = new HashMap<Integer, HashMap<Integer, ItemStack>>();
						for (String vault : config.getKeys(false)) {
							if (!vault.startsWith("vaults")) {
								int vaultNum = Integer.parseInt(vault.substring(5));
								HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
								for (String slot : config.getConfigurationSection(vault).getKeys(false)) {
									ItemStack item = config.getItemStack(vault + "." + slot);
									items.put(Integer.parseInt(slot), item);
								}
								itemMap.put(vaultNum, items);
							}
						}
						File newFile = new File(PlayerVaults.getPlugin().getDataFolder().toString() + "/vaults/" + file.getName());
						newFile.createNewFile();
						YamlConfiguration vaultFile = YamlConfiguration.loadConfiguration(newFile);
						for (int vault : itemMap.keySet()) {
							HashMap<Integer, ItemStack> itemsToPut = itemMap.get(vault);
							for (Integer slot : itemsToPut.keySet()) {
								vaultFile.set("vaults." + String.valueOf(vault) + "." + String.valueOf(slot), itemsToPut.get(slot));
							}
						}
						Bukkit.getLogger().info("Converting Vaults");
						vaultFile.save(newFile);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
				}
		}
		return true;
	}
}*/
