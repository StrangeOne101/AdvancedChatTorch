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
			for(int i = 0; i < message.size(); i++) {
				ChatObject chatObj = message.get(i);
				String objMsg = chatObj.getMessage();
				if(!objMsg.contains("{" + cp.getId() + "}")) continue;
				SubPlaceholder bestPlaceholder = null;
				try {
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
				}catch(Exception e1) {
					AdvancedChatTorch.getInstance().getLogger().info("Something went wrong when parsing for the custom placeholder, report this to the author!");
					e1.printStackTrace();
				}
				if(cp.isNotIndependent() && bestPlaceholder != null) {
					chatObj.setMessage(objMsg.replace("{" + cp.getId() + "}", StringHelper.cc(PlaceholderAPIIntegrator.setPlaceholders(p, bestPlaceholder.getValue()))));
				} else {
					int i2 = objMsg.indexOf("{" + cp.getId() + "}");
					int i3 = i2 + ("{" + cp.getId() + "}").length();
					chatObj.setMessage(objMsg.substring(0, i2));
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
					message.getChatObjects().add(i+2, new ChatObject(objMsg.substring(i3)));
				}
			}
		}
	}
	
}
