package me.mcgamer00000.act.filter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.utils.ChatMessage;
import me.mcgamer00000.act.utils.Extend;
import me.mcgamer00000.act.utils.TextMaker;

public class JsonSender extends Extend {

	public void filter(AsyncPlayerChatEvent e, ChatMessage message) {
		if(e.isCancelled()) return;
		if(!pl.uufi.containsKey(e.getPlayer().getUniqueId())) return;
		TextMaker tm = new TextMaker(message, e.getPlayer());
		for(Player p: e.getRecipients()) {
			p.spigot().sendMessage(tm.getText());
		}
        Bukkit.getConsoleSender().sendMessage("[ACT Chat] " + tm.getText().toPlainText());
        e.setCancelled(true);
	}
	
	
}
