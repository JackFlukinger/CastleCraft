package net.castlecraftmc.tphandlers;

import me.redepicness.socketmessenger.api.ReceivedDataEvent;

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
		}
	}
}
