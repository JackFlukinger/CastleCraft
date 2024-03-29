package net.castlecraftmc.backhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

public class BackFunctions {
	
    public static void createBackTable() {
    	try {
			BungeeUtilCompanion.c.createStatement().execute("CREATE TABLE IF NOT EXISTS `BackCache` (`uuid` VARCHAR(36), `server` VARCHAR(32), `world` VARCHAR(32), `x` DOUBLE PRECISION, `y` DOUBLE PRECISION, `z` DOUBLE PRECISION, `yaw` FLOAT, `pitch` FLOAT, PRIMARY KEY (`uuid`))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public static void setBack(final String uuid, final String server, final String world, final double x, final double y, final double z, final Float yaw, final Float pitch) {
    	try {
    		PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("INSERT INTO `BackCache` (`uuid`, `server`, `world`, `x`, `y`, `z`, `yaw`, `pitch`) VALUE (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `uuid`=VALUES(`uuid`),`server`=VALUES(`server`),`world`=VALUES(`world`),`x`=VALUES(`x`),`y`=VALUES(`y`),`z`=VALUES(`z`),`yaw`=VALUES(`yaw`),`pitch`=VALUES(`pitch`)");
    		statement.setString(1, uuid);
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
    }
    
    public static void sendSetBackMessage(String uuid) {
		Data message = new Data();
		message.addString("uuid", uuid);
		SocketAPI.sendDataToServer("setBack", message);

    }
    
    public static HashMap<String, String> getBack(String uuid) {
    	HashMap<String,String> backLocation = new HashMap<String,String>();
    	PreparedStatement statement = null;
		try {
			statement = BungeeUtilCompanion.c.prepareStatement("SELECT * FROM `BackCache` WHERE `uuid`=?;");
			statement.setString(1, uuid);
			ResultSet backsToParse = statement.executeQuery();
			while (backsToParse.next()) {
				String server = backsToParse.getString(2);
				String world = backsToParse.getString(3);
				double x = backsToParse.getDouble(4);
				double y = backsToParse.getDouble(5);
				double z = backsToParse.getDouble(6);
				Float yaw = backsToParse.getFloat(7);
				Float pitch = backsToParse.getFloat(8);
				backLocation.put("server", server);
				backLocation.put("world", world);
				backLocation.put("x", String.valueOf(x));
				backLocation.put("y", String.valueOf(y));
				backLocation.put("z", String.valueOf(z));
				backLocation.put("yaw", String.valueOf(yaw));
				backLocation.put("pitch", String.valueOf(pitch));
			}
			statement.close();
			return backLocation;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return backLocation;
	    }
    }
}
