package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import de.slikey.effectlib.effect.CylinderEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class NecromancerAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();
    
	@EventHandler
	public void Summon(PlayerInteractEvent e) {
		PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1);
		ItemStack blazeRod = new ItemStack(Material.BLAZE_ROD, 1);
		final MobDisguise skeleton = new MobDisguise(DisguiseType.SKELETON);
		FlagWatcher watcher = skeleton.getWatcher();
		watcher.setItemInHand(blazeRod);
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
				Long usedNow = System.currentTimeMillis();
				int seconds = 120;
				if (p.hasPermission("essentials.kits.necromancer1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Summon§c!");
                    Location location = p.getEyeLocation();
                    BlockIterator iterator = new BlockIterator(location, 0D, 100);
                    Location blockToAdd;
                    while(iterator.hasNext()) {
                        blockToAdd = iterator.next().getLocation();
                        if (blockToAdd.getBlock().getType() != Material.AIR) {
                        	for (int i = 0; i < 3 ; i++) {
                        		DisguiseAPI.disguiseNextEntity(skeleton);
                        		Wolf wolf = (Wolf) p.getWorld().spawnEntity(blockToAdd.add(1 - Math.random() * 2, 2 - i, 1 - Math.random() * 2), EntityType.WOLF);
                        		wolf.setHealth(2);
                        		wolf.addPotionEffect(strength, true);
                        		wolf.setOwner(p);
                        		Delay.killAfter(wolf, 300);
            					final CylinderEffect effect = new CylinderEffect(ClassAbilities.em);
               					effect.radius = 1;
            					effect.iterations = 1;
            					effect.particles = 200;
            					effect.particle = ParticleEffect.FLAME;
            					effect.height = 3;
            					effect.setLocation(wolf.getEyeLocation());
            					effect.autoOrient = true;
            					effect.start();
                        	}
                    		break;
                        }
                    }
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.necromancer2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Summon§c!");
                    Location location = p.getEyeLocation();
                    BlockIterator iterator = new BlockIterator(location, 0D, 100);
                    Location blockToAdd;
                    while(iterator.hasNext()) {
                        blockToAdd = iterator.next().getLocation();
                        if (blockToAdd.getBlock().getType() != Material.AIR) {
                        	for (int i = 0; i < 6 ; i++) {
                        		DisguiseAPI.disguiseNextEntity(skeleton);
                        		Wolf wolf = (Wolf) p.getWorld().spawnEntity(blockToAdd.add(1 - Math.random() * 2, 2 - i, 1 - Math.random() * 2), EntityType.WOLF);
                        		wolf.setHealth(2);
                        		wolf.addPotionEffect(strength, true);
                        		wolf.setOwner(p);
                        		Delay.killAfter(wolf, 300);
            					final CylinderEffect effect = new CylinderEffect(ClassAbilities.em);
               					effect.radius = 1;
            					effect.iterations = 1;
            					effect.particles = 200;
            					effect.particle = ParticleEffect.FLAME;
            					effect.height = 3;
            					effect.setLocation(wolf.getEyeLocation());
            					effect.autoOrient = true;
            					effect.start();
                        	}
                    		break;
                        }
                    }
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.necromancer3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Summon§c!");
                    Location location = p.getEyeLocation();
                    BlockIterator iterator = new BlockIterator(location, 0D, 100);
                    Location blockToAdd;
                    while(iterator.hasNext()) {
                        blockToAdd = iterator.next().getLocation();
                        if (blockToAdd.getBlock().getType() != Material.AIR) {
                        	for (int i = 0; i < 9 ; i++) {
                        		DisguiseAPI.disguiseNextEntity(skeleton);
                        		Wolf wolf = (Wolf) p.getWorld().spawnEntity(blockToAdd.add(1 - Math.random() * 2, 2 - i, 1 - Math.random() * 2), EntityType.WOLF);
                        		wolf.setHealth(2);
                        		wolf.addPotionEffect(strength, true);
                        		wolf.setOwner(p);
                        		Delay.killAfter(wolf, 300);
            					final CylinderEffect effect = new CylinderEffect(ClassAbilities.em);
               					effect.radius = 1;
            					effect.iterations = 1;
            					effect.particles = 200;
            					effect.particle = ParticleEffect.FLAME;
            					effect.height = 3;
            					effect.setLocation(wolf.getEyeLocation());
            					effect.autoOrient = true;
            					effect.start();
                        	}
                    		break;
                        }
                    }
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.necromancer1") | p.hasPermission("essentials.kits.necromancer2") | p.hasPermission("essentials.kits.necromancer3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
