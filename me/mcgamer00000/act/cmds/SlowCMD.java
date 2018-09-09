package me.mcgamer00000.act.cmds;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SlowCMD extends ACTCommand {

	public HashMap<UUID, Long> Cooldowns = new HashMap<UUID, Long>();
	public boolean slowed = false;
	
	public SlowCMD() {
		super("slowchat", getCStr("slowchat.perm"), 0);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(slowed) {
			slowed = false;
			sender.sendMessage(cc(getCStr("slowchat.unslowed")));
			Cooldowns.clear();
		} else {
			slowed = true;
			sender.sendMessage(cc(getCStr("slowchat.slowed")));
		}
	}

	public void filter(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if(!slowed) return;
		if(p.hasPermission(getCStr("slowchat.bypassPerm"))) return;
		if(!Cooldowns.containsKey(u)) {
			Cooldowns.put(u, System.currentTimeMillis()-pl.getCmds().getInt("slowchat.cooldown")-100);
		}
		if(System.currentTimeMillis() < Cooldowns.get(u) + pl.getCmds().getInt("slowchat.cooldown")) {
			String message = pl.getCmds().getString("slowchat.wait");
			if(pl.getCmds().getString("slowchat.wait").contains("<time>")) 
				message = message.replace("<time>", (Cooldowns.get(u) + pl.getCmds().getInt("slowchat.cooldown") - System.currentTimeMillis()) / 1000 + "");
			p.sendMessage(cc(message));
			e.setCancelled(true);
			return;
		}
		Cooldowns.put(u, System.currentTimeMillis());
		return;
	}
	
}
