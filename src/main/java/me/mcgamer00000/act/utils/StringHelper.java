package me.mcgamer00000.act.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPIIntegrator;

public class StringHelper {
	
	private StringHelper() {}
	
	public static String cc(String s) {
		return ChatColor.translateAlternateColorCodes('&', s).replaceAll("\\\n", "\n");
	}
	
	public static String setPlaceholders(Player p, String s) {
		return PlaceholderAPIIntegrator.setPlaceholders(p, s);
	}

	public static String getCmdStr(String string) {
		return AdvancedChatTorch.getInstance().getCmds().getString(string);
	}

	public static String ccGetCmdStr(String string) {
		return StringHelper.cc(getCmdStr(string));
	}
	
}
