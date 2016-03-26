package net.castlecraftmc.bungeeutilcompanion;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.backhandlers.BackFunctions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class ServerTeleportProtocol {
	
	public static void teleportPlayer(Player p, String server, String location) {
		BackFunctions.setBack(p.getName(), BungeeUtilCompanion.getServerName(), p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
		sendPreTeleportInfo(p, server, location);
		callTeleport(p.getName(), server);
	}
	
	public static void teleportPlayerToPlayer(String playerToTeleport, String playerToTeleportTo) {
		BackFunctions.sendSetBackMessage(playerToTeleport);
		sendPlayerToPlayerTeleportInfo(playerToTeleport, BungeeUtilCompanion.playerList.get(playerToTeleportTo), playerToTeleportTo);
		callTeleport(playerToTeleport, BungeeUtilCompanion.playerList.get(playerToTeleportTo));
	}
	
	public static void teleportPlayerToPlayerNoCheck(String playerToTeleport, String playerToTeleportTo) {
		BackFunctions.sendSetBackMessage(playerToTeleport);
		sendPlayerToPlayerTeleportInfoNoCheck(playerToTeleport, BungeeUtilCompanion.playerList.get(playerToTeleportTo), playerToTeleportTo);
		callTeleport(playerToTeleport, BungeeUtilCompanion.playerList.get(playerToTeleportTo));
	}
	
	public static void sendPreTeleportInfo(Player p, String server, String location) {
		Data message = new Data();
		message.addString("server", server);
		message.addString("player", p.getName());
		message.addString("location", location);
		SocketAPI.sendDataToServer("TeleportProtocol", message);
	}
	
	public static void sendPlayerToPlayerTeleportInfo(String playerToTeleport, String server, String playerToTeleportTo) {
		Data message = new Data();
		message.addString("playerToTeleport", playerToTeleport);
		message.addString("playerToTeleportTo", playerToTeleportTo);
		message.addString("server", server);
		SocketAPI.sendDataToServer("P2P", message);
	}
	
	public static void sendPlayerToPlayerTeleportInfoNoCheck(String playerToTeleport, String server, String playerToTeleportTo) {
		Data message = new Data();
		message.addString("playerToTeleport", playerToTeleport);
		message.addString("playerToTeleportTo", playerToTeleportTo);
		message.addString("server", server);
		SocketAPI.sendDataToServer("P2PNC", message);
	}
	
	public static void callTeleport(String player, String server) {
		  ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("ConnectOther");
		  out.writeUTF(player);
		  out.writeUTF(server);
		  Player playerSender = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		  playerSender.sendPluginMessage(BungeeUtilCompanion.getPlugin(), "BungeeCord", out.toByteArray());
	}
}