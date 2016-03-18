package net.castlecraftmc.bungeeutilcompanion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.castlecraftmc.spawnhandlers.SetSpawnCommands;
import net.castlecraftmc.spawnhandlers.SpawnCommand;
import net.castlecraftmc.spawnhandlers.SpawnFunctions;
import net.castlecraftmc.spawnhandlers.SpawnsListener;
import net.castlecraftmc.sql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import warphandlers.SetWarpCommands;
import warphandlers.WarpCommand;
import warphandlers.WarpFunctions;
import warphandlers.WarpsListener;

public class BungeeUtilCompanion extends JavaPlugin implements Listener {
	private static String serverName = new String();
	private static BungeeUtilCompanion instance;
	public static HashMap<String,String> MysqlInfo = new HashMap<String,String>();
	public static HashMap<String, Map<String,String>> spawnCache = new HashMap<String, Map<String,String>>();
	public static HashMap<String, Map<String,String>> warpCache = new HashMap<String, Map<String,String>>();
	private static Plugin plugin;
	public static Connection c;
	PluginManager pm;
	static MySQL SQL;

    @Override
    public void onEnable(){
    	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    	plugin = this;
		loadConfig();
		instance = this;
		regCommands();
		pm = Bukkit.getPluginManager();
		regListeners();
		SQL = new MySQL(MysqlInfo.get("host"), MysqlInfo.get("port"), MysqlInfo.get("database"), MysqlInfo.get("username"), MysqlInfo.get("password"));
		c = SQL.open();
		SpawnFunctions.createSpawnTable();
		WarpFunctions.createWarpTable();
		loadSpawns();
		loadWarps();
    }
    
   
    public static BungeeUtilCompanion getInstance(){
        return instance;
    }
    
    public static Plugin getPlugin() {
    	return plugin;
    }
    
    public static String getServerName() {
    	return serverName;
    }
    
    private void regListeners() {
    	pm.registerEvents(new ServerTeleportListener(), this);
    	pm.registerEvents(new SpawnsListener(), this);
    	pm.registerEvents(new WarpsListener(), this);

    }
   
    private void regCommands() {
    	getCommand("delspawn").setExecutor(new SetSpawnCommands());
    	getCommand("setspawn").setExecutor(new SetSpawnCommands());
    	getCommand("spawn").setExecutor(new SpawnCommand());
    	getCommand("warp").setExecutor(new WarpCommand());
    	getCommand("setwarp").setExecutor(new SetWarpCommands());
    	getCommand("delwarp").setExecutor(new SetWarpCommands());

    }
    
    private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		serverName = getConfig().getString("ServerName");
		MysqlInfo.put("password", getConfig().getString("MySQL.Password"));
		MysqlInfo.put("username", getConfig().getString("MySQL.Username"));
		MysqlInfo.put("database", getConfig().getString("MySQL.Database"));
		MysqlInfo.put("port", getConfig().getString("MySQL.Port"));
		MysqlInfo.put("host", getConfig().getString("MySQL.Host"));
    }
    
    public static void loadSpawns() {
    	spawnCache.clear();
    	PreparedStatement statement = null;
		try {
			statement = BungeeUtilCompanion.c.prepareStatement("SELECT * FROM `Spawns`;");
			ResultSet spawnsToParse = statement.executeQuery();
			while (spawnsToParse.next()) {
				String group = spawnsToParse.getString(1);
				String server = spawnsToParse.getString(2);
				String world = spawnsToParse.getString(3);
				double x = spawnsToParse.getDouble(4);
				double y = spawnsToParse.getDouble(5);
				double z = spawnsToParse.getDouble(6);
				Float yaw = spawnsToParse.getFloat(7);
				Float pitch = spawnsToParse.getFloat(8);
				HashMap <String,String> cacheLocation = new HashMap<String,String>();
				cacheLocation.put("server", server);
				cacheLocation.put("world", world);
				cacheLocation.put("x", String.valueOf(x));
				cacheLocation.put("y", String.valueOf(y));
				cacheLocation.put("z", String.valueOf(z));
				cacheLocation.put("yaw", String.valueOf(yaw));
				cacheLocation.put("pitch", String.valueOf(pitch));
				spawnCache.put(group, cacheLocation);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
	        if (statement != null) { 
	        	try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
	        }
	    }
    }
    
    public static void loadWarps() {
    	warpCache.clear();
    	PreparedStatement statement = null;
		try {
			statement = BungeeUtilCompanion.c.prepareStatement("SELECT * FROM `Warps`;");
			ResultSet warpsToParse = statement.executeQuery();
			while (warpsToParse.next()) {
				String name = warpsToParse.getString(1);
				String server = warpsToParse.getString(2);
				String world = warpsToParse.getString(3);
				double x = warpsToParse.getDouble(4);
				double y = warpsToParse.getDouble(5);
				double z = warpsToParse.getDouble(6);
				Float yaw = warpsToParse.getFloat(7);
				Float pitch = warpsToParse.getFloat(8);
				HashMap <String,String> cacheLocation = new HashMap<String,String>();
				cacheLocation.put("server", server);
				cacheLocation.put("world", world);
				cacheLocation.put("x", String.valueOf(x));
				cacheLocation.put("y", String.valueOf(y));
				cacheLocation.put("z", String.valueOf(z));
				cacheLocation.put("yaw", String.valueOf(yaw));
				cacheLocation.put("pitch", String.valueOf(pitch));
				warpCache.put(name, cacheLocation);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
	        if (statement != null) { 
	        	try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
	        }
	    }
    }

    @SuppressWarnings("static-access")
	@Override
    public void onDisable() {
    	c = SQL.closeConnection(c);
    	plugin = null;
    }
}