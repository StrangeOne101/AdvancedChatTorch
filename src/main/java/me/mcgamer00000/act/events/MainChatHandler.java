package me.mcgamer00000.act.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.filter.ChatFormatter;
import me.mcgamer00000.act.filter.Colors;
import me.mcgamer00000.act.filter.CustomPlaceholdersFilter;
import me.mcgamer00000.act.filter.IgnoreFilter;
import me.mcgamer00000.act.filter.JsonSender;
import me.mcgamer00000.act.utils.ChatMessage;

/*
 * Main Chat listener for all parsing and formatting.
 */
public class MainChatHandler implements Listener {
	
	final ChatFormatter cf = new ChatFormatter();
	final JsonSender js = new JsonSender();
	final CustomPlaceholdersFilter cp = new CustomPlaceholdersFilter();
	final Colors c = new Colors();
	final IgnoreFilter ignore = new IgnoreFilter();
	
	@EventHandler(priority=EventPriority.HIGH)
	public void playerChat(AsyncPlayerChatEvent e) {
		// The method "filter" isn't very accurate of what it does. Most just edits the data, and only some actually cancel the message
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();

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
