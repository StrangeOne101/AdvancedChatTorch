package me.mcgamer00000.act.cmds;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcgamer00000.act.utils.StringHelper;
/*
 * 
 * CommandExecutor of the /slowchat, /mutechat, /clearchat commands.
 * 
 */
public class BukkitCommand implements CommandExecutor {

	public static ArrayList<ACTCommand> cmds = new ArrayList<ACTCommand>();
	
	public static void add(ACTCommand cmd) {
		cmds.add(cmd);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for (ACTCommand cmd : cmds) {
			if(command.getName().equals(cmd.getName())) {
				if (!sender.hasPermission(cmd.getPerm())) {
					sender.sendMessage(StringHelper.ccGetCmdStr(cmd.getName() + ".noPerm"));
					return true;
				}
				cmd.execute(sender, args);
				return true;
			}
		}
		
		return true;
	}

	
	
}
