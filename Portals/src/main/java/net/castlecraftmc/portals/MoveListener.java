package net.castlecraftmc.portals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class MoveListener implements Listener {

	static BukkitRunnable checkIfPlayerIsInPortal = new BukkitRunnable() {
		public void run() {
			List<World> enabledWorlds = Portals.getEnabledWorlds();
			List<Player> players = new ArrayList<Player>();
			List<ProtectedRegion> regionsToCheck = new ArrayList<ProtectedRegion>();
			HashMap<String, String> commandArray = Portals.getCommandArray();
			for (World world : enabledWorlds) {
				for (Player p : world.getPlayers()) {
					players.add(p);
				}
			}
			// Get regions from config file to loop through
			for (ProtectedRegion region : Portals.getPortalRegions()) {
				regionsToCheck.add(region);
			}
			for (Player p : players) {
				for (ProtectedRegion r : regionsToCheck) {
					// Make player perform command if they enter a portal
					if (Portals.playerIsInRegion(p, r)) {
						String command = commandArray.get(r.getId());
						Bukkit.getServer().dispatchCommand(p, command);
					}
				}
			}
			Portals.getLog().finest(String.format("[Portals] Checking players: " + players));
		}
	};
}
