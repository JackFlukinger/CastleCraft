package net.castlecraftmc.bungeeutilcompanion;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.redepicness.socketmessenger.api.ReceivedDataEvent;
import net.castlecraftmc.backhandlers.BackFunctions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class ServerTeleportListener implements Listener {
	
	public static HashMap<String,String> playersToTeleport = new HashMap<String,String>();
	public static HashMap<String,String> p2pCache = new HashMap<String,String>();
	public static HashMap<String,String> p2pNCCache = new HashMap<String,String>();

	
	@EventHandler
	public void onTeleportDataReceived(ReceivedDataEvent e) {
		if (e.getChannel().equals("TeleportProtocol")) {
			String playerToTeleport = e.getData().getString("player");
			String location = e.getData().getString("location");
			playersToTeleport.put(playerToTeleport, location);
			
		} else if (e.getChannel().equals("P2P")) {
			String playerToTeleport = e.getData().getString("playerToTeleport");
			String playerToTeleportTo = e.getData().getString("playerToTeleportTo");
			p2pCache.put(playerToTeleport, playerToTeleportTo);
			//For players doing /tp and /tphere, specifically
		} else if (e.getChannel().equals("P2PNC")) {
			String playerToTeleport = e.getData().getString("playerToTeleport");
			String playerToTeleportTo = e.getData().getString("playerToTeleportTo");
			p2pNCCache.put(playerToTeleport, playerToTeleportTo);
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
			Location locToTeleportTo = new Location(world, x, y, z, yaw, pitch);
			if (isLocationSafe(locToTeleportTo)) {
				p.teleport(locToTeleportTo);
				p.sendMessage("§6Teleporting...");
			} else {
				p.sendMessage("§6Teleport cancelled due to unsafe location.");
				HashMap<String,String> locationToTPTo = BackFunctions.getBack(p.getName());
				String server = locationToTPTo.get("server");
				String worldString = locationToTPTo.get("world");
				String xString = locationToTPTo.get("x");
				String yString = locationToTPTo.get("y");
				String zString = locationToTPTo.get("z");
				String yawString = locationToTPTo.get("yaw");
				String pitchString = locationToTPTo.get("pitch");
				String location = worldString + "," + xString + "," + yString + "," + zString + "," + yawString + "," + pitchString;
				ServerTeleportProtocol.teleportPlayer(p, server, location);
			}
			playersToTeleport.remove(p.getName());
		} else if (p2pCache.containsKey(e.getPlayer().getName())) {
			Player p = e.getPlayer();
			Player playerToTeleportTo = Bukkit.getPlayer(p2pCache.get(p.getName()));
			if (isLocationSafe(playerToTeleportTo.getLocation())) {
				p.teleport(Bukkit.getPlayer(p2pCache.get(p.getName())));
				p.sendMessage("§6Teleporting...");
			} else {
				p.sendMessage("§c" + playerToTeleportTo.getName() + " §6is in an unsafe location.");
				HashMap<String,String> locationToTPTo = BackFunctions.getBack(p.getName());
				String server = locationToTPTo.get("server");
				String world = locationToTPTo.get("world");
				String x = locationToTPTo.get("x");
				String y = locationToTPTo.get("y");
				String z = locationToTPTo.get("z");
				String yaw = locationToTPTo.get("yaw");
				String pitch = locationToTPTo.get("pitch");
				String location = world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
				ServerTeleportProtocol.teleportPlayer(p, server, location);
			}
			p2pCache.remove(p.getName());

		} else if (p2pNCCache.containsKey(e.getPlayer().getName())) {
			Player p = e.getPlayer();
			Player playerToTeleportTo = Bukkit.getPlayer(p2pNCCache.get(p.getName()));
			p.teleport(playerToTeleportTo);
			p.sendMessage("§6Teleporting...");
			p2pNCCache.remove(p.getName());

		}
	}
	
	public static boolean isLocationSafe(Location location) {
		List<Material> unsafeBlocks = new ArrayList<Material>();
		unsafeBlocks.add(Material.LAVA);
		unsafeBlocks.add(Material.STATIONARY_LAVA);
		location.add(new Vector(0, -1, 0));
		if (location.getBlock().getType() == Material.AIR) {
			return false;
		}
		List<Location> locationsToCheck = new ArrayList<Location>();
		for (int i = 0; i <= 2; i++) {
			Location iterator = location;
			iterator.add(0, i, 0);
			locationsToCheck.add(iterator);
		}
		for (Location l : locationsToCheck) {
			Block test = l.getBlock();
			if (unsafeBlocks.contains(test.getType())) {
				return false;
			}
		}
		location.add(new Vector(0, -2, 0));
		return true;
	}
}