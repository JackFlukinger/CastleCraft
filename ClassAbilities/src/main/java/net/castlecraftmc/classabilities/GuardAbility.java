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

import de.slikey.effectlib.effect.ShieldEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class GuardAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();

	@EventHandler
	public void Guard(PlayerInteractEvent e) {
		PotionEffect slow10 = new PotionEffect(PotionEffectType.SLOW, 200, 0, true);
		PotionEffect slow20 = new PotionEffect(PotionEffectType.SLOW, 400, 0, true);
		PotionEffect slow35 = new PotionEffect(PotionEffectType.SLOW, 700, 0, true);
		PotionEffect strength10 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1, true);
		PotionEffect strength20 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 1, true);
		PotionEffect strength35 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 700, 1, true);
		PotionEffect resistance10 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1, true);
		PotionEffect resistance20 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 1, true);
		PotionEffect resistance35 = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 700, 1, true);
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.GLOWSTONE_DUST) {
				int seconds = 180;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.guard1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Fortify§c!");
                    p.addPotionEffect(slow10, true);
                    p.addPotionEffect(strength10, true);
                    p.addPotionEffect(resistance10, true);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					final ShieldEffect effect = new ShieldEffect(ClassAbilities.em);
					effect.sphere = true;
					effect.infinite();
					effect.particles = 100;
					effect.particle = ParticleEffect.CRIT_MAGIC;
					effect.radius = 2;
					effect.setEntity(p);
					effect.start();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                        public void run() {
                                effect.cancel();
                        }
                    }, 200);
				}
				else if (p.hasPermission("essentials.kits.guard2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Fortify§c!");
                    p.addPotionEffect(slow20, true);
                    p.addPotionEffect(strength20, true);
                    p.addPotionEffect(resistance20, true);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					final ShieldEffect effect = new ShieldEffect(ClassAbilities.em);
					effect.sphere = true;
					effect.infinite();
					effect.particles = 100;
					effect.particle = ParticleEffect.CRIT_MAGIC;
					effect.radius = 2;
					effect.setEntity(p);
					effect.start();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                        public void run() {
                                effect.cancel();
                        }
                    }, 400);
				}
				else if (p.hasPermission("essentials.kits.guard3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Fortify§c!");
                    p.addPotionEffect(slow35, true);
                    p.addPotionEffect(strength35, true);
                    p.addPotionEffect(resistance35, true);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					final ShieldEffect effect = new ShieldEffect(ClassAbilities.em);
					effect.sphere = true;
					effect.infinite();
					effect.particles = 100;
					effect.particle = ParticleEffect.CRIT_MAGIC;
					effect.radius = 2;
					effect.setEntity(p);
					effect.start();
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                        public void run() {
                                effect.cancel();
                        }
                    }, 700);
				}
				else if (p.hasPermission("essentials.kits.guard1") | p.hasPermission("essentials.kits.guard2") | p.hasPermission("essentials.kits.guard3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
