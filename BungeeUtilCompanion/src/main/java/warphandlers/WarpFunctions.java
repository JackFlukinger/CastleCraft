package warphandlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

public class WarpFunctions {
	
    public static void createWarpTable() {
    	try {
			BungeeUtilCompanion.c.createStatement().execute("CREATE TABLE IF NOT EXISTS `Warps` (`name` VARCHAR(32), `server` VARCHAR(32), `world` VARCHAR(32), `x` DOUBLE PRECISION, `y` DOUBLE PRECISION, `z` DOUBLE PRECISION, `yaw` FLOAT, `pitch` FLOAT, PRIMARY KEY (`name`))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static void setWarp(String name, final String server, final String world, final double x, final double y, final double z, final Float yaw, final Float pitch) {
		try {
			PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("INSERT INTO `Warps` (`name`, `server`, `world`, `x`, `y`, `z`, `yaw`, `pitch`) VALUE (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `name`=VALUES(`name`),`server`=VALUES(`server`),`world`=VALUES(`world`),`x`=VALUES(`x`),`y`=VALUES(`y`),`z`=VALUES(`z`),`yaw`=VALUES(`yaw`),`pitch`=VALUES(`pitch`)");
			statement.setString(1, name);
			statement.setString(2, server);
			statement.setString(3, world);
			statement.setDouble(4, x);
			statement.setDouble(5, y);
			statement.setDouble(6, z);
			statement.setFloat(7, yaw);
			statement.setFloat(8, pitch);
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
		    put("yaw", String.valueOf(yaw));
		    put("pitch", String.valueOf(pitch));

		}};
		BungeeUtilCompanion.warpCache.put(name, location);
		sendReloadWarpsMessage();
    }
    
    public static Map<String, String> getWarp(String name) {
    	if (BungeeUtilCompanion.warpCache.containsKey(name)) {
    		return BungeeUtilCompanion.warpCache.get(name);
    	} else {
    		return null;
    	}
    }
    
    public static void delWarp(String name) {
		try {
			PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("DELETE FROM `Warps` WHERE `name`=?;");
			statement.setString(1, name);
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BungeeUtilCompanion.warpCache.remove(name);
		sendReloadWarpsMessage();
    }
    
    public static void sendReloadWarpsMessage() {
		/*ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF("ALL");
		out.writeUTF("ReloadWarps"); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());
		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		p.sendPluginMessage(BungeeUtilCompanion.getPlugin(), "BungeeCord", out.toByteArray());
		*/
		Data message = new Data();
		SocketAPI.sendDataToServer("ReloadWarps", message);

    }
}
