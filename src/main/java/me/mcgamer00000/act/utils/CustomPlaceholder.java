package me.mcgamer00000.act.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import me.mcgamer00000.act.AdvancedChatTorch;

public class CustomPlaceholder {
	
	String id;
	boolean independentTextComponent;
	List<SubPlaceholder> placeholders = new ArrayList<>();
	
	public CustomPlaceholder(String id) {
		this.id = id;
		FileConfiguration yaml = AdvancedChatTorch.getInstance().getCPConfig();
		if(yaml.contains(id + ".independentTextComponent")) {
			independentTextComponent = yaml.getBoolean(id + ".independentTextComponent");
		} else {
			independentTextComponent = true;
		}
		ConfigurationSection cpSection = AdvancedChatTorch.getInstance().getCPConfig().getConfigurationSection(id);
		for(String placeholder: cpSection.getKeys(false)) {
			if(placeholder.equals("independentTextComponent")) continue;
			if(placeholder.equals("list")) continue;
			ConfigurationSection section = cpSection.getConfigurationSection(placeholder);
			SubPlaceholder subPlaceholder = new SubPlaceholder(placeholder, section);
			placeholders.add(subPlaceholder);
		}
	}
	
	public boolean isNotIndependent() {
		return !this.independentTextComponent;
	}

	public String getId() {
		return id;
	}
	
	public List<SubPlaceholder> getPlaceholders() {
		return placeholders;
	}
	
}
