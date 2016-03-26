package net.castlecraftmc.delayedcommands;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.api.ReceivedDataEvent;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DelayedCommandsListener implements Listener {
	@EventHandler
	public void onAddDelayedCommand(ReceivedDataEvent e) {
		if (e.getChannel().equals("addDelayedCommand")) {
			Data message = e.getData();			
			String player = message.getString("player");
			String command = message.getString("command");
			BungeeUtilCompanion.delayCache.put(player, command);
		}
	}
	
	@EventHandler
	public void onJoinRunCommand(PlayerJoinEvent e) {
		if (BungeeUtilCompanion.delayCache.containsKey(e.getPlayer().getName())) {
			String command = BungeeUtilCompanion.delayCache.get(e.getPlayer().getName());
			e.getPlayer().chat("/" + command);
			BungeeUtilCompanion.delayCache.remove(e.getPlayer().getName());
		}
	}
}
