package me.mcgamer00000.act.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteChatCMD extends ACTCommand {

	public static boolean muted;
	
	public MuteChatCMD() {
		super("mutechat", getCStr("mutechat.perm"), 0);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(muted) {
			muted = false;
			sender.sendMessage(cc(getCStr("mutechat.unmuted")));
		} else {
			muted = true;
			sender.sendMessage(cc(getCStr("mutechat.muted")));
		}
	}

	public void filter(AsyncPlayerChatEvent e) {
		if(!muted) return;
		Player p = e.getPlayer();
		if(!p.hasPermission(getCStr("mutechat.override"))) {
			e.setCancelled(true);
			p.sendMessage(cc(getCStr("mutechat.mute")));
		}
		return;
	}

}
