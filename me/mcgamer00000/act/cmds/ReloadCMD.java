package me.mcgamer00000.act.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mcgamer00000.act.events.ConnectionHandler;

public class ReloadCMD extends ACTCommand {
	
	public ReloadCMD() {
		super("reload", pl.getCmds().getString("reload.perm"), 1);
	}
	
	public void execute(CommandSender sender, String[] args) {
		if(args.length >= 2) {
			try {
			Player p = Bukkit.getPlayer(args[1]);
			if(p == null || !p.isOnline()) {
				sender.sendMessage(cc(pl.getCmds().getString("reload.invalidPlayer")));
				return;
			}
			ConnectionHandler.remove(p);
			ConnectionHandler.add(p);
			sender.sendMessage(cc(pl.getCmds().getString("reload.playerSuccess")));
			} catch(Exception e) {
				sender.sendMessage(cc(pl.getCmds().getString("reload.playerFail")));
			}
			return;
		}
		try {
			pl.setupFiles();
			pl.registerCustomPlaceholders();
			for (Player p : Bukkit.getOnlinePlayers()) {
				ConnectionHandler.remove(p);
				ConnectionHandler.add(p);
			}
		} catch(Exception e) {
			sender.sendMessage(cc(pl.getCmds().getString("reload.fail")));
			return;
		}
		sender.sendMessage(cc(pl.getCmds().getString("reload.success")));
	}
}
