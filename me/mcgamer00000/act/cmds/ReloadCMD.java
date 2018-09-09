package me.mcgamer00000.act.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.events.ConnectionHandler;
import me.mcgamer00000.act.utils.StringHelper;

/*
 * Class for the Reload sub-command
 */
public class ReloadCMD extends ACTCommand {
	
	public ReloadCMD() {
		super("reload", StringHelper.getCmdStr("reload.perm"), 1);
	}
	
	public void execute(CommandSender sender, String[] args) {
		if(args.length >= 2) {
			try {
			Player p = Bukkit.getPlayer(args[1]);
			if(p == null || !p.isOnline()) {
				sender.sendMessage(StringHelper.ccGetCmdStr("reload.invalidPlayer"));
				return;
			}
			ConnectionHandler.remove(p);
			ConnectionHandler.add(p);
			sender.sendMessage(StringHelper.ccGetCmdStr("reload.playerSuccess"));
			} catch(Exception e) {
				sender.sendMessage(StringHelper.ccGetCmdStr("reload.playerFail"));
			}
			return;
		}
		try {
			AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
			pl.setupFiles();
			pl.registerCustomPlaceholders();
			for (Player p : Bukkit.getOnlinePlayers()) {
				ConnectionHandler.remove(p);
				ConnectionHandler.add(p);
			}
		} catch(Exception e) {
			sender.sendMessage(StringHelper.ccGetCmdStr("reload.fail"));
			return;
		}
		sender.sendMessage(StringHelper.ccGetCmdStr("reload.success"));
	}
}
