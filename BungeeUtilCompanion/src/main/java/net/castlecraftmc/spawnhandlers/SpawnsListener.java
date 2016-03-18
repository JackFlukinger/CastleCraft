package net.castlecraftmc.spawnhandlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import me.redepicness.socketmessenger.api.ReceivedDataEvent;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class SpawnsListener implements Listener {
	
	@EventHandler
	public void onReloadSpawns(ReceivedDataEvent e) {
		if (e.getChannel().equals("ReloadSpawns")) {
			BungeeUtilCompanion.loadSpawns();
		}
	}
}