package me.mcgamer00000.act;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderAPIIntegrator {

	public static String setPlaceholders(Player p, String s) {
		try {
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders((OfflinePlayer) p, s);
			}
		} catch(Exception e) {
			AdvancedChatTorch.getInstance().getLogger().severe("Error when parsing PlaceholderAPI placeholders, is it up to date?");
		}
		s = s.contains("%player_name%") ? s.replace("%player_name%", p.getName()) : s;
		s = s.contains("%display_name%") ? s.replace("%display_name%", p.getDisplayName()) : s;
		return s;
	}
	
}
