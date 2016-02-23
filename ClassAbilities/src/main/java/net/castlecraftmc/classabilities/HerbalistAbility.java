package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.slikey.effectlib.effect.CylinderEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class HerbalistAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Grow(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.SLIME_BALL) {
				int seconds = 600;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.herbalist1") && cooldown.contains(p.getName()) == false) {
					int radius = 7;
					Location loc = p.getLocation();
					World world = loc.getWorld();
					final CylinderEffect effect = new CylinderEffect(ClassAbilities.em);
   					effect.radius = radius;
					effect.iterations = 1;
					effect.particles = 30 * radius;
					effect.particle = ParticleEffect.VILLAGER_HAPPY;
					effect.height = 0.5F;
					effect.setLocation(p.getLocation());
					effect.autoOrient = true;
					effect.start();
					for (int x = -radius; x < radius; x++) {
					    for (int y = -radius; y < radius; y++) {
					        for (int z = -radius; z < radius; z++) {
					            Block block = world.getBlockAt(loc.getBlockX()+x, loc.getBlockY()+y, loc.getBlockZ()+z);
					            if (block.getType() == Material.CARROT || block.getType() == Material.CROPS || block.getType() == Material.POTATO || block.getType() == Material.NETHER_WARTS || block.getType() == Material.PUMPKIN_STEM || block.getType() == Material.MELON_STEM) {
					                block.setData((byte) 7);
					            }
					            if (block.getType() == Material.PUMPKIN_STEM) {
					            	for (int i = 0; i < 3; i++) {
					            		if (block.getLocation().add(1 - i, 0, 0).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(1 - i, 0, 0).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.PUMPKIN);
						            			break;
					            			}
					            		}
					            		if (block.getLocation().add(0, 0, 1 - i).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(0, 0, 1 - i).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.PUMPKIN);
						            			break;
					            			}
					            		}
					            	}
					            }
					            if (block.getType() == Material.MELON_STEM) {
					            	for (int i = 0; i < 3; i++) {
					            		if (block.getLocation().add(1 - i, 0, 0).getBlock().getType() == Material.AIR | block.getLocation().add(1 - i, 0, 0).getBlock().getType() == null) {
					            			Block placeBlock = block.getLocation().add(1 - i, 0, 0).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.MELON_BLOCK);
						            			break;
					            			}
					            		}
					            		if (block.getLocation().add(0, 0, 1 - i).getBlock().getType() == Material.AIR | block.getLocation().add(1 - i, 0, 0).getBlock().getType() == null) {
					            			Block placeBlock = block.getLocation().add(0, 0, 1 - i).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.MELON_BLOCK);
						            			break;
					            			}
					            		}
					            	}
					            }
					        }
					    }
					}
					p.sendMessage("§6[§aC§2C§6] §cYou used Grow§c!");
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.herbalist2") && cooldown.contains(p.getName()) == false) {
					int radius = 12;
					Location loc = p.getLocation();
					World world = loc.getWorld();
					final CylinderEffect effect = new CylinderEffect(ClassAbilities.em);
   					effect.radius = radius;
					effect.iterations = 1;
					effect.particles = 30 * radius;
					effect.particle = ParticleEffect.VILLAGER_HAPPY;
					effect.height = 0.5F;
					effect.setLocation(p.getLocation());
					effect.autoOrient = true;
					effect.start();
					for (int x = -radius; x < radius; x++) {
					    for (int y = -radius; y < radius; y++) {
					        for (int z = -radius; z < radius; z++) {
					            Block block = world.getBlockAt(loc.getBlockX()+x, loc.getBlockY()+y, loc.getBlockZ()+z);
					            if (block.getType() == Material.CARROT || block.getType() == Material.CROPS || block.getType() == Material.POTATO || block.getType() == Material.NETHER_WARTS || block.getType() == Material.PUMPKIN_STEM || block.getType() == Material.MELON_STEM) {
					                block.setData((byte) 7);
					            }
					            if (block.getType() == Material.PUMPKIN_STEM) {
					            	for (int i = 0; i < 3; i++) {
					            		if (block.getLocation().add(1 - i, 0, 0).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(1 - i, 0, 0).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.PUMPKIN);
						            			break;
					            			}
					            		}
					            		if (block.getLocation().add(0, 0, 1 - i).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(0, 0, 1 - i).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.PUMPKIN);
						            			break;
					            			}
					            		}
					            	}
					            }
					            if (block.getType() == Material.MELON_STEM) {
					            	for (int i = 0; i < 3; i++) {
					            		if (block.getLocation().add(1 - i, 0, 0).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(1 - i, 0, 0).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.MELON_BLOCK);
						            			break;
					            			}
					            		}
					            		if (block.getLocation().add(0, 0, 1 - i).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(0, 0, 1 - i).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.MELON_BLOCK);
						            			break;
					            			}
					            		}
					            	}
					            }
					        }
					    }
					}
					p.sendMessage("§6[§aC§2C§6] §cYou used Grow§c!");
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.herbalist3") && cooldown.contains(p.getName()) == false) {
					int radius = 20;
					Location loc = p.getLocation();
					World world = loc.getWorld();
					final CylinderEffect effect = new CylinderEffect(ClassAbilities.em);
   					effect.radius = radius;
					effect.iterations = 1;
					effect.particles = 30 * radius;
					effect.particle = ParticleEffect.VILLAGER_HAPPY;
					effect.height = 0.5F;
					effect.setLocation(p.getLocation());
					effect.autoOrient = true;
					effect.start();
					for (int x = -radius; x < radius; x++) {
					    for (int y = -radius; y < radius; y++) {
					        for (int z = -radius; z < radius; z++) {
					            Block block = world.getBlockAt(loc.getBlockX()+x, loc.getBlockY()+y, loc.getBlockZ()+z);
					            if (block.getType() == Material.CARROT || block.getType() == Material.CROPS || block.getType() == Material.POTATO || block.getType() == Material.NETHER_WARTS || block.getType() == Material.PUMPKIN_STEM || block.getType() == Material.MELON_STEM) {
					                block.setData((byte) 7);
					            }
					            if (block.getType() == Material.PUMPKIN_STEM) {
					            	for (int i = 0; i < 3; i++) {
					            		if (block.getLocation().add(1 - i, 0, 0).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(1 - i, 0, 0).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.PUMPKIN);
						            			break;
					            			}
					            		}
					            		if (block.getLocation().add(0, 0, 1 - i).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(0, 0, 1 - i).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.PUMPKIN);
						            			break;
					            			}
					            		}
					            	}
					            }
					            if (block.getType() == Material.MELON_STEM) {
					            	for (int i = 0; i < 3; i++) {
					            		if (block.getLocation().add(1 - i, 0, 0).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(1 - i, 0, 0).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.MELON_BLOCK);
						            			break;
					            			}
					            		}
					            		if (block.getLocation().add(0, 0, 1 - i).getBlock().getType() == Material.AIR) {
					            			Block placeBlock = block.getLocation().add(0, 0, 1 - i).getBlock();
					            			if (placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.GRASS || placeBlock.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIRT) {
						            			placeBlock.setType(Material.MELON_BLOCK);
						            			break;
					            			}
					            		}
					            	}
					            }
					        }
					    }
					}
					p.sendMessage("§6[§aC§2C§6] §cYou used Grow§c!");
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.herbalist1") | p.hasPermission("essentials.kits.herbalist2") | p.hasPermission("essentials.kits.herbalist3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
