package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class ClericAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();
	
	@EventHandler
	public void Heal(PlayerInteractEvent e) {
		PotionEffect regen1 = new PotionEffect(PotionEffectType.REGENERATION, 200, 0, false);
		PotionEffect regen2 = new PotionEffect(PotionEffectType.REGENERATION, 200, 1, false);
		PotionEffect regen3 = new PotionEffect(PotionEffectType.REGENERATION, 200, 2, false);
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.SEEDS) {
				int seconds = 60;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.cleric1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Heal§c!");
                    p.addPotionEffect(regen1, true);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 3; i++) {
						final SphereEffect effect = new SphereEffect(ClassAbilities.em);
						effect.particle = ParticleEffect.FIREWORKS_SPARK;
						effect.radius = i;
						effect.delay = i * 2;
						effect.setEntity(p);
						effect.yOffset = -1;
						effect.period = 0;
						effect.start();
	                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
	                        public void run() {
								effect.cancel();
	                        }
	                    }, 200);
					}
				}
				else if (p.hasPermission("essentials.kits.cleric2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Heal§c!");
                    p.addPotionEffect(regen2, true);
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 3; i++) {
						final SphereEffect effect = new SphereEffect(ClassAbilities.em);
						effect.particle = ParticleEffect.FIREWORKS_SPARK;
						effect.radius = i;
						effect.delay = i * 2;
						effect.setEntity(p);
						effect.yOffset = -1;
						effect.period = 0;
						effect.start();
	                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
	                        public void run() {
								effect.cancel();
	                        }
	                    }, 200);
					}
				}
				else if (p.hasPermission("essentials.kits.cleric3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Heal§c!");
                    p.addPotionEffect(regen3, true);
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 3; i++) {
						final SphereEffect effect = new SphereEffect(ClassAbilities.em);
						effect.particle = ParticleEffect.FIREWORKS_SPARK;
						effect.radius = i;
						effect.delay = i * 2;
						effect.setEntity(p);
						effect.yOffset = -1;
						effect.period = 0;
						effect.start();
	                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
	                        public void run() {
								effect.cancel();
	                        }
	                    }, 200);
					}
				}
				else if (p.hasPermission("essentials.kits.cleric1") | p.hasPermission("essentials.kits.cleric2") | p.hasPermission("essentials.kits.cleric3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}

}
