package me.mcgamer00000.act.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.filter.ChatFormatter;
import me.mcgamer00000.act.filter.Colors;
import me.mcgamer00000.act.filter.CustomPlaceholdersFilter;
import me.mcgamer00000.act.filter.IgnoreFilter;
import me.mcgamer00000.act.filter.JsonSender;
import me.mcgamer00000.act.utils.ChatMessage;
import me.mcgamer00000.act.utils.Extend;

public class MainChatHandler extends Extend implements Listener {
	
	final ChatFormatter cf = new ChatFormatter();
	final JsonSender js = new JsonSender();
	final CustomPlaceholdersFilter cp = new CustomPlaceholdersFilter();
	final Colors c = new Colors();
	final IgnoreFilter ignore = new IgnoreFilter();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void playerChat(AsyncPlayerChatEvent e) {
		boolean stop = ignore.filter(e);
		if(stop) return;
		pl.muted.filter(e);
		if(e.isCancelled()) return;
		pl.slowed.filter(e);
		if(e.isCancelled()) return;
		ChatMessage message = new ChatMessage(e.getMessage(), e.getPlayer());
		cf.filter(e, message);
		c.filter(e, message);
		cp.filter(e, message);
		js.filter(e, message);
	}
	
}
