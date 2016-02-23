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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MinerAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();

	@EventHandler
	public void Grenade(PlayerInteractEvent e) {
		ItemStack tntItem = new ItemStack(Material.TNT, 1);
		final MobDisguise tnt = new MobDisguise(DisguiseType.ZOMBIE);
		ZombieWatcher watcher = (ZombieWatcher) tnt.getWatcher();
		watcher.setInvisible(true);
		watcher.setItemInHand(tntItem);
		watcher.setBaby();
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.SULPHUR) {
				if (p.hasPermission("essentials.kits.miner1") && cooldown.contains(p.getName()) == false) {
					int seconds = 180;
					p.sendMessage("§6[§aC§2C§6] §cYou used Grenade§c!");
					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
	                double x = Math.sin(pitch) * Math.cos(yaw);
	                double y = Math.sin(pitch) * Math.sin(yaw);
	                double z = Math.cos(pitch);
	                final Vector vector = new Vector(x, z, y);
	                final Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection().multiply(2)).toLocation(p.getWorld());
        			DisguiseAPI.disguiseNextEntity(tnt);
        			final Snowball tntShot = p.getWorld().spawn(spawnAt, Snowball.class);
        			tntShot.setShooter(p);
        			tntShot.setVelocity(vector.multiply(2));
        			tntShot.setBounce(true);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                        public void run() {
                                Location landLoc = tntShot.getLocation();
                                tntShot.remove();
                                p.getWorld().spawnEntity(landLoc, EntityType.PRIMED_TNT);
                        }
                    }, 30);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.miner2") && cooldown.contains(p.getName()) == false) {
					int seconds = 150;
					p.sendMessage("§6[§aC§2C§6] §cYou used Grenade§c!");
					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
	                double x = Math.sin(pitch) * Math.cos(yaw);
	                double y = Math.sin(pitch) * Math.sin(yaw);
	                double z = Math.cos(pitch);
	                final Vector vector = new Vector(x, z, y);
	                final Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection().multiply(2)).toLocation(p.getWorld());
        			DisguiseAPI.disguiseNextEntity(tnt);
        			final Snowball tntShot = p.getWorld().spawn(spawnAt, Snowball.class);
        			tntShot.setShooter(p);
        			tntShot.setVelocity(vector.multiply(2));
        			tntShot.setBounce(true);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                        public void run() {
                                Location landLoc = tntShot.getLocation();
                                tntShot.remove();
                                p.getWorld().spawnEntity(landLoc, EntityType.PRIMED_TNT);
                        }
                    }, 30);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.miner3") && cooldown.contains(p.getName()) == false) {
					int seconds = 120;
					p.sendMessage("§6[§aC§2C§6] §cYou used Grenade§c!");
					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
	                double x = Math.sin(pitch) * Math.cos(yaw);
	                double y = Math.sin(pitch) * Math.sin(yaw);
	                double z = Math.cos(pitch);
	                final Vector vector = new Vector(x, z, y);
	                final Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection().multiply(2)).toLocation(p.getWorld());
        			DisguiseAPI.disguiseNextEntity(tnt);
        			final Snowball tntShot = p.getWorld().spawn(spawnAt, Snowball.class);
        			tntShot.setShooter(p);
        			tntShot.setVelocity(vector.multiply(2));
        			tntShot.setBounce(true);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClassAbilities.getPlugin(), new Runnable() {
                        public void run() {
                                Location landLoc = tntShot.getLocation();
                                tntShot.remove();
                                p.getWorld().spawnEntity(landLoc, EntityType.PRIMED_TNT);
                        }
                    }, 30);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.miner1") | p.hasPermission("essentials.kits.miner2") | p.hasPermission("essentials.kits.miner3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can't use Grenade yet.");
				}
			}
		}
	}
}
