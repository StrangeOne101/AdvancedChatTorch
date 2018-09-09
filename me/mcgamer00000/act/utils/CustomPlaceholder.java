package me.mcgamer00000.act.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import me.mcgamer00000.act.AdvancedChatTorch;

public class CustomPlaceholder {
	
	String id;
	List<SubPlaceholder> placeholders = new ArrayList<>();
	
	public CustomPlaceholder(String id) {
		this.id = id;
		for(String placeholder: AdvancedChatTorch.getInstance().getCPConfig().getStringList(id + ".list")) {
			ConfigurationSection section = AdvancedChatTorch.getInstance().getCPConfig().getConfigurationSection(id + "." + placeholder);
			SubPlaceholder subPlaceholder = new SubPlaceholder(placeholder, section);
			placeholders.add(subPlaceholder);
		}
	}

	public String getId() {
		return id;
	}
	
	public List<SubPlaceholder> getPlaceholders() {
		return placeholders;
	}
	
}
