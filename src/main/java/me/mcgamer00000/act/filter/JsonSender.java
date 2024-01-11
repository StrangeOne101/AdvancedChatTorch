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
		boolean relationalPlaceholders = AdvancedChatTorch.getInstance().getConfig().getBoolean("chat.enableRelationalPlaceholders");
		if(!relationalPlaceholders)
			tm.convertMessageToComponents();
		for(Player p: e.getRecipients()) {
			if(relationalPlaceholders)
				p.spigot().sendMessage(tm.getRelationalText(p));
			else
				p.spigot().sendMessage(tm.getText());
		}
        if(pl.getConfig().getBoolean("chat.useOldWay")) {
    		if(relationalPlaceholders)
    			Bukkit.getConsoleSender().sendMessage("[ACT Chat] " + tm.getRelationalText(e.getPlayer()).toPlainText());
    		else
    			Bukkit.getConsoleSender().sendMessage("[ACT Chat] " + tm.getText().toPlainText());
        	e.setCancelled(true);
        } else {
    		/*if(relationalPlaceholders)
        		e.setMessage(tm.getRelationalText(e.getPlayer()).toPlainText());
        	else
        		e.setMessage(tm.getText().toPlainText());*/
        	//e.setFormat(message.get(0) + "%2$s");
			e.setFormat(tm.getFormatCombined());

			e.getRecipients().clear();
        }
	}
	
	
}
