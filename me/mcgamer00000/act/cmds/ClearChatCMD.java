package me.mcgamer00000.act.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCMD extends ACTCommand {

	public ClearChatCMD() {
		super("clearchat", getCStr("clearchat.perm"), 0);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		StringBuilder message = new StringBuilder();
		for(int i = 0; i < 100; i++) {
			message.append("\n");
		}
		for(Player p: Bukkit.getOnlinePlayers()) {
			if(!p.hasPermission(getCStr("clearchat.noClearPerm"))) {
				p.sendMessage(message.toString());
			} else {
				p.sendMessage(cc(pl.getCmds().getString("clearchat.onNoClear")));
			}
		}
		Bukkit.broadcastMessage(cc(getCStr("clearchat.success").replace("%player%", sender.getName())));
	}

	
	
}
