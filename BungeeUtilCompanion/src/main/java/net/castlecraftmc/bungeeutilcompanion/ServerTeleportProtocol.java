package net.castlecraftmc.bungeeutilcompanion;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.backhandlers.BackFunctions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class ServerTeleportProtocol {
	
	public static void teleportPlayer(Player p, String server, String location) {
		BackFunctions.setBack(p.getName(), BungeeUtilCompanion.getServerName(), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
		sendPreTeleportInfo(p, server, location);
		callTeleport(p, server);
	}
	
	public static void sendPreTeleportInfo(Player p, String server, String location) {
		Data message = new Data();
		message.addString("server", server);
		message.addString("player", p.getName());
		message.addString("location", location);
		SocketAPI.sendDataToServer("TeleportProtocol", message);
	}
	
	public static void callTeleport(Player p, String server) {
		  ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("Connect");
		  out.writeUTF(server);
		  p.sendPluginMessage(BungeeUtilCompanion.getPlugin(), "BungeeCord", out.toByteArray());
	}
}