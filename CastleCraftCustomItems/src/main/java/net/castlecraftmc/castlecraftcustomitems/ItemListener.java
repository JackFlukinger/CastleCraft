package net.castlecraftmc.castlecraftcustomitems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {
	//For swords/bows
	@EventHandler(ignoreCancelled=true)
	public void hitEvent(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			e.setDamage(e.getDamage() + Util.getDamage(e.getDamager()));
			if (!mobSpawnUtil.getSpawnerMobs().contains(e.getEntity().getUniqueId())) {
				if (((LivingEntity) e.getEntity()).getHealth() - (e.getDamage()) > 0.0) {
					Util.onHit((LivingEntity) e.getEntity(), e.getDamager(), 1);
				} else {
					Util.onHit((LivingEntity) e.getEntity(), e.getDamager(), CastleCraftCustomItems.getEXPPerMobKill((LivingEntity) e.getEntity()));
				}
				Bukkit.broadcastMessage("Damage: " + e.getDamage());
			}
		}
	}
	
	//For tools
	@EventHandler(ignoreCancelled=true)
	public void blockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if (CastleCraftCustomItems.getAffectedItems().contains(item.getType().toString())) {
			if (CastleCraftCustomItems.getRareDropBlocks().get(item.getType().toString()).contains(e.getBlock().getType().toString()) | CastleCraftCustomItems.getRareDropBlocks().get(item.getType().toString()).contains(e.getBlock().getType().toString() + "-" + String.valueOf(e.getBlock().getData()))) {
				if (CastleCraftCustomItems.getModifierType(item).equals("luck")) {
					int Luck = (int) ((CastleCraftCustomItems.getLevel(item) - 1) * CastleCraftCustomItems.getMultiplier(item));
					if (Math.random() * 200 <= Luck) {
						Bukkit.broadcastMessage("1");
						ItemStack ItemToDrop = new ItemStack(Util.getDrop(item));
						e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), ItemToDrop);
					}
					Util.addEXP(item, 10, p);
				}
			}
		}
	}
	
	//For armor
}
