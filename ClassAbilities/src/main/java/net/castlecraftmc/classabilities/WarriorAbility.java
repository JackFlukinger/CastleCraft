package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class WarriorAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();

	@EventHandler
	public void Rush(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.FEATHER) {
				int seconds = 45;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.warrior1") && cooldown.contains(p.getName()) == false) {
					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
	                double x = Math.sin(pitch) * Math.cos(yaw);
	                double y = Math.sin(pitch) * Math.sin(yaw);
	                double z = Math.cos(pitch);
	                Vector vector = new Vector(x, z, y);
	                p.setVelocity(vector.multiply(3));
                    Location location = p.getEyeLocation();
                    BlockIterator iterator = new BlockIterator(location, 0D, 100);
                    Location blockToAdd;
                    while(iterator.hasNext()) {
                        blockToAdd = iterator.next().getLocation();
                        if (blockToAdd.getBlock().getType() != Material.AIR && blockToAdd.getBlock().getType() != Material.LONG_GRASS) {
                        	p.getWorld().strikeLightning(blockToAdd);
                        	break;
                        }
                    }
					p.sendMessage("§6[§aC§2C§6] §cYou used Rush§c!");
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.warrior2") && cooldown.contains(p.getName()) == false) {
					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
	                double x = Math.sin(pitch) * Math.cos(yaw);
	                double y = Math.sin(pitch) * Math.sin(yaw);
	                double z = Math.cos(pitch);
	                Vector vector = new Vector(x, z, y);
	                p.setVelocity(vector.multiply(3));
                    Location location = p.getEyeLocation();
                    BlockIterator iterator = new BlockIterator(location, 0D, 100);
                    Location blockToAdd;
                    while(iterator.hasNext()) {
                        blockToAdd = iterator.next().getLocation();
                        if (blockToAdd.getBlock().getType() != Material.AIR && blockToAdd.getBlock().getType() != Material.LONG_GRASS) {
                        	p.getWorld().strikeLightning(blockToAdd);
                        	p.getWorld().strikeLightning(blockToAdd);
                        	p.getWorld().strikeLightning(blockToAdd);
                        	break;
                        }
                    }
					p.sendMessage("§6[§aC§2C§6] §cYou used Rush§c!");
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.warrior3") && cooldown.contains(p.getName()) == false) {
					double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
	                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
	                double x = Math.sin(pitch) * Math.cos(yaw);
	                double y = Math.sin(pitch) * Math.sin(yaw);
	                double z = Math.cos(pitch);
	                Vector vector = new Vector(x, z, y);
	                p.setVelocity(vector.multiply(3));
                    Location location = p.getEyeLocation();
                    BlockIterator iterator = new BlockIterator(location, 0D, 100);
                    Location blockToAdd;
                    while(iterator.hasNext()) {
                        blockToAdd = iterator.next().getLocation();
                        if (blockToAdd.getBlock().getType() != Material.AIR && blockToAdd.getBlock().getType() != Material.LONG_GRASS) {
                        	p.getWorld().strikeLightning(blockToAdd);
                        	p.getWorld().strikeLightning(blockToAdd);
                        	p.getWorld().strikeLightning(blockToAdd);
                        	p.getWorld().strikeLightning(blockToAdd);
                        	p.getWorld().strikeLightning(blockToAdd);
                        	break;
                        }
                    }
					p.sendMessage("§6[§aC§2C§6] §cYou used Rush§c!");
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.warrior1") | p.hasPermission("essentials.kits.warrior2") | p.hasPermission("essentials.kits.warrior3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
