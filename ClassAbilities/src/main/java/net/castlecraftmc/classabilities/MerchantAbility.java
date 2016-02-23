package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class MerchantAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();
	static MiscDisguise goldPressurePlate = new MiscDisguise(DisguiseType.FALLING_BLOCK, 147);

	@EventHandler
	public void MoltenGold(PlayerInteractEvent e) {
		PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 4, true);
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.GOLD_NUGGET) {
				int seconds = 120;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.merchant1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Molten Gold§c!");
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					final SphereEffect effect = new SphereEffect(ClassAbilities.em);
					effect.particle = ParticleEffect.FLAME;
					effect.radius = 4;
					effect.iterations = 1;
					effect.particles = 100;
					effect.setEntity(p);
					effect.yOffset = -1;
					effect.period = 0;
					effect.start();
					for (int i = 0 ; i < 15 ; i++) {
						DisguiseAPI.disguiseNextEntity(goldPressurePlate);
						final Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation().add(Math.random() * 4 - 2, Math.random() * 4 - 2, Math.random() * 4 - 2), EntityType.WOLF);
						wolf.setMaxHealth(2000);
						wolf.setHealth(2000);
						wolf.addPotionEffect(slow, true);
						Delay.killAfter(wolf, 200 + i);
						wolf.setOwner(p);
						for (int j = 0 ; j < wolf.getNearbyEntities(2, 1, 2).size() ; j++) {
							if (wolf.getNearbyEntities(2, 1, 2).get(j) instanceof LivingEntity && wolf.getNearbyEntities(2, 1, 2).get(j) != p) {
								wolf.setTarget((LivingEntity) wolf.getNearbyEntities(2, 1, 2).get(j));
								break;
							}
						}
					}
				}
				else if (p.hasPermission("essentials.kits.merchant2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Molten Gold§c!");
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					final SphereEffect effect = new SphereEffect(ClassAbilities.em);
					effect.particle = ParticleEffect.FLAME;
					effect.radius = 4;
					effect.iterations = 1;
					effect.particles = 100;
					effect.setEntity(p);
					effect.yOffset = -1;
					effect.period = 0;
					effect.start();
					for (int i = 0 ; i < 25 ; i++) {
						DisguiseAPI.disguiseNextEntity(goldPressurePlate);
						final Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation().add(Math.random() * 4 - 2, Math.random() * 4 - 2, Math.random() * 4 - 2), EntityType.WOLF);
						wolf.setMaxHealth(2000);
						wolf.setHealth(2000);
						wolf.addPotionEffect(slow, true);
						Delay.killAfter(wolf, 200 + i);
						wolf.setOwner(p);
						for (int j = 0 ; j < wolf.getNearbyEntities(2, 1, 2).size() ; j++) {
							if (wolf.getNearbyEntities(2, 1, 2).get(j) instanceof LivingEntity && wolf.getNearbyEntities(2, 1, 2).get(j) != p) {
								wolf.setTarget((LivingEntity) wolf.getNearbyEntities(2, 1, 2).get(j));
								break;
							}
						}
					}
				}
				else if (p.hasPermission("essentials.kits.merchant3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Molten Gold§c!");
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					final SphereEffect effect = new SphereEffect(ClassAbilities.em);
					effect.particle = ParticleEffect.FLAME;
					effect.radius = 4;
					effect.iterations = 1;
					effect.particles = 100;
					effect.setEntity(p);
					effect.yOffset = -1;
					effect.period = 0;
					effect.start();
					for (int i = 0 ; i < 40 ; i++) {
						DisguiseAPI.disguiseNextEntity(goldPressurePlate);
						final Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation().add(Math.random() * 4 - 2, Math.random() * 4 - 2, Math.random() * 4 - 2), EntityType.WOLF);
						wolf.setMaxHealth(2000);
						wolf.setHealth(2000);
						wolf.addPotionEffect(slow, true);
						Delay.killAfter(wolf, 200 + i);
						wolf.setOwner(p);
						for (int j = 0 ; j < wolf.getNearbyEntities(2, 1, 2).size() ; j++) {
							if (wolf.getNearbyEntities(2, 1, 2).get(j) instanceof LivingEntity && wolf.getNearbyEntities(2, 1, 2).get(j) != p) {
								wolf.setTarget((LivingEntity) wolf.getNearbyEntities(2, 1, 2).get(j));
								break;
							}
						}
					}
				}
				else if (p.hasPermission("essentials.kits.merchant1") | p.hasPermission("essentials.kits.merchant2") | p.hasPermission("essentials.kits.merchant3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
