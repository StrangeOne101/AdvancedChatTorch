package me.mcgamer00000.act.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPI;

public class Extend {

	public static AdvancedChatTorch pl;
	
	public Extend() {
		pl = AdvancedChatTorch.getInstance();
	}
	
	public static String cc(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String placeHolder(Player p, String s) {
		return PlaceholderAPI.setPlaceholders(p, s);
	}

	public static String getCStr(String string) {
		return pl.getCmds().getString(string);
	}
	
}
