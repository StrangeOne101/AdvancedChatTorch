package me.mcgamer00000.act.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPIIntegrator;

public class StringHelper {
	
	private StringHelper() {}
	
	public static String cc(String s) {
		return StringEscapeUtils.unescapeJava(
				ChatColor.translateAlternateColorCodes('&',
						s.replaceAll("&#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])", "&x&$1&$2&$3&$4&$5&$6")
				));
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
