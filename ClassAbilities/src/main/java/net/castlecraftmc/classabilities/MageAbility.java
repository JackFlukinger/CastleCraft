package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class MageAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();

	@EventHandler
	public void Fireball(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.FLINT) {
				double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
                double yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
                double x = Math.sin(pitch) * Math.cos(yaw);
                double y = Math.sin(pitch) * Math.sin(yaw);
                double z = Math.cos(pitch);
                Vector vector = new Vector(x, z, y);
                Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection().multiply(3)).toLocation(p.getWorld());
				int seconds = 15;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.mage1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Fireball§c!");
                    Fireball fireball1 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball1.setDirection(vector.multiply(10));
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.mage2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Fireball§c!");
                    Fireball fireball1 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball1.setDirection(vector.multiply(10));
                    Fireball fireball2 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball2.setDirection(vector.multiply(10));
                    Fireball fireball3 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball3.setDirection(vector.multiply(10));
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.mage3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Fireball§c!");
                    Fireball fireball1 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball1.setDirection(vector.multiply(10));
                    Fireball fireball2 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball2.setDirection(vector.multiply(10));
                    Fireball fireball3 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball3.setDirection(vector.multiply(10));
                    Fireball fireball4 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball4.setDirection(vector.multiply(10));
                    Fireball fireball5 = p.getWorld().spawn(spawnAt, Fireball.class);
                    fireball5.setDirection(vector.multiply(10));
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
				}
				else if (p.hasPermission("essentials.kits.mage1") | p.hasPermission("essentials.kits.mage2") | p.hasPermission("essentials.kits.mage3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
