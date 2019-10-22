package me.mcgamer00000.act.filter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPIIntegrator;
import me.mcgamer00000.act.events.ConnectionHandler;
import me.mcgamer00000.act.utils.ChatMessage;
import me.mcgamer00000.act.utils.ChatObject;
import me.mcgamer00000.act.utils.FormatInfo;
import me.mcgamer00000.act.utils.StringHelper;

/*
 * Formatter for loading the chat format based on a player's group.
 */
public class ChatFormatter {
	
	public void filter(AsyncPlayerChatEvent e, ChatMessage message) {
		Player p = e.getPlayer();
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		if (!pl.uufi.containsKey(p.getUniqueId())) return;
		if(pl.getConfig().getBoolean("chat.autoUpdateGroups", false))
			ConnectionHandler.add(p);
		FormatInfo fi = pl.uufi.get(p.getUniqueId());
		if(pl.getGroups().getBoolean(fi.getName() + ".useChatColor")) {
			ChatObject msg = new ChatObject("%message%");
			msg.setColor(ChatColor.getByChar(pl.getGroups().getString(fi.getName() + ".chatColor").toCharArray()[0]));
			String total = parse(p, pl.getGroups().getString(fi.getName() + ".format"));
			int i = total.indexOf("%message%");
			int i2 = i+"%message%".length();
			String prefix = total.substring(0, i);
			message.get(0).setMessage(prefix);
			message.getChatObjects().add(msg);
			if(i2+1<=total.length())
				message.getChatObjects().add(new ChatObject(total.substring(i2+1)));
		} else {
			message.get(0).setMessage(parse(p, pl.getGroups().getString(fi.getName() + ".format")));
		}
	}
	
	public String parse(Player p, String s) {
		if (s.contains("&")) s = StringHelper.cc(s);
		s = PlaceholderAPIIntegrator.setPlaceholders(p, s);
		return s;
	}
}
