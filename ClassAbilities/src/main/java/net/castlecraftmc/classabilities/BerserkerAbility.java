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

import de.slikey.effectlib.effect.VortexEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class BerserkerAbility implements Listener{

    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();
	
	@EventHandler
	public void Rage(PlayerInteractEvent e) {
		PotionEffect strength15 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 0, false);
		PotionEffect speed15 = new PotionEffect(PotionEffectType.SPEED, 300, 1, false);
		PotionEffect strength10 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0, false);
		PotionEffect speed10 = new PotionEffect(PotionEffectType.SPEED, 200, 1, false);
		PotionEffect strength5 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0, false);
		PotionEffect speed5 = new PotionEffect(PotionEffectType.SPEED, 100, 1, false);

		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.NETHER_STALK) {
				int seconds = 60;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.berserker1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Rage§c!");
                    p.addPotionEffect(strength5, true);
                    p.addPotionEffect(speed5, true);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					//Bukkit.getServer().getScheduler().runTaskTimer(ClassAbilities.getPlugin(), new Runnable() {
						//public void run() {
					for (int i = 0; i < 100; i++) {
							final VortexEffect effect = new VortexEffect(ClassAbilities.em);
							effect.particle = ParticleEffect.REDSTONE;
							effect.helixes = 3;
							effect.radius = 1;
							effect.delay = i;
		                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
		                        public void run() {
									effect.setLocation(p.getLocation());
									effect.setTarget(p.getLocation().add(0, 2, 0));
		                        }
		                    }, i);
							effect.circles = 10;
							effect.grow = 0.08F;
							effect.iterations = 3;
							effect.period = 0;
							effect.start();
					}
						//}
					//}, 0, 4);
				}
				else if (p.hasPermission("essentials.kits.berserker2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Rage§c!");
                    p.addPotionEffect(strength10, true);
                    p.addPotionEffect(speed10, true);
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 200; i++) {
						final VortexEffect effect = new VortexEffect(ClassAbilities.em);
						effect.particle = ParticleEffect.REDSTONE;
						effect.helixes = 3;
						effect.radius = 1;
						effect.delay = i;
	                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
	                        public void run() {
								effect.setLocation(p.getLocation());
								effect.setTarget(p.getLocation().add(0, 2, 0));
	                        }
	                    }, i);
						effect.circles = 10;
						effect.grow = 0.08F;
						effect.iterations = 3;
						effect.period = 0;
						effect.start();
					}
				}
				else if (p.hasPermission("essentials.kits.berserker3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Rage§c!");
                    p.addPotionEffect(strength15, true);
                    p.addPotionEffect(speed15, true);
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 300; i++) {
						final VortexEffect effect = new VortexEffect(ClassAbilities.em);
						effect.particle = ParticleEffect.REDSTONE;
						effect.helixes = 3;
						effect.radius = 1;
						effect.delay = i;
	                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
	                        public void run() {
								effect.setLocation(p.getLocation());
								effect.setTarget(p.getLocation().add(0, 2, 0));
	                        }
	                    }, i);
						effect.circles = 10;
						effect.grow = 0.08F;
						effect.iterations = 3;
						effect.period = 0;
						effect.start();
					}
				}
				else if (p.hasPermission("essentials.kits.berserker1") | p.hasPermission("essentials.kits.berserker2") | p.hasPermission("essentials.kits.berserker3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
