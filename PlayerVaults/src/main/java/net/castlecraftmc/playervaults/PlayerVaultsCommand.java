package net.castlecraftmc.playervaults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerVaultsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("playervaults") && sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 1 && isInteger(args[0], 10)) {
				if (PlayerVaultsFunctions.hasVault(p, Integer.parseInt(args[0]))) {
					int vaultNum = Integer.parseInt(args[0]);
					PlayerVaultsFunctions.openVault(p, vaultNum);
				} else {
					p.sendMessage("§6You don't have that vault!");
				}
			} else {
				p.sendMessage("§6Correct usage is §c/pv <vault number>§6.");
			}
		}
		return true;
	}

	public static boolean isInteger(String s, int radix) {
		Scanner sc = new Scanner(s.trim());
		if(!sc.hasNextInt(radix)) return false;
		// we know it starts with a valid int, now make sure
		// there's nothing left!
		sc.nextInt(radix);
		return !sc.hasNext();
	}
}
