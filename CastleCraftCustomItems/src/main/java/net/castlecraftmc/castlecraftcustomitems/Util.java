package net.castlecraftmc.castlecraftcustomitems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class Util {
	
	//Run on hit
	public static void onHit(LivingEntity damagedEntity, Entity damager, int exp) {
		if (damager instanceof Player | damager instanceof Arrow) {
			//If damager is player
			if (damager instanceof Player) {
				//If item entity is hit with is affected by CastleCraftCustomItems
				if (((Player) damager).getInventory().getItemInHand().getType() != Material.BOW && CastleCraftCustomItems.getAffectedItems().contains(((Player) damager).getInventory().getItemInHand().getType().toString())) {
					ItemStack item = ((Player) damager).getInventory().getItemInHand();
					if (CastleCraftCustomItems.getModifierType(item).equals("damage")) {
						if (hasLore(item)) {
							//If entity will die, give more exp
							addEXP(item, exp, (Player) damager);
						}
					}
				}
			} else if (damager instanceof Arrow) {
				Player damagerPlayer;
				//If shooter is a player, continue. Otherwise, quit.
				if (((Arrow) damager).getShooter() instanceof Player) {
					damagerPlayer = (Player) ((Arrow) damager).getShooter();
				} else {
					return;
				}
				//If item hit with is affected by CastleCraftCustomItems
				if (CastleCraftCustomItems.getAffectedItems().contains(damagerPlayer.getInventory().getItemInHand().getType().toString())) {
					ItemStack item = damagerPlayer.getInventory().getItemInHand();
					if (CastleCraftCustomItems.getModifierType(item).equals("damage")) {
						if (hasLore(item)) {
							addEXP(item, exp, damagerPlayer);
						} 
					}
				}
			}
		}
	}
	
	
	public static Double getDamage(Entity e) {
		if (e instanceof Player | e instanceof Arrow) {
			if (e instanceof Player) {
				if (CastleCraftCustomItems.getAffectedItems().contains(((Player) e).getItemInHand().getType().toString())) {
					return ((CastleCraftCustomItems.getLevel(((Player) e).getInventory().getItemInHand()) - 1) * CastleCraftCustomItems.getMultiplier(((Player) e).getInventory().getItemInHand()));
				}
			} else if (e instanceof Arrow) {
				if (CastleCraftCustomItems.getAffectedItems().contains(((Player) ((Arrow) e).getShooter()).getItemInHand().getType().toString())) {
					return ((CastleCraftCustomItems.getLevel(((Player) ((Arrow) e).getShooter()).getInventory().getItemInHand()) * CastleCraftCustomItems.getMultiplier(((Player) ((Arrow) e).getShooter()).getInventory().getItemInHand())));
				}
			}
		}
		return 0.0;
	}
	//Fairly redundant method to make check if item has lore. Makes stuff easier to look at :P
	public static boolean hasLore(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void addEXP(ItemStack item, int exp, Player p) {
		if (CastleCraftCustomItems.getLevel(item) != 100) {
			List<String> lore = item.getItemMeta().getLore();
			String expLine = new String();
			boolean levelUp = false;
			int expToAdd = exp;
			int startEXP;
			int maxEXP;
			int level = 1;
			int maxEXPAdd = CastleCraftCustomItems.getMaxEXPAdd(item);
			int indexToRemove = 0;
			for (String line : lore) {
				if (line.contains("EXP:")) {
					String[] brokenLine = line.split(" ");
					brokenLine[1] = ChatColor.stripColor(brokenLine[1]);
					level = Integer.parseInt(brokenLine[1]);
					for (String word : brokenLine) {
						if (word.contains("/")) {
							word = ChatColor.stripColor(word);
							//Parse starting exp from crazy string thingy
							startEXP = Integer.parseInt(word.split("/")[0]);
							//Parse max exp from crazy string thingy
							maxEXP = Integer.parseInt(word.split("/")[1]);
							expLine = formatEXPLine(item, startEXP, maxEXP, expToAdd, level, maxEXPAdd, p);
							if (startEXP + expToAdd >= maxEXP) {
								levelUp = true;
							}
							break;
						}
					}
				}
				indexToRemove++;
			}
			//Add level stuff
			lore.set(lore.size() - 2, expLine);
			if (levelUp) {
				int levelToBe = level + 1;
				lore.set(lore.size() - 1, CastleCraftCustomItems.getModifier(item, true));
				lore.set(lore.size() - 3, formatTypeLine(levelToBe));
			} else {
				lore.set(lore.size() - 1, CastleCraftCustomItems.getModifier(item, false));
			}
			ItemMeta meta = item.getItemMeta();
			meta.setLore(lore);
			item.setItemMeta(meta);
		}

	}

	public static String formatEXPLine(ItemStack item, int startEXP, int maxEXP, int expToAdd, int level, int maxEXPAdd, Player p) {
		List<String> format = CastleCraftCustomItems.getLoreFormat();
		for (String line : format) {
			line = ChatColor.stripColor(line);
			if (line.contains("<currentXP>") && line.contains("<maxXP>") && line.contains("<level>")) {
				//When just adding EXP
				if (startEXP + expToAdd < maxEXP) {
					//Compile neato xp line
					line = "§aLevel: §3" + level + " §4| §aEXP: §3" + (startEXP + expToAdd) + "§a/§3" + maxEXP;
					return line;
				//When leveling up
				} else {
					line = "§aLevel: §3" + (level + 1) + " §4| §aEXP: §3" + (startEXP + expToAdd - maxEXP) + "§a/§3" + (maxEXP + maxEXPAdd);
					p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5F, 0.9F);	
					return line;
				}
			}
		}
		return null;
		
	}
	
	public static String formatTypeLine(int level) {
		if (level < 5) {
			return CastleCraftCustomItems.getTypes().get(0);
		} else if (level < 15) {
			return CastleCraftCustomItems.getTypes().get(1);
		} else if (level < 25) {
			return CastleCraftCustomItems.getTypes().get(2);
		} else if (level < 50) {
			return CastleCraftCustomItems.getTypes().get(3);
		} else if (level < 75) {
			return CastleCraftCustomItems.getTypes().get(4);
		} else {
			return CastleCraftCustomItems.getTypes().get(5);
		}
	}
	
	public static Material getDrop(ItemStack item) {
		Map<Integer, String> rareDrops = CastleCraftCustomItems.getRareDrops().get(item.getType().toString());
		Material itemToDrop = Material.AIR;
		int randomMax = 0;
		for (int biggestNum : rareDrops.keySet()) {
			if (biggestNum > randomMax) {
				randomMax = biggestNum;
			}
		}
		//This is the random value that determines the drop
		int randomTicket = (int) (Math.random() * randomMax);
		for (int randomDropValue : rareDrops.keySet()) {
			if (randomTicket < randomDropValue) {
				itemToDrop = Material.getMaterial(rareDrops.get(randomDropValue));
				Bukkit.broadcastMessage(itemToDrop.toString());
				break;
			}
		}
		return itemToDrop;
	}

}
