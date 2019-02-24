package me.mcgamer00000.act.events;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mcgamer00000.act.cmds.ACTCommand;
import me.mcgamer00000.act.cmds.BukkitCommand;
import me.mcgamer00000.act.utils.StringHelper;

/*
 * CommandExecutor for the /act command.
 */
public class CmdHandler implements CommandExecutor {

	public static ArrayList<ACTCommand> subcmds = new ArrayList<ACTCommand>();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 1) {
			if(sender.hasPermission(StringHelper.getCmdStr("mainPerm"))) {
				sender.sendMessage(StringHelper.ccGetCmdStr("noArgs").replace("<v>", "2.2.0"));
			} else {
				sender.sendMessage(StringHelper.ccGetCmdStr("noPermArgs").replace("<v>", "2.2.0"));
			}
			return true;
		}
		for (ACTCommand cmd : subcmds) {
			if (args[0].equalsIgnoreCase(cmd.getName())) {
				if (!sender.hasPermission(cmd.getPerm())) {
					sender.sendMessage(StringHelper.ccGetCmdStr(cmd.getName() + ".noPerm"));
					return true;
				}
				if (args.length < cmd.getLength()) {
					sender.sendMessage(StringHelper.ccGetCmdStr(cmd.getName() + ".invalidArgs"));
					return true;
				}
				cmd.execute(sender, args);
				return true;
			}
		}
		for(ACTCommand cmd: BukkitCommand.cmds) {
			if (args[0].equalsIgnoreCase(cmd.getName())) {
				if (!sender.hasPermission(cmd.getPerm())) {
					sender.sendMessage(StringHelper.ccGetCmdStr(cmd.getName() + ".noPerm"));
					return true;
				}
				cmd.execute(sender, args);
				return true;
			}
		}
		if(sender.hasPermission(StringHelper.getCmdStr("mainPerm"))) {
			sender.sendMessage(StringHelper.ccGetCmdStr("noArgs").replace("<v>", "2.2.0"));
		} else {
			sender.sendMessage(StringHelper.ccGetCmdStr("noPermArgs").replace("<v>", "2.2.0"));
		}
		return true;
	}
	
}
