package net.castlecraftmc.bungeeutilcompanion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.castlecraftmc.sql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import spawnhandlers.SetSpawnCommands;
import spawnhandlers.SpawnCommand;
import spawnhandlers.SpawnFunctions;
import spawnhandlers.SpawnsListener;

public class BungeeUtilCompanion extends JavaPlugin implements Listener {
	private static String serverName = new String();
	private static BungeeUtilCompanion instance;
	public static HashMap<String,String> MysqlInfo = new HashMap<String,String>();
	public static HashMap<String, Map<String,String>> spawnCache = new HashMap<String, Map<String,String>>();
	private static Plugin plugin;
	public static Connection c;
	PluginManager pm;
	static MySQL SQL;

    @Override
    public void onEnable(){
    	Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    	Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new ServerTeleportListener());
    	Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new SpawnsListener());
    	plugin = this;
		loadConfig();
		instance = this;
		regCommands();
		pm = Bukkit.getPluginManager();
		regListeners();
		SQL = new MySQL(MysqlInfo.get("host"), MysqlInfo.get("port"), MysqlInfo.get("database"), MysqlInfo.get("username"), MysqlInfo.get("password"));
		c = SQL.open();
		SpawnFunctions.createSpawnTable();
		loadSpawns();
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
    }
   
    private void regCommands() {
    	getCommand("delspawn").setExecutor(new SetSpawnCommands());
    	getCommand("setspawn").setExecutor(new SetSpawnCommands());
    	getCommand("spawn").setExecutor(new SpawnCommand());
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
				int x = spawnsToParse.getInt(4);
				int y = spawnsToParse.getInt(5);
				int z = spawnsToParse.getInt(6);
				HashMap <String,String> cacheLocation = new HashMap<String,String>();
				cacheLocation.put("server", server);
				cacheLocation.put("world", world);
				cacheLocation.put("x", String.valueOf(x));
				cacheLocation.put("y", String.valueOf(y));
				cacheLocation.put("z", String.valueOf(z));
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

    @SuppressWarnings("static-access")
	@Override
    public void onDisable() {
    	c = SQL.closeConnection(c);
    	plugin = null;
    }
}