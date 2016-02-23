package net.castlecraftmc.castlecraftcustomitems;

import java.util.List;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftListener implements Listener {
	@EventHandler
	public void CustomCraftEvent(CraftItemEvent e) {
		//Make sure the event is not cancelled
		if (!e.isCancelled()) {
			if (e.getRecipe() instanceof ShapedRecipe) {
				ItemStack item = e.getRecipe().getResult();
				if (CastleCraftCustomItems.isItemInConfig(item)) {
					ItemMeta meta = item.getItemMeta();
					List<String> lore = CastleCraftCustomItems.getLoreFormat(item);
					meta.setLore(lore);
					meta.setDisplayName(CastleCraftCustomItems.getNameVariant(item));
					item.setItemMeta(meta);
					e.setCurrentItem(item);
				}
			}
		}
	}
}
