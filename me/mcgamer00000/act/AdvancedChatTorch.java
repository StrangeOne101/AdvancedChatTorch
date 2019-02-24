package me.mcgamer00000.act;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.mcgamer00000.act.cmds.BukkitCommand;
import me.mcgamer00000.act.cmds.ClearChatCMD;
import me.mcgamer00000.act.cmds.MuteChatCMD;
import me.mcgamer00000.act.cmds.ReloadCMD;
import me.mcgamer00000.act.cmds.SlowChatCMD;
import me.mcgamer00000.act.events.CmdHandler;
import me.mcgamer00000.act.events.ConnectionHandler;
import me.mcgamer00000.act.events.MainChatHandler;
import me.mcgamer00000.act.utils.CustomPlaceholder;
import me.mcgamer00000.act.utils.FormatInfo;

public class AdvancedChatTorch extends JavaPlugin {

	private static AdvancedChatTorch instance;
	public HashMap<UUID, FormatInfo> uufi = new HashMap<UUID, FormatInfo>();
	public List<CustomPlaceholder> customPlaceholders = new ArrayList<>();
	public SlowChatCMD slowed;
	public MuteChatCMD muted;
	private FileConfiguration config;
	private File configFile;
	private FileConfiguration groupsConfig;
	private File groupsFile;
	private FileConfiguration cmdsConfig;
	private File cmdsFile;
	private FileConfiguration cpsConfig;
	private File cpsFile;
	
	public void onEnable() {
		instance = this;
		setupFiles();
		registerCustomPlaceholders();
		Bukkit.getPluginManager().registerEvents(new MainChatHandler(), this);
		Bukkit.getPluginManager().registerEvents(new ConnectionHandler(), this);
		registerCmds();
		checkGroups();
		for (Player p : Bukkit.getOnlinePlayers()) ConnectionHandler.add(p);
	}
	
	public void onDisable() {
		saveConfig();
		saveCmds();
		saveGroups();
		saveCPs();
	}

	public void setupFiles() {
		File folder = getDataFolder();
		if(!folder.exists()) {
			folder.mkdir();
		}
		File conf = new File(folder, "/config.yml");
		if(!conf.exists()) {
			try {
				conf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(conf);
		this.configFile = conf;
		registerConfig();
		saveConfig();
		File groups = new File(folder, "/groups.yml");
		boolean groupsExisted = groups.exists();
		if(!groups.exists()) {
			try {
				groups.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.groupsConfig = YamlConfiguration.loadConfiguration(groups);
		this.groupsFile = groups;
		if(!groupsExisted) {
			registerGroups();
			saveGroups();
		}
		File cmds = new File(folder, "/commands.yml");
		if(!cmds.exists()) {
			try {
				cmds.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.cmdsConfig = YamlConfiguration.loadConfiguration(cmds);
		this.cmdsFile = cmds;
		registerCommands();
		saveCmds();
		File cps = new File(folder, "/customplaceholders.yml");
		boolean cpsExisted = cps.exists();
		if(!cps.exists()) {
			try {
				cps.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.cpsConfig = YamlConfiguration.loadConfiguration(cps);
		this.cpsFile = cps;
		if(!cpsExisted) {
			registerCPs();
			saveCPs();
		}
	}

	public void registerCustomPlaceholders() {
		customPlaceholders.clear();
		for(String id: this.getCPConfig().getKeys(false)) {
			if(id.equals("customplaceholders")) continue;
			CustomPlaceholder cp = new CustomPlaceholder(id);
			customPlaceholders.add(cp);
		}
	}
	
	private void registerConfig() {
		FileConfiguration c = getConfig();
		c.addDefault("colorperm.all", "act.color.all");
		c.addDefault("colorperm.black", "act.color.black");
		c.addDefault("colorperm.dark_blue", "act.color.dark_blue");
		c.addDefault("colorperm.dark_green", "act.color.dark_green");
		c.addDefault("colorperm.aqua", "act.color.aqua");
		c.addDefault("colorperm.dark_red", "act.color.black");
		c.addDefault("colorperm.purple", "act.color.black");
		c.addDefault("colorperm.gold", "act.color.gold");
		c.addDefault("colorperm.light_gray", "act.color.light_gray");
		c.addDefault("colorperm.dark_gray", "act.color.dark_gray");
		c.addDefault("colorperm.light_blue", "act.color.light_blue");
		c.addDefault("colorperm.light_green", "act.color.ligh_green");
		c.addDefault("colorperm.light_blue", "act.color.light_blue");
		c.addDefault("colorperm.light_red", "act.color.light_red");
		c.addDefault("colorperm.magenta", "act.color.magenta");
		c.addDefault("colorperm.yellow", "act.color.yellow");
		c.addDefault("colorperm.white", "act.color.white");
		c.addDefault("colorperm.bold", "act.color.bold");
		c.addDefault("colorperm.strikethr", "act.color.strikethrough");
		c.addDefault("colorperm.underline", "act.color.underline");
		c.addDefault("colorperm.italic", "act.color.italic");
		c.addDefault("ignore.characterList", Arrays.asList("@"));
		c.addDefault("ignore.@.perm", "act.ignoreCharacter");
		c.addDefault("chat.autoUpdateGroups", false);
		c.options().header("#########################\n\n# AdvancedChatTorch\n\n# Created By MCGamer00000\n\n#########################");
		c.options().copyHeader(true);
		c.options().copyDefaults(true);
	}
	
	private void registerCommands() {
		FileConfiguration cmd = getCmds();
		cmd.addDefault("mainPerm", "act.cmd");
		cmd.addDefault("noPermArgs", "&eAdvanced Chat Torch &c[&4<v>&c]");
		cmd.addDefault("noArgs", "&eAdvanced Chat Torch &c[&4<v>&c]"
				+ "\n&c- &ereload [Player] - &6Reloads the Plugin / Player Info"
				+ "\n&c- &eclearchat - &6Clears the chat"
				+ "\n&c- &eslowchat - &6Slows the chat"
				+ "\n&c- &emutechat - &6Mutes the chat");
		cmd.addDefault("reload.noPerm", "&cError: You do not have permission to perform this command.");
		cmd.addDefault("reload.perm", "act.reload");
		cmd.addDefault("reload.invalidPlayer", "&c[&eACT&c] &cInvalid player. Check the name again.");
		cmd.addDefault("reload.playerSuccess", "&c[&eACT&c] &6Successfully reloaded the player information!");
		cmd.addDefault("reload.playerFail", "&c[&eACT&c] &cSomething went wrong when reloading the player information!");
		cmd.addDefault("reload.success", "&c[&eACT&c] &6Successfully reloaded the plugin!");
		cmd.addDefault("reload.fail", "&c[&eACT&c] &6Something went wrong when reloading!");
		cmd.addDefault("clearchat.noPerm", "&cError: You do not have permission to perform this command.");
		cmd.addDefault("clearchat.enabled", true);
		cmd.addDefault("clearchat.perm", "act.clearchat");
		cmd.addDefault("clearchat.noClearPerm", "act.clearchat.bypass");
		cmd.addDefault("clearchat.onNoClear", "\n&6Because you are an administrator, the chat wasn't clear for you.");
		cmd.addDefault("clearchat.success", "\n&c[&eACT&c] &6The chat has been cleared (by %player%)\n");
		cmd.addDefault("slowchat.noPerm", "&cError: You do not have permission to perform this command.");
		cmd.addDefault("slowchat.enabled", true);
		cmd.addDefault("slowchat.perm", "act.slowchat");
		cmd.addDefault("slowchat.bypassPerm", "act.slowchat.bypass");
		cmd.addDefault("slowchat.slowed", "&c[&eACT&c] &6Successfully slowed the chat!");
		cmd.addDefault("slowchat.unslowed", "&c[&eACT&c] &6Successfully unslowed the chat!");
		cmd.addDefault("slowchat.wait", "&c[&eACT&c] &6Wait <time>s until you can talk again!");
		cmd.addDefault("slowchat.cooldown", 10000);
		cmd.addDefault("mutechat.enabled", true);
		cmd.addDefault("mutechat.noPerm", "&cError: You do not have permission to perform this command.");
		cmd.addDefault("mutechat.perm", "act.mutechat");
		cmd.addDefault("mutechat.override", "act.mutechat.bypass");
		cmd.addDefault("mutechat.unmuted", "&c[&eACT&c] &6Successfully unmuted the chat!");
		cmd.addDefault("mutechat.muted", "&c[&eACT&c] &6Successfully muted the chat!");
		cmd.addDefault("mutechat.mute", "&c[&eACT&c] &6You can't talk! Chat is silenced!");
		cmd.options().copyDefaults(true);
	}
	
	private void registerCPs() {
		FileConfiguration cph = getCPConfig();
		cph.addDefault("prefix.independentTextComponent", true);
		cph.addDefault("prefix.normal.priority", 1);
		cph.addDefault("prefix.normal.perm", "act.prefix.normal");
		cph.addDefault("prefix.normal.value", "&7[N]");
		cph.addDefault("prefix.normal.useGroupEvents", true);
		cph.addDefault("prefix.donor.priority", 2);
		cph.addDefault("prefix.donor.perm", "act.prefix.donor");
		cph.addDefault("prefix.donor.value", "&7[&a&o$&7]");
		cph.addDefault("prefix.donor.useGroupEvents", true);
		cph.addDefault("prefix.staff.priority", 3);
		cph.addDefault("prefix.staff.perm", "act.prefix.staff");
		cph.addDefault("prefix.staff.value", "&7[&b&lS&7]");
		cph.addDefault("prefix.staff.useGroupEvents", true);
		cph.addDefault("suffix.independentTextComponent", true);
		cph.addDefault("suffix.member.priority", 1);
		cph.addDefault("suffix.member.perm", "act.suffix.member");
		cph.addDefault("suffix.member.value", "&7[&8Member&7]");
		cph.addDefault("suffix.member.hoverText", "&7This player has not supported the server.\n&7Click here to message the player.");
		cph.addDefault("suffix.member.suggestCmd", "/msg %player_name% ");
		cph.addDefault("suffix.donor.priority", 2);
		cph.addDefault("suffix.donor.perm", "act.suffix.donor");
		cph.addDefault("suffix.donor.value", "&7[&bDonor&7]");
		cph.addDefault("suffix.donor.hoverText", "&bThis player has supported the server.\n&7Click here to message the player.");
		cph.addDefault("suffix.donor.suggestCmd", "/msg %player_name% ");
		cph.addDefault("colored_name.independentTextComponent", false);
		cph.addDefault("colored_name.red.priority", 1);
		cph.addDefault("colored_name.red.perm", "act.name.red");
		cph.addDefault("colored_name.red.value", "&c%player_name%");
		cph.addDefault("colored_name.blue.priority", 2);
		cph.addDefault("colored_name.blue.perm", "act.name.blue");
		cph.addDefault("colored_name.blue.value", "&b%player_name%");
		cph.addDefault("colored_name.green.priority", 3);
		cph.addDefault("colored_name.green.perm", "act.name.green");
		cph.addDefault("colored_name.green.value", "&a%player_name%");
		cph.options().copyDefaults(true);
	}
	
	private void registerGroups() {
		FileConfiguration g = getGroups();
		g.addDefault("default.priority", 1);
		g.addDefault("default.perm", "act.group.default");
		g.addDefault("default.format", "{prefix} &f%player_name% {suffix}&7: %message%");
		g.addDefault("default.useChatColor", true);
		g.addDefault("default.chatColor", "7");
		g.addDefault("default.on_click.suggest_command", Boolean.valueOf(true));
		g.addDefault("default.on_click.suggested_command", "/m %player_name%");
		g.addDefault("default.on_click.run_command", Boolean.valueOf(false));
		g.addDefault("default.on_click.runned_command", "/info %player_name%");
		g.addDefault("default.on_hover.show_text", Boolean.valueOf(true));
		g.addDefault("default.on_hover.text_shown", "&a%player_name%\nInfo goes here\nFor each line.\n&a+ Colors!");
		g.addDefault("donor.priority", 2);
		g.addDefault("donor.perm", "act.group.donor");
		g.addDefault("donor.format", "{prefix} {colored_name} {suffix}&7: %message%");
		g.addDefault("donor.useChatColor", true);
		g.addDefault("donor.chatColor", "b");
		g.addDefault("donor.on_click.suggest_command", Boolean.valueOf(true));
		g.addDefault("donor.on_click.suggested_command", "/m %player_name%");
		g.addDefault("donor.on_click.run_command", Boolean.valueOf(false));
		g.addDefault("donor.on_click.runned_command", "/info %player_name%");
		g.addDefault("donor.on_hover.show_text", Boolean.valueOf(true));
		g.addDefault("donor.on_hover.text_shown", "&7[&bDonor&7]\n&a%player_name%\nInfo goes here\nFor each line.\n&a+ Colors!");
		g.options().copyDefaults(true);
	}
	
	private void registerCmds() {
		if(getCmds().getBoolean("clearchat.enabled")) Bukkit.getPluginCommand("clearchat").setExecutor(new BukkitCommand());
		if(getCmds().getBoolean("slowchat.enabled")) Bukkit.getPluginCommand("slowchat").setExecutor(new BukkitCommand());
		if(getCmds().getBoolean("mutechat.enabled")) Bukkit.getPluginCommand("mutechat").setExecutor(new BukkitCommand());
		Bukkit.getPluginCommand("act").setExecutor(new CmdHandler());
		CmdHandler.subcmds.add(new ReloadCMD());
		slowed = new SlowChatCMD();
		muted = new MuteChatCMD();
		BukkitCommand.add(slowed);
		BukkitCommand.add(muted);
		BukkitCommand.add(new ClearChatCMD());
	}
	
	public static AdvancedChatTorch getInstance() {
		return instance;
	}
	
	@Override
	public FileConfiguration getConfig() {
		return config;
	}
	
	@Override
	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getGroups() {
		return groupsConfig;
	}
	
	public void saveGroups() {
		try {
			groupsConfig.save(groupsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getCPConfig() {
		return cpsConfig;
	}
	
	public void saveCPs() {
		try {
			cpsConfig.save(cpsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getCmds() {
		return cmdsConfig;
	}
	
	public void saveCmds() {
		try {
			cmdsConfig.save(cmdsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<CustomPlaceholder> getCustomPlaceholders() {
		return customPlaceholders;
	}
	
	public void checkGroups() {
		for(Object s: getGroups().getKeys(false)) {
			if(s.equals("groups")) continue;
			if(!getGroups().contains(s.toString())) 
				getLogger().log(Level.SEVERE, "\nError! Group \"" + s + "\" doesn't exist!\nFix the problem, or contact the the plugin creator, MCGamer00000, on Spigot for support.\n");
			if(!getGroups().contains(s.toString() + ".perm")) 
				getLogger().log(Level.SEVERE, "\nError! Group \"" + s + "\" doesn't have a permission set!\nFix the problem, or contact the the plugin creator, MCGamer00000, on Spigot for support.\n");
		}
	}
	
}
