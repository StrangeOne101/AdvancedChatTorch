package me.mcgamer00000.act.filter;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPIIntegrator;
import me.mcgamer00000.act.utils.ChatMessage;
import me.mcgamer00000.act.utils.ChatObject;
import me.mcgamer00000.act.utils.CustomPlaceholder;
import me.mcgamer00000.act.utils.StringHelper;
import me.mcgamer00000.act.utils.SubPlaceholder;

/*
 * Formatter for replacing custom placeholders.
 */
public class CustomPlaceholdersFilter {

	public void filter(AsyncPlayerChatEvent e, ChatMessage message) {
		Player p = e.getPlayer();
		for(CustomPlaceholder cp: AdvancedChatTorch.getInstance().getCustomPlaceholders()) {
			int max = ((AdvancedChatTorch.getInstance().getConfig().getBoolean("allowCPMessages"))?message.size():((message.size() == 1) ? 1 : message.size()-1));
			for(int i = 0; i < max; i++) {
				ChatObject chatObj = message.get(i);
				String objMsg = chatObj.getMessage();
				if(!objMsg.contains("{" + cp.getId() + "}")) continue;
				int i2 = objMsg.indexOf("{" + cp.getId() + "}");
				int i3 = i2 + ("{" + cp.getId() + "}").length();
				chatObj.setMessage(objMsg.substring(0, i2));
				try {
					SubPlaceholder bestPlaceholder = null;;
					for(SubPlaceholder subPlaceholder: cp.getPlaceholders()) {
						if(subPlaceholder.hasPerm(p)) {
							if(bestPlaceholder == null) {
								bestPlaceholder = subPlaceholder;
							} else {
								if(bestPlaceholder.getPriority() < subPlaceholder.getPriority()) {
									bestPlaceholder = subPlaceholder;
								}
							}
						}
					}
					if(bestPlaceholder != null) {
						String hover = bestPlaceholder.getHover();
						String suggest = bestPlaceholder.getSuggest();
						String run = bestPlaceholder.getRun();
						message.getChatObjects().add(i+1, new ChatObject(StringHelper.cc(PlaceholderAPIIntegrator.setPlaceholders(p, bestPlaceholder.getValue())), hover, suggest, run));
						if(bestPlaceholder.isText()) {
							message.getChatObjects().get(i+1).setIsText(true);
						}
					} else {
						message.getChatObjects().add(i+1, new ChatObject(""));
					}
				}catch(Exception e1) {
					AdvancedChatTorch.getInstance().getLogger().info("Something went wrong when parsing for the custom placeholder, report this to the author!");
					e1.printStackTrace();
				}
				message.getChatObjects().add(i+2, new ChatObject(objMsg.substring(i3)));
				max = ((AdvancedChatTorch.getInstance().getConfig().getBoolean("allowCPMessages"))?message.size():((message.size() == 1) ? 1 : message.size()-1));
			}
		}
	}
	
}
