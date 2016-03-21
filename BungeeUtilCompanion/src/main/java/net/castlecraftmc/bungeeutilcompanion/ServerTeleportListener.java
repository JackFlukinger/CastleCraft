package net.castlecraftmc.bungeeutilcompanion;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

import me.redepicness.socketmessenger.api.ReceivedDataEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class ServerTeleportListener implements Listener {
	public static HashMap<String,String> playersToTeleport = new HashMap<String,String>();
	
	@EventHandler
	public void onTeleportDataReceived(ReceivedDataEvent e) {
		if (e.getChannel().equals("TeleportProtocol")) {
			String playerToTeleport = e.getData().getString("player");
			String location = e.getData().getString("location");
			playersToTeleport.put(playerToTeleport, location);
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void teleportOnJoin(PlayerJoinEvent e) {
		if (playersToTeleport.containsKey(e.getPlayer().getName())) {
			final Player p = e.getPlayer();
			final World world = Bukkit.getWorld(playersToTeleport.get(p.getName()).split(",")[0]);
			final double x = Double.parseDouble(playersToTeleport.get(p.getName()).split(",")[1]);
			final double y = Double.parseDouble(playersToTeleport.get(p.getName()).split(",")[2]);
			final double z = Double.parseDouble(playersToTeleport.get(p.getName()).split(",")[3]);
			final Float yaw = Float.parseFloat(playersToTeleport.get(p.getName()).split(",")[4]);
			final Float pitch = Float.parseFloat(playersToTeleport.get(p.getName()).split(",")[5]);
			Location locToTeleportTo = new Location(world, x, y, z);
			locToTeleportTo.setYaw(yaw);
			locToTeleportTo.setPitch(pitch);
			p.teleport(locToTeleportTo);
			p.sendMessage("§6Teleporting...");
			playersToTeleport.remove(p.getName());
		}
	}
}