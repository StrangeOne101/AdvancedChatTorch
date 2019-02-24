package me.mcgamer00000.act.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import me.mcgamer00000.act.AdvancedChatTorch;

public class CustomPlaceholder {
	
	String id;
	boolean independantTextComponent;
	List<SubPlaceholder> placeholders = new ArrayList<>();
	
	public CustomPlaceholder(String id) {
		this.id = id;
		FileConfiguration yaml = AdvancedChatTorch.getInstance().getCPConfig();
		if(yaml.contains(id + ".independantTextComponent")) {
			independantTextComponent = yaml.getBoolean(id + ".independantTextComponent");
		} else {
			independantTextComponent = true;
		}
		for(String placeholder: AdvancedChatTorch.getInstance().getCPConfig().getStringList(id + ".list")) {
			ConfigurationSection section = AdvancedChatTorch.getInstance().getCPConfig().getConfigurationSection(id + "." + placeholder);
			SubPlaceholder subPlaceholder = new SubPlaceholder(placeholder, section);
			placeholders.add(subPlaceholder);
		}
	}
	
	public boolean isNotIndependant() {
		return !this.independantTextComponent;
	}

	public String getId() {
		return id;
	}
	
	public List<SubPlaceholder> getPlaceholders() {
		return placeholders;
	}
	
}
