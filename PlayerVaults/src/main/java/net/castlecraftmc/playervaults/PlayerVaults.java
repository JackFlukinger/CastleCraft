package net.castlecraftmc.playervaults;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerVaults extends JavaPlugin {
	private static int vaultSize;
	private static PlayerVaults instance;
	private static Plugin plugin;
	PluginManager pm;

    @Override
    public void onEnable(){
    	plugin = this;
		loadConfig();
		instance = this;
		regCommands();
		pm = Bukkit.getPluginManager();
		regListeners();
		createVaultFolder();
    }
    
    private void createVaultFolder() {
    	new File(plugin.getDataFolder().toString() + "/vaults").mkdirs();
    }
    public static PlayerVaults getInstance(){
        return instance;
    }
    
    public static Plugin getPlugin() {
    	return plugin;
    }
    
    public static int getVaultSize() {
    	return vaultSize;
    }
    
    private void regListeners() {
    	Bukkit.getPluginManager().registerEvents(new PlayerVaultsListener(), plugin);

    }
   
    private void regCommands() {
    	getCommand("playervaults").setExecutor(new PlayerVaultsCommand());
    	//getCommand("convertvaults").setExecutor(new ConvertCommand());
    }
    
    private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		vaultSize = getConfig().getInt("VaultSize");
    }
    
    

	@Override
    public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerVaultsFunctions.saveVault(p);
		}
    	plugin = null;
    }
}