package me.mcgamer00000.act.cmds;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.utils.StringHelper;

/*
 * Class for the SlowChat sub-command
 */
public class SlowChatCMD extends ACTCommand {

	public HashMap<UUID, Long> Cooldowns = new HashMap<UUID, Long>();
	public boolean slowed = false;
	
	public SlowChatCMD() {
		super("slowchat", StringHelper.getCmdStr("slowchat.perm"), 0);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(slowed) {
			slowed = false;
			sender.sendMessage(StringHelper.ccGetCmdStr("slowchat.unslowed"));
			Cooldowns.clear();
		} else {
			slowed = true;
			sender.sendMessage(StringHelper.ccGetCmdStr("slowchat.slowed"));
		}
	}

	public void filter(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if(!slowed) return;
		if(p.hasPermission(StringHelper.getCmdStr("slowchat.bypassPerm"))) return;
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		if(!Cooldowns.containsKey(u)) {
			Cooldowns.put(u, System.currentTimeMillis()-pl.getCmds().getInt("slowchat.cooldown")-100);
		}
		if(System.currentTimeMillis() < Cooldowns.get(u) + pl.getCmds().getInt("slowchat.cooldown")) {
			String message = StringHelper.getCmdStr("slowchat.wait");
			if(pl.getCmds().getString("slowchat.wait").contains("<time>")) 
				message = message.replace("<time>", (Cooldowns.get(u) + pl.getCmds().getInt("slowchat.cooldown") - System.currentTimeMillis()) / 1000 + "");
			p.sendMessage(StringHelper.cc(message));
			e.setCancelled(true);
			return;
		}
		Cooldowns.put(u, System.currentTimeMillis());
		return;
	}
	
}
