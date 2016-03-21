package net.castlecraftmc.bungeeutilcompanion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.api.ReceivedDataEvent;
import me.redepicness.socketmessenger.bukkit.SocketAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class GetPlayerList implements Listener {
	
	public static void receivePlayerList(ReceivedDataEvent e) {
		if (e.getChannel().equals("SendPlayerList")) {
			HashMap<String,String> playerList = new HashMap<String,String>();
			for (String nameServerString : e.getData().getString("list").split(";")) {
				playerList.put(nameServerString.split(",")[0], nameServerString.split(",")[1]);
			}
			BungeeUtilCompanion.playerList = playerList;
		}
	}
}
