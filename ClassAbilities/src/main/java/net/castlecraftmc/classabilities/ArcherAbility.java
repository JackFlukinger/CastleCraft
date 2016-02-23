package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.watchers.ZombieWatcher;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.slikey.effectlib.effect.CylinderEffect;
import de.slikey.effectlib.util.ParticleEffect;

public class ArcherAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();

	@EventHandler
	public void ThrowingKnife(PlayerInteractEvent e) {
		ItemStack knifeItem = new ItemStack(Material.IRON_SWORD, 1);
		final MobDisguise throwingKnife = new MobDisguise(DisguiseType.ZOMBIE);
		ZombieWatcher watcher = (ZombieWatcher) throwingKnife.getWatcher();
		watcher.setInvisible(true);
		watcher.setItemInHand(knifeItem);
		watcher.setBaby();
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.STICK) {
				int seconds = 15;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.archer1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Throwing Knives§c!");
                    for (int i = 0; i < 2; i++) {
                    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                    		public void run() {
            					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
            	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
            	                double x = Math.sin(pitch) * Math.cos(yaw);
            	                double y = Math.sin(pitch) * Math.sin(yaw);
            	                double z = Math.cos(pitch);
            	                final Vector vector = new Vector(x, z, y);
            	                final Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection().multiply(2)).toLocation(p.getWorld());
                    			DisguiseAPI.disguiseNextEntity(throwingKnife);
                    			Arrow knifeProjectile = p.getWorld().spawn(spawnAt, Arrow.class);
                    			knifeProjectile.setCritical(true);
                    			knifeProjectile.setShooter(p);
                    			knifeProjectile.setVelocity(vector.multiply(3));
            					final CylinderEffect effect = new CylinderEffect(ClassAbilities.em);
               					effect.radius = 0;
            					effect.iterations = 20;
            					effect.particles = 1;
            					effect.particle = ParticleEffect.FIREWORKS_SPARK;
            					effect.height = 0.5F;
            					effect.setEntity(knifeProjectile);
            					effect.start();
                    		}
                    	}, 10 * i);
                    }
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.archer2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Throwing Knives§c!");
                    for (int i = 0; i < 3; i++) {
                    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                    		public void run() {
            					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
            	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
            	                double x = Math.sin(pitch) * Math.cos(yaw);
            	                double y = Math.sin(pitch) * Math.sin(yaw);
            	                double z = Math.cos(pitch);
            	                final Vector vector = new Vector(x, z, y);
            	                final Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection().multiply(2)).toLocation(p.getWorld());
                    			DisguiseAPI.disguiseNextEntity(throwingKnife);
                    			Arrow knifeProjectile = p.getWorld().spawn(spawnAt, Arrow.class);
                    			knifeProjectile.setCritical(true);
                    			knifeProjectile.setShooter(p);
                    			knifeProjectile.setVelocity(vector.multiply(3));
                    		}
                    	}, 10 * i);
                    }
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.archer3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Throwing Knives§c!");
                    for (int i = 0; i < 5; i++) {
                    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                    		public void run() {
            					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
            	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
            	                double x = Math.sin(pitch) * Math.cos(yaw);
            	                double y = Math.sin(pitch) * Math.sin(yaw);
            	                double z = Math.cos(pitch);
            	                final Vector vector = new Vector(x, z, y);
            	                final Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection().multiply(2)).toLocation(p.getWorld());
                    			DisguiseAPI.disguiseNextEntity(throwingKnife);
                    			Arrow knifeProjectile = p.getWorld().spawn(spawnAt, Arrow.class);
                    			knifeProjectile.setCritical(true);
                    			knifeProjectile.setShooter(p);
                    			knifeProjectile.setVelocity(vector.multiply(3));
                    		}
                    	},10 * i);
                    }
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.archer1") | p.hasPermission("essentials.kits.archer2") | p.hasPermission("essentials.kits.archer3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
