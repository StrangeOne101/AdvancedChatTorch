package me.mcgamer00000.act.filter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;

public class IgnoreFilter {

	public boolean filter(AsyncPlayerChatEvent e) {
		FileConfiguration config =  AdvancedChatTorch.getInstance().getConfig();
		
		if(!config.contains("ignore.characterList")) return false;
		for(Object characterObj: config.getList("ignore.characterList")) {
			if(!(characterObj instanceof String))
				continue;
			String character = (String) characterObj;
			if(!e.getMessage().startsWith(character))
				continue;
			if(!config.contains("ignore." + character + ".perm"))
				continue;
			if(e.getPlayer().hasPermission(config.getString("ignore." + character +".perm")))
				return true;
		}
		return false;
	}

}
