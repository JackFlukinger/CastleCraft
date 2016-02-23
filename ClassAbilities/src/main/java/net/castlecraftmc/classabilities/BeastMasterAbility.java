package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BeastMasterAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();
	
	@EventHandler
	public void beastCall(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.BONE) {
				int seconds = 60;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.beastmaster1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used BeastCall§c!");
					Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
					wolf.setMaxHealth(1000.0);
					wolf.setHealth(1000.0);
					wolf.setBreed(false);
					wolf.setTamed(true);
					wolf.setOwner(p);
					wolf.setAngry(true);
					Delay.killAfter(wolf, 200);
					previousUse.put(p.getName(),System.currentTimeMillis());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					cooldown.add(p.getName());
					if (p.getLastDamageCause() instanceof Player || p.getLastDamageCause() instanceof Arrow || p.getLastDamageCause() instanceof LivingEntity) {
						if (p.getLastDamageCause() instanceof Arrow) {
							Arrow shotArrow = (Arrow) p.getLastDamageCause();
							LivingEntity damager = (LivingEntity) shotArrow.getShooter();
							wolf.setTarget(damager);
						} else if (p.getLastDamageCause() instanceof Player || p.getLastDamageCause() instanceof LivingEntity) {
							LivingEntity damager = (LivingEntity) p.getLastDamageCause();
							wolf.setTarget(damager);
						}
					}
				}
				else if (p.hasPermission("essentials.kits.beastmaster2") && cooldown.contains(p.getName()) == false) {
					for (int i = 0; i < 3; i++) {
						p.sendMessage("§6[§aC§2C§6] §cYou used BeastCall§c!");
						Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
						wolf.setMaxHealth(1000.0);
						wolf.setHealth(1000.0);
						wolf.setBreed(false);
						wolf.setTamed(true);
						wolf.setOwner(p);
						wolf.setAngry(true);
						Delay.killAfter(wolf, 200);
						previousUse.put(p.getName(),System.currentTimeMillis());
						Delay.cooldownDone(p, (seconds*20), cooldown);
						cooldown.add(p.getName());
						if (p.getLastDamageCause() instanceof Player || p.getLastDamageCause() instanceof Arrow || p.getLastDamageCause() instanceof LivingEntity) {
							if (p.getLastDamageCause() instanceof Arrow) {
								Arrow shotArrow = (Arrow) p.getLastDamageCause();
								LivingEntity damager = (LivingEntity) shotArrow.getShooter();
								wolf.setTarget(damager);
							} else if (p.getLastDamageCause() instanceof Player || p.getLastDamageCause() instanceof LivingEntity) {
								LivingEntity damager = (LivingEntity) p.getLastDamageCause();
								wolf.setTarget(damager);
							}
						}
					}
				}
				else if (p.hasPermission("essentials.kits.beastmaster3") && cooldown.contains(p.getName()) == false) {
					for (int i = 0; i < 6; i++) {
						p.sendMessage("§6[§aC§2C§6] §cYou used BeastCall§c!");
						Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
						wolf.setMaxHealth(1000.0);
						wolf.setHealth(1000.0);
						wolf.setBreed(false);
						wolf.setTamed(true);
						wolf.setOwner(p);
						wolf.setAngry(true);
						Delay.killAfter(wolf, 200);
						previousUse.put(p.getName(),System.currentTimeMillis());
						Delay.cooldownDone(p, (seconds*20), cooldown);
						cooldown.add(p.getName());
						if (p.getLastDamageCause() instanceof Player || p.getLastDamageCause() instanceof Arrow || p.getLastDamageCause() instanceof LivingEntity) {
							if (p.getLastDamageCause() instanceof Arrow) {
								Arrow shotArrow = (Arrow) p.getLastDamageCause();
								LivingEntity damager = (LivingEntity) shotArrow.getShooter();
								wolf.setTarget(damager);
							} else if (p.getLastDamageCause() instanceof Player || p.getLastDamageCause() instanceof LivingEntity) {
								LivingEntity damager = (LivingEntity) p.getLastDamageCause();
								wolf.setTarget(damager);
							}
						}
					}
				}
				else if (p.hasPermission("essentials.kits.beastmaster1") | p.hasPermission("essentials.kits.beastmaster2") | p.hasPermission("essentials.kits.beastmaster3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
