package net.castlecraftmc.tphandlers;

import java.util.Map;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.backhandlers.BackFunctions;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;
import net.castlecraftmc.bungeeutilcompanion.ServerTeleportProtocol;
import net.castlecraftmc.spawnhandlers.SpawnFunctions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPCommand implements CommandExecutor {
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpa") && sender instanceof Player && sender.hasPermission("bungeeutil.tpa")) {
			Bukkit.broadcastMessage(BungeeUtilCompanion.playerList.toString());
			if (args.length == 1) {
				Player p = (Player) sender;
				if (BungeeUtilCompanion.playerList.containsKey(args[0]) && !args[0].equals(sender.getName())) {
					String playerToTeleportTo = args[0];
					sendTeleportRequest(p.getName(), playerToTeleportTo, false);
					TPFunctions.addTPRequest(p.getName(), playerToTeleportTo);
					p.sendMessage("§6Teleport request sent.");
					return true;
				} else {
					p.sendMessage("§c" + args[0] + " §6is not a valid player.");
					return true;
				}
			} else {
				sender.sendMessage("§6Correct usage is §c/tpa <player>§6.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("tpaccept") && sender instanceof Player && sender.hasPermission("bungeeutil.tpaccept")) {
		
			Player player = (Player) sender;
			if (BungeeUtilCompanion.tpRequestsCache.containsKey(player.getName())) {
				String playerToTeleport = BungeeUtilCompanion.tpRequestsCache.get(player.getName());
				String playerToTeleportTo = player.getName();
				String playerToTeleportServer = BungeeUtilCompanion.playerList.get(playerToTeleport);
				String serverToTeleportTo = BungeeUtilCompanion.playerList.get(playerToTeleportTo);
				if (playerToTeleportServer.equals(serverToTeleportTo)) {
					BackFunctions.setBack(Bukkit.getPlayer(playerToTeleport).getName(), BungeeUtilCompanion.getServerName(), Bukkit.getPlayer(playerToTeleport).getWorld().getName(), Bukkit.getPlayer(playerToTeleport).getLocation().getX(), Bukkit.getPlayer(playerToTeleport).getLocation().getY(), Bukkit.getPlayer(playerToTeleport).getLocation().getZ(), Bukkit.getPlayer(playerToTeleport).getLocation().getYaw(), Bukkit.getPlayer(playerToTeleport).getLocation().getPitch());
					Bukkit.getPlayer(playerToTeleport).teleport(Bukkit.getPlayer(playerToTeleportTo));
					TPFunctions.removeTPRequest(playerToTeleportTo);
				} else {
					ServerTeleportProtocol.teleportPlayerToPlayer(playerToTeleport, playerToTeleportTo);
					TPFunctions.removeTPRequest(playerToTeleportTo);
				}
			} else {
				player.sendMessage("§6You don't have any pending teleport requests!");
			}
		} else if (cmd.getName().equalsIgnoreCase("tp") && sender instanceof Player && sender.hasPermission("bungeeutil.tp")) {
			if (args.length == 1) {
				Player p = (Player) sender;
				if (BungeeUtilCompanion.playerList.containsKey(args[0]) && !args[0].equals(sender.getName())) {
					String playerToTeleport = p.getName();
					String playerToTeleportTo = args[0];
					String playerToTeleportServer = BungeeUtilCompanion.getServerName();
					String serverToTeleportTo = BungeeUtilCompanion.playerList.get(playerToTeleportTo);
					if (playerToTeleportServer.equals(serverToTeleportTo)) {
						BackFunctions.setBack(Bukkit.getPlayer(playerToTeleport).getName(), BungeeUtilCompanion.getServerName(), Bukkit.getPlayer(playerToTeleport).getWorld().getName(), Bukkit.getPlayer(playerToTeleport).getLocation().getX(), Bukkit.getPlayer(playerToTeleport).getLocation().getY(), Bukkit.getPlayer(playerToTeleport).getLocation().getZ(), Bukkit.getPlayer(playerToTeleport).getLocation().getYaw(), Bukkit.getPlayer(playerToTeleport).getLocation().getPitch());
						Bukkit.getPlayer(playerToTeleport).teleport(Bukkit.getPlayer(playerToTeleportTo));
						p.sendMessage("§6Teleporting...");
					} else {
						ServerTeleportProtocol.teleportPlayerToPlayerNoCheck(playerToTeleport, playerToTeleportTo);
					}
					return true;
				} else {
					p.sendMessage("§c" + args[0] + " §6is not a valid player.");
					return true;
				}
			} else {
				sender.sendMessage("§6Correct usage is §c/tp <player>§6.");
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("tphere") && sender instanceof Player && sender.hasPermission("bungeeutil.tphere")) {
			if (args.length == 1) {
				Player p = (Player) sender;
				if (BungeeUtilCompanion.playerList.containsKey(args[0])  && !args[0].equals(sender.getName())) {
					String playerToTeleportTo = p.getName();
					String playerToTeleport = args[0];
					String playerToTeleportServer = BungeeUtilCompanion.playerList.get(playerToTeleport);
					String serverToTeleportTo = BungeeUtilCompanion.playerList.get(playerToTeleportTo);
					if (playerToTeleportServer.equals(serverToTeleportTo)) {
						BackFunctions.setBack(Bukkit.getPlayer(playerToTeleport).getName(), BungeeUtilCompanion.getServerName(), Bukkit.getPlayer(playerToTeleport).getWorld().getName(), Bukkit.getPlayer(playerToTeleport).getLocation().getX(), Bukkit.getPlayer(playerToTeleport).getLocation().getY(), Bukkit.getPlayer(playerToTeleport).getLocation().getZ(), Bukkit.getPlayer(playerToTeleport).getLocation().getYaw(), Bukkit.getPlayer(playerToTeleport).getLocation().getPitch());
						Bukkit.getPlayer(playerToTeleport).teleport(Bukkit.getPlayer(playerToTeleportTo));
						p.sendMessage("§6Teleporting...");
					} else {
						ServerTeleportProtocol.teleportPlayerToPlayerNoCheck(playerToTeleport, playerToTeleportTo);
					}
					return true;
				} else {
					p.sendMessage("§c" + args[0] + " §6is not a valid player.");
					return true;
				}
			} else {
				sender.sendMessage("§6Correct usage is §c/tphere <player>§6.");
				return true;
			}
		}
		return true;
	}
    
    public void sendTeleportRequest(String sender, String recipientname, boolean tpaHere) {
    	String request;
    	if (tpaHere) {
    		request = "§c" + sender + " §6has requested you to teleport to them. Type §c/tpaccept §6to accept the request.";
    	} else {
    		request = "§c" + sender + " §6has requested to teleport to you. Type §c/tpaccept §6to accept the request.";
    	}
    	if (BungeeUtilCompanion.getServerName().equals(BungeeUtilCompanion.playerList.get(recipientname))) {
    		Player recipient = Bukkit.getPlayer(recipientname);
    		recipient.sendMessage(request);
    	} else {
    		Data message = new Data();
    		message.addString("server", BungeeUtilCompanion.playerList.get(recipientname));
    		message.addString("sender", sender);
    		message.addString("recipient", recipientname);
    		SocketAPI.sendDataToServer("TeleportRequest", message);
    	}
    }
}