package net.castlecraftmc.classabilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

public abstract class Delay implements Plugin {
	public static void killAfter(final LivingEntity w, final int ticks) {
		Bukkit.getServer().getScheduler().runTaskLater(ClassAbilities.getPlugin(), new Runnable() {
			public void run() {
				if (w.getLocation().getChunk().isLoaded()) {
					//Bukkit.getConsoleSender().sendMessage("1");
					for (Entity e : w.getLocation().getChunk().getEntities()) {
						//Bukkit.getConsoleSender().sendMessage("2");
						if (e.getUniqueId().equals(w.getUniqueId())) {
							//Bukkit.getConsoleSender().sendMessage("3");
							while (!e.isDead()) {
								e.remove();
							}
						}
					}
				}
				else {
					//Bukkit.getConsoleSender().sendMessage("4");
					w.getLocation().getChunk().load();
					Bukkit.getServer().getScheduler().runTaskLater(ClassAbilities.getPlugin(), new Runnable() {
						public void run() {
							for (Entity e : w.getLocation().getChunk().getEntities()) {
								if (e.getUniqueId().equals(w.getUniqueId())) {
									while(!e.isDead()) {
										e.remove();
									}
								}
							}
						}
					}, 1);
				}
			}
		}, ticks);
	}
	public static void cooldownDone(final Player p, final int ticks, final ArrayList<String> cooldown) {
		Bukkit.getServer().getScheduler().runTaskLater(ClassAbilities.getPlugin(), new Runnable() {
			public void run() {
				cooldown.remove(p.getName());
				Player player = Bukkit.getPlayer(p.getUniqueId());
				player.sendMessage("§6[§aC§2C§6] §cYou can use your ability!");
			}
		}, ticks);
	}
	public static void canRequestTownCooldownDone(final Player p, final int ticks) {
		Bukkit.getServer().getScheduler().runTaskLater(ClassAbilities.getPlugin(), new Runnable() {
			public void run() {
				ClassAbilities.cooldown.remove(p.getName());
			}
		}, ticks);
	}
	public static boolean isPlayerInTown(Resident r) {
		try {
			@SuppressWarnings("unused")
			Town town = r.getTown();
			return true;
		} catch (NotRegisteredException e) {
			return false;
		}
	}
}