package net.castlecraftmc.castlecraftcustomitems;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
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
	
	//Fixes items from before plugin installation
	@EventHandler
	public void CustomCheckEvent(InventoryClickEvent e) {
		ItemStack item = e.getCursor();
		if (CastleCraftCustomItems.isItemInConfig(item)) {
			ItemMeta meta;
			if (item.hasItemMeta()) {
				meta = item.getItemMeta();
			} else {
				meta = Bukkit.getItemFactory().getItemMeta(item.getType());
			}
			List<String> lore = CastleCraftCustomItems.getLoreFormat(item);
			meta.setLore(lore);
			meta.setDisplayName(CastleCraftCustomItems.getNameVariant(item));
			item.setItemMeta(meta);
		}
	}
}
