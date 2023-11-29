package me.mcgamer00000.act.events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.utils.FormatInfo;

/*
 * Listener for joining and leaving to load a player's group.
 * Handler for loading and removing a player's group.
 */
public class ConnectionHandler implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		add(e.getPlayer());
	}
		
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		remove(e.getPlayer());
	}
	
	public static void add(Player p) {
		UUID u = p.getUniqueId();
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		for (String s : pl.getGroups().getKeys(false)) {
			if(s.equals("groups")) continue;
			int priority = pl.getGroups().getInt(s + ".priority");
			if(pl.getGroups().getString(s + ".perm", "").equals("") || p.hasPermission(pl.getGroups().getString(s + ".perm"))) {
				if(pl.uufi.containsKey(u)) {
					if(pl.uufi.get(u).getPriority() < priority) {
						pl.uufi.put(u, new FormatInfo(priority, s));
					}
				} else {
					pl.uufi.put(u, new FormatInfo(priority, s));
				}
			}
		}
	}
	
	public static void remove(Player p) {
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		if (pl.uufi.containsKey(p.getUniqueId())) pl.uufi.remove(p.getUniqueId());	
	}
	
}
