package net.castlecraftmc.tphandlers;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

public class TPFunctions {
	public static void addTPRequest(String playerToTeleport, String playerToTeleportTo) {
		Data message = new Data();
		message.addString("playerToTeleport", playerToTeleport);
		message.addString("playerToTeleportTo", playerToTeleportTo);
		SocketAPI.sendDataToServer("addTPRequest", message);
		BungeeUtilCompanion.tpRequestsCache.put(playerToTeleportTo, playerToTeleport);
	}
	
	public static void removeTPRequest(String playerToTeleportTo) {
		Data message = new Data();
		message.addString("key", playerToTeleportTo);
		SocketAPI.sendDataToServer("removeTPRequest", message);
		BungeeUtilCompanion.tpRequestsCache.remove(playerToTeleportTo);
	}
}