package net.castlecraftmc.tphandlers;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.api.ReceivedDataEvent;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TPListener implements Listener {
	@EventHandler
	public void onTeleportDataReceived(ReceivedDataEvent e) {
		if (e.getChannel().equals("TeleportRequest")) {
			String sender = e.getData().getString("sender");
			String recipient = e.getData().getString("recipient");
			Player p = Bukkit.getPlayer(recipient);
	    	p.sendMessage("§c" + sender + " §6has requested to teleport to you. Type §c/tpaccept §6to accept the request.");
	    	
		} else if (e.getChannel().equals("addTPRequest")) {
			String playerToTeleport = e.getData().getString("playerToTeleport");
			String playerToTeleportTo = e.getData().getString("playerToTeleportTo");
			BungeeUtilCompanion.tpRequestsCache.put(playerToTeleportTo, playerToTeleport);
			
		} else if (e.getChannel().equals("removeTPRequest")) {
			Data message = e.getData();
			String playerToTeleportTo = message.getString("key");
			BungeeUtilCompanion.tpRequestsCache.remove(playerToTeleportTo);
		}
	}
}