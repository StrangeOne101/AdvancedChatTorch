package me.mcgamer00000.act.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.utils.StringHelper;

/*
 * Class for the MuteChat sub-command
 */
public class MuteChatCMD extends ACTCommand {

	public static boolean muted;
	
	public MuteChatCMD() {
		super("mutechat", StringHelper.getCmdStr("mutechat.perm"), 0);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(muted) {
			muted = false;
			sender.sendMessage(StringHelper.ccGetCmdStr("mutechat.unmuted"));
		} else {
			muted = true;
			sender.sendMessage(StringHelper.ccGetCmdStr("mutechat.muted"));
		}
	}

	public void filter(AsyncPlayerChatEvent e) {
		if(!muted) return;
		Player p = e.getPlayer();
		if(!p.hasPermission(StringHelper.getCmdStr("mutechat.override"))) {
			e.setCancelled(true);
			p.sendMessage(StringHelper.ccGetCmdStr("mutechat.mute"));
		}
		return;
	}

}
