package net.castlecraftmc.classabilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.effect.SmokeEffect;

public class AssassinAbility implements Listener{
	
    final static ArrayList<String> cooldown = new ArrayList<String>();
    final HashMap<String,Long> previousUse = new HashMap<String,Long>();
	
	@EventHandler
	public void Sneak(PlayerInteractEvent e) {
		PotionEffect invis1 = new PotionEffect(PotionEffectType.INVISIBILITY, 300, 0, true);
		PotionEffect invis2 = new PotionEffect(PotionEffectType.INVISIBILITY, 400, 0, true);
		PotionEffect invis3 = new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0, true);
		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (p.getItemInHand().getType() == Material.SUGAR) {
				int seconds = 180;
				Long usedNow = System.currentTimeMillis();
				if (p.hasPermission("essentials.kits.Assassin1") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Sneak§c!");
                    p.addPotionEffect(invis1, true);
                    cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 300; i++) {
						final SmokeEffect effect = new SmokeEffect(ClassAbilities.em);
						effect.setLocation(p.getLocation().add(Math.random() * 2 - 1, Math.random() * 2 - 1, Math.random() * 2 - 1));
						effect.start();
					}
				}
				else if (p.hasPermission("essentials.kits.Assassin2") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Sneak§c!");
                    p.addPotionEffect(invis2, true);
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 300; i++) {
						final SmokeEffect effect = new SmokeEffect(ClassAbilities.em);
						effect.setLocation(p.getLocation().add(Math.random() * 2 - 1, Math.random() * 2 - 1, Math.random() * 2 - 1));
						effect.start();
					}
				}
				else if (p.hasPermission("essentials.kits.Assassin3") && cooldown.contains(p.getName()) == false) {
					p.sendMessage("§6[§aC§2C§6] §cYou used Sneak§c!");
                    p.addPotionEffect(invis3, true);
					cooldown.add(p.getName());
					Delay.cooldownDone(p, (seconds*20), cooldown);
					previousUse.put(p.getName(), System.currentTimeMillis());
					for (int i = 0; i < 300; i++) {
						final SmokeEffect effect = new SmokeEffect(ClassAbilities.em);
						effect.setLocation(p.getLocation().add(Math.random() * 2 - 1, Math.random() * 2 - 1, Math.random() * 2 - 1));
						effect.start();
					}
				}
				else if (p.hasPermission("essentials.kits.Assassin1") | p.hasPermission("essentials.kits.Assassin2") | p.hasPermission("essentials.kits.Assassin3")) {
					p.sendMessage("§6[§aC§2C§6] §cYou can use your ability in " + (((seconds*1000 - (usedNow - previousUse.get(p.getName()))) / 1000)) + " §cseconds!");
				}
			}
		}
	}
}
