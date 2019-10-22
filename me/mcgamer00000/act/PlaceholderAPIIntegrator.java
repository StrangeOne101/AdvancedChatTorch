package me.mcgamer00000.act;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPIIntegrator {

	public static String setPlaceholders(Player p, String s) {
		s = s.contains("%player_name%") ? s.replace("%player_name%", p.getName()) : s;
		s = s.contains("%display_name%") ? s.replace("%display_name%", p.getDisplayName()) : s;
		try {
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders((OfflinePlayer) p, s);
			}
		} catch(Exception e) {
			AdvancedChatTorch.getInstance().getLogger().severe("Error when parsing PlaceholderAPI placeholders, is it up to date?");
		}
		return s;
	}
	
	public static String setRelationalPlaceholders(Player p, Player p2, String s) {
		return me.clip.placeholderapi.PlaceholderAPI.setRelationalPlaceholders(p, p2, s);
	}

	public static String setBothPlaceholders(Player p, Player to, String cc) {
		return setRelationalPlaceholders(p, to, setPlaceholders(p, cc));
	}
	
}
