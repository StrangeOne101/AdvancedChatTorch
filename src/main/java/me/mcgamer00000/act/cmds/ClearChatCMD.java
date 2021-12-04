package me.mcgamer00000.act.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcgamer00000.act.utils.StringHelper;

/*
 * Class for the ClearChat sub-command
 */
public class ClearChatCMD extends ACTCommand {

	public ClearChatCMD() {
		super("clearchat", StringHelper.getCmdStr("clearchat.perm"), 0);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		for(Player p: Bukkit.getOnlinePlayers()) {
			if(!p.hasPermission(StringHelper.getCmdStr("clearchat.noClearPerm"))) {
				for(int i = 0; i < 100; i++) {
					p.sendMessage("\n");
				}
			} else {
				p.sendMessage(StringHelper.ccGetCmdStr("clearchat.onNoClear"));
			}
		}
		Bukkit.broadcastMessage(StringHelper.ccGetCmdStr("clearchat.success").replace("%player%", sender.getName()));
	}

	
	
}
