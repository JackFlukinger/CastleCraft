package net.castlecraftmc.bungeeutilcompanion;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class ServerTeleportListener implements PluginMessageListener,Listener {
	public static HashMap<String,String> playersToTeleport = new HashMap<String,String>();

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("TeleportProtocol")) {
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
			try {
				String location = msgin.readUTF(); // Read the data in the same way you wrote it
				String playerToTeleport = msgin.readUTF();
				playersToTeleport.put(playerToTeleport, location);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void teleportOnJoin(PlayerJoinEvent e) {
		if (playersToTeleport.containsKey(e.getPlayer().getName())) {
			Player p = e.getPlayer();
			World world = Bukkit.getWorld(playersToTeleport.get(p.getName()).split(",")[0]);
			int x = Integer.parseInt(playersToTeleport.get(p.getName()).split(",")[1]);
			int y = Integer.parseInt(playersToTeleport.get(p.getName()).split(",")[2]);
			int z = Integer.parseInt(playersToTeleport.get(p.getName()).split(",")[3]);
			p.teleport(new Location(world, x, y, z));
			p.sendMessage("§6Teleporting...");
			playersToTeleport.remove(p.getName());
		}
	}
}