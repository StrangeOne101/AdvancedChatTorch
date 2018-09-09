package me.mcgamer00000.act.filter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPIIntegrator;
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
		FormatInfo fi = pl.uufi.get(p.getUniqueId());
		ChatObject msg = new ChatObject(e.getMessage());
		if(pl.getGroups().getBoolean(fi.getName() + ".useChatColor"))
			msg.setColor(ChatColor.getByChar(pl.getGroups().getString(fi.getName() + ".chatColor").toCharArray()[0]));
		String total = parse(p, pl.getGroups().getString(fi.getName() + ".format"));
		int i = total.indexOf("%message%");
		int i2 = i+"%message%".length();
		String prefix = total.substring(0, i);
		message.get(0).setMessage(prefix);
		message.getChatObjects().add(msg);
		if(i2+1<=total.length())
			message.getChatObjects().add(new ChatObject(total.substring(i2+1)));
	}
	
	public String parse(Player p, String s) {
		if (s.contains("&")) s = StringHelper.cc(s);
		s = s.contains("%player_name%") ? s.replace("%player_name%", p.getName()) : s;
		s = s.contains("%display_name%") ? s.replace("%display_name%", p.getDisplayName()) : s;
		s = PlaceholderAPIIntegrator.setPlaceholders(p, s);
		return s;
	}
}
