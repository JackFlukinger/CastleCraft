package net.castlecraftmc.delayedcommands;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

public class DelayedCommandsFunctions {
	public static void addDelayedCommand(String server, String player, String command) {
		Data message = new Data();
		message.addString("player", player);
		message.addString("command", command);
		message.addString("server", server);
		SocketAPI.sendDataToServer("addDelayedCommand", message);
	}
}
