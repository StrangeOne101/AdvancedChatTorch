package me.mcgamer00000.act.filter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.utils.ChatMessage;
import me.mcgamer00000.act.utils.TextMaker;

/*
 * Converts ChatMessage object into an actual message that then get sents out.
 */
public class JsonSender {

	public void filter(AsyncPlayerChatEvent e, ChatMessage message) {
		if(e.isCancelled()) return;
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		if(!pl.uufi.containsKey(e.getPlayer().getUniqueId())) return;
		TextMaker tm = new TextMaker(message, e.getPlayer());
		for(Player p: e.getRecipients()) {
			p.spigot().sendMessage(tm.getText());
		}
        Bukkit.getConsoleSender().sendMessage("[ACT Chat] " + tm.getText().toPlainText());
        e.setCancelled(true);
	}
	
	
}
