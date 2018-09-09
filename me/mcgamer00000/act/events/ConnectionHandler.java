package me.mcgamer00000.act.events;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.mcgamer00000.act.utils.Extend;
import me.mcgamer00000.act.utils.FormatInfo;

public class ConnectionHandler extends Extend implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		add(e.getPlayer());
	}
		
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (pl.uufi.containsKey(p.getUniqueId())) pl.uufi.remove(p.getUniqueId());
	}
	
	public static void add(Player p) {
		UUID u = p.getUniqueId();
		for (String s : pl.getGroups().getStringList("groups")) {
			int priority = pl.getGroups().getInt(s + ".priority");
			if(p.hasPermission(pl.getGroups().getString(s + ".perm"))) {
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
		if (pl.uufi.containsKey(p.getUniqueId())) pl.uufi.remove(p.getUniqueId());	
	}
	
}
