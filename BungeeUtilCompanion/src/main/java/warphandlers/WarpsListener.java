package warphandlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import me.redepicness.socketmessenger.api.ReceivedDataEvent;
import net.castlecraftmc.bungeeutilcompanion.BungeeUtilCompanion;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class WarpsListener implements Listener {
	
	@EventHandler
	public void onReloadWarps(ReceivedDataEvent e) {
		if (e.getChannel().equals("ReloadWarps")) {
			BungeeUtilCompanion.loadWarps();
		}
	}
}
