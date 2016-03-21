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
			if (args.length == 1) {
				Player p = (Player) sender;
				if (BungeeUtilCompanion.playerList.containsKey(args[0])) {
					String recipient = args[0];
					sendTeleportRequest(p, recipient);
					return true;
				} else {
					p.sendMessage("§c" + args[0] + " §6is not a valid player.");
					return true;
				}
			} else {
				sender.sendMessage("§6Correct usage is §c/tpa <player>§6.");
				return true;
			}
		}
	    return true;
	}
    
    public void sendTeleportRequest(Player sender, String recipientname) {
    	String request = "§c" + sender.getName() + " §6has requested to teleport to you. Type §c/tpaccept §6to accept the request.";
    	if (BungeeUtilCompanion.getServerName().equals(BungeeUtilCompanion.playerList.get(recipientname))) {
    		Player recipient = Bukkit.getPlayer(recipientname);
    		recipient.sendMessage(request);
    	} else {
    		Data message = new Data();
    		message.addString("server", BungeeUtilCompanion.playerList.get(recipientname));
    		message.addString("sender", sender.getName());
    		message.addString("recipient", recipientname);
    		SocketAPI.sendDataToServer("TeleportRequest", message);
    	}
    }
}