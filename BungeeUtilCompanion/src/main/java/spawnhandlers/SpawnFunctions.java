package spawnhandlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class SpawnFunctions {
    
    public static void createSpawnTable() {
    	try {
			BungeeUtilCompanion.c.createStatement().execute("CREATE TABLE IF NOT EXISTS `Spawns` (`group` VARCHAR(32), `server` VARCHAR(32), `world` VARCHAR(32), `x` INT(10), `y` INT(10), `z` INT(10), PRIMARY KEY (`group`))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static void setSpawn(String group, final String server, final String world, final int x, final int y, final int z) {
		try {
			PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("INSERT INTO `Spawns` (`group`, `server`, `world`, `x`, `y`, `z`) VALUE (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `group`=VALUES(`group`),`server`=VALUES(`server`),`world`=VALUES(`world`),`x`=VALUES(`x`),`y`=VALUES(`y`),`z`=VALUES(`z`)");
			statement.setString(1, group);
			statement.setString(2, server);
			statement.setString(3, world);
			statement.setInt(4, x);
			statement.setInt(5, y);
			statement.setInt(6, z);
			statement.execute();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map<String,String> location = new HashMap<String,String>() {{
		    put("server", server);
		    put("world", world);
		    put("x", String.valueOf(x));
		    put("y", String.valueOf(y));
		    put("z", String.valueOf(z));

		}};
		BungeeUtilCompanion.spawnCache.put(group, location);
		sendReloadSpawnsMessage();
    }
    
    public static Map<String, String> getSpawn(String group) {
    	if (BungeeUtilCompanion.spawnCache.containsKey(group)) {
    		return BungeeUtilCompanion.spawnCache.get(group);
    	} else {
    		return null;
    	}
    }
    
    public static void delSpawn(String group) {
		try {
			PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("DELETE FROM `Spawns` WHERE `group`=?;");
			statement.setString(1, group);
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BungeeUtilCompanion.spawnCache.remove(group);
		sendReloadSpawnsMessage();
    }
    
    public static void sendReloadSpawnsMessage() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("ReloadSpawns"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		p.sendPluginMessage(BungeeUtilCompanion.getPlugin(), "BungeeCord", out.toByteArray());

    }
}