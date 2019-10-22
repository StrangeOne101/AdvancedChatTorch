package me.mcgamer00000.act.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.utils.ChatMessage;
import me.mcgamer00000.act.utils.StringHelper;

/*
 * Formatter for replacing colors inside a message, if they have permissions.
 */
public class Colors {
	
	private static final Collection<String> colors = new HashSet<String>();
	
	public Colors() {
		String[] arry = new String[] {"&0","&1","&2","&3","&4","&5","&6","&7","&8","&9","&a","&b","&c","&d","&e","&f"};
		for(String s: arry)
			colors.add(s);
	}
	
	public void filter(AsyncPlayerChatEvent e, ChatMessage message) {
		Player p = e.getPlayer();
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		ArrayList<String> perms = new ArrayList<>();
		
		if(p.hasPermission(pl.getConfig().getString("colorperm.all"))) { perms.add("&"); } else {
			if(p.hasPermission(pl.getConfig().getString("colorperm.bold"))) { perms.add("&l"); }
			if(p.hasPermission(pl.getConfig().getString("colorperm.strikethr"))) { perms.add("&m"); }
			if(p.hasPermission(pl.getConfig().getString("colorperm.underline"))) { perms.add("&n"); }
			if(p.hasPermission(pl.getConfig().getString("colorperm.italic"))) { perms.add("&o"); } 
			if(p.hasPermission(pl.getConfig().getString("colorperm.magic"))) { perms.add("&k"); }
			if(p.hasPermission(pl.getConfig().getString("colorperm.allColors"))) { perms.addAll(colors); } else {
				if(p.hasPermission(pl.getConfig().getString("colorperm.black"))) { perms.add("&0"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.dark_blue"))) { perms.add("&1"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.dark_green"))) { perms.add("&2"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.aqua"))) { perms.add("&3"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.dark_red"))) { perms.add("&4"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.purple"))) { perms.add("&5"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.gold"))) { perms.add("&6"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.light_gray"))) { perms.add("&7"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.dark_gray"))) { perms.add("&8"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.light_blue"))) { perms.add("&9"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.light_green"))) { perms.add("&a"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.light_blue"))) { perms.add("&b"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.light_red"))) { perms.add("&c"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.magenta"))) { perms.add("&d"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.yellow"))) { perms.add("&e"); }
				if(p.hasPermission(pl.getConfig().getString("colorperm.white"))) { perms.add("&f"); }
			}
		}
		
		if(perms.isEmpty()) return;
		
		String m = message.messageSent;
		for(String s: perms) {
			if(s.equals("&")) {
				m = StringHelper.cc(m);	
				break;
			}
			if(m.contains(s))
				m = m.replace(s, StringHelper.cc(s));
		}
		message.messageSent = m;
		perms.clear();
	}
	
}
