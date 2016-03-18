package net.castlecraftmc.bungeeutilcompanion;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class ServerTeleportProtocol {
	
	public static void teleportPlayer(Player p, String server, String location) {
		sendPreTeleportInfo(p, server, location);
		callTeleport(p, server);
	}
	
	public static void sendPreTeleportInfo(Player p, String server, String location) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF(server);
		out.writeUTF("TeleportProtocol"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeUTF(location);
			msgout.writeUTF(p.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		p.sendPluginMessage(BungeeUtilCompanion.getPlugin(), "BungeeCord", out.toByteArray());
	}
	
	public static void callTeleport(Player p, String server) {
		  ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("Connect");
		  out.writeUTF(server);
		  p.sendPluginMessage(BungeeUtilCompanion.getPlugin(), "BungeeCord", out.toByteArray());
	}
}