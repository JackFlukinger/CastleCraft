package net.castlecraftmc.backhandlers;

import java.util.HashMap;

import me.redepicness.socketmessenger.api.ReceivedDataEvent;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BackListener implements Listener {
	
	@EventHandler
	public static void recieveSetBack(ReceivedDataEvent e) {
		if (e.getChannel().equals("setBack")) {
			String uuid = e.getData().getString("uuid");
			Player p = Bukkit.getPlayer(uuid);
			BackFunctions.setBack(uuid, BungeeUtilCompanion.getServerName(), p.getWorld().getName(), p.getLocation().getX(),  p.getLocation().getY(),  p.getLocation().getZ(),  p.getLocation().getYaw(),  p.getLocation().getPitch());
		}
	}
}
