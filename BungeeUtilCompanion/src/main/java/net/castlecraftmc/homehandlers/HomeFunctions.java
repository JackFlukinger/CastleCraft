package net.castlecraftmc.homehandlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.redepicness.socketmessenger.api.Data;
import me.redepicness.socketmessenger.bukkit.SocketAPI;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

public class HomeFunctions {

	public static void createHomeTable() {
		try {
			BungeeUtilCompanion.c.createStatement().execute("CREATE TABLE IF NOT EXISTS `Homes` (`uuid` VARCHAR(50), `home` VARCHAR(32), `server` VARCHAR(32), `world` VARCHAR(32), `x` DOUBLE PRECISION, `y` DOUBLE PRECISION, `z` DOUBLE PRECISION, `yaw` FLOAT, `pitch` FLOAT);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void setHome(final String uuid, final String home, final String server, final String world, final double x, final double y, final double z, final Float yaw, final Float pitch) {
    	Bukkit.getScheduler().runTaskAsynchronously(BungeeUtilCompanion.getPlugin(), new Runnable() {
    		public void run() {
    			try {
    				PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("INSERT INTO `Homes` (`uuid`, `home`, `server`, `world`, `x`, `y`, `z`, `yaw`, `pitch`) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?);");
    				statement.setString(1, uuid);
    				statement.setString(2, home);
    				statement.setString(3, server);
    				statement.setString(4, world);
    				statement.setDouble(5, x);
    				statement.setDouble(6, y);
    				statement.setDouble(7, z);
    				statement.setFloat(8, yaw);
    				statement.setFloat(9, pitch);
    				statement.execute();
    				statement.close();

    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	});
	}
	
	public static boolean hasHome(String uuid, String home) {
		PreparedStatement statement = null;
		try {
			statement = BungeeUtilCompanion.c.prepareStatement("SELECT * FROM `Homes` WHERE `uuid`=? AND `home`=?;");
			statement.setString(1, uuid);
			statement.setString(2, home);
			ResultSet result = statement.executeQuery();
			if (result.absolute(1)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
	}

	public static HashMap<String,String> getHome(String uuid, String home) {
		try {
			PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("SELECT * FROM `Homes` WHERE `uuid`=? AND `home`=?;");
			statement.setString(1, uuid);
			statement.setString(2, home);
			ResultSet result = statement.executeQuery();
			HashMap<String,String> location = new HashMap<String,String>();
			while (result.next()) {
				String server = result.getString(3);
				String world = result.getString(4);
				double x = result.getDouble(5);
				double y = result.getDouble(6);
				double z = result.getDouble(7);
				Float yaw = result.getFloat(8);
				Float pitch = result.getFloat(9);
				location.put("server", server);
				location.put("world", world);
				location.put("x", String.valueOf(x));
				location.put("y", String.valueOf(y));
				location.put("z", String.valueOf(z));
				location.put("yaw", String.valueOf(yaw));
				location.put("pitch", String.valueOf(pitch));
			}
			statement.close();
			return location;
		} catch (SQLException e) {
			return null;
		}
	}

	public static void delHome(String uuid, String homeName) {
		try {
			PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("DELETE FROM `Homes` WHERE `uuid`=? AND `home`=?;");
			statement.setString(1, uuid);
			statement.setString(2, homeName);
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getHomes(String uuid) {
		try {
			PreparedStatement statement = BungeeUtilCompanion.c.prepareStatement("SELECT * FROM `Homes` WHERE `uuid`=?;");
			statement.setString(1, uuid);
			ResultSet result = statement.executeQuery();
			List<String> homes = new ArrayList<String>();
			while (result.next()) {
				String home = result.getString(2);
				homes.add(home);
			}
			statement.close();
			return homes;
		} catch (SQLException e) {
			return null;
		}
	}
}
