package me.mcgamer00000.act.utils;

import java.util.ArrayList;

import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPIIntegrator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class TextMaker {

	private ChatMessage message;
	public TextComponent text;
	private Player p;
	
	public TextMaker(ChatMessage message, Player p) {
		this.p = p;
		this.message = message;
	}
	
	// Only used whenever relational placeholders is disabled
	public void convertMessageToComponents() {
		BaseComponent[] baseComp = new BaseComponent[message.size()];
		for(int i = 0; i < message.size(); i++) {
			ChatObject chatObject = message.getChatObjects().get(i);
			String msg = chatObject.message;
			msg = PlaceholderAPIIntegrator.setPlaceholders(p, msg);
			if(msg.contains("%message%"))
				msg = msg.replace("%message%", message.messageSent);
			TextComponent textComp = new TextComponent(TextComponent.fromLegacyText(StringHelper.cc(msg)));
			if(chatObject.getHover() != null) {
				BaseComponent[] components = TextComponent.fromLegacyText(StringHelper.cc(PlaceholderAPIIntegrator.setPlaceholders(p, chatObject.getHover())), ChatColor.WHITE.asBungee());
				textComp.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(components)));
			}
			if(chatObject.getColor() != null) {
				textComp.setColor(chatObject.getColor().asBungee());
			}
			if(chatObject.getSuggest() != null) {
				textComp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, PlaceholderAPIIntegrator.setPlaceholders(p, StringHelper.cc(chatObject.getSuggest()))));
			}
			if(chatObject.getRun() != null) {
				textComp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, PlaceholderAPIIntegrator.setPlaceholders(p, StringHelper.cc(chatObject.getRun()))));
			}
			if(chatObject.isText()) {
				setTextAttr(textComp, p);
			}
			baseComp[i] = textComp;
		}
		text = new TextComponent(baseComp);
	}
	
	private void setTextAttr(TextComponent text, Player p) {
		if (getConfigBoolean(p, "on_click.suggest_command")) {
			addClickSuggest(text, customPlaceholder(p, getConfigString(p, "on_click.suggested_command")));
		}
		if (getConfigBoolean(p, "on_click.run_command")) {
			addClickRun(text, customPlaceholder(p, getConfigString(p, "on_click.runned_command")));
		}
		if(getConfigBoolean(p, "on_hover.show_text")) {
			addHover(text, customPlaceholder(p, getConfigString(p, "on_hover.text_shown")));
		}
	}

	public void addHover(TextComponent text, String s) {
		if(s == null) return;

		BaseComponent[] components = TextComponent.fromLegacyText(StringHelper.cc(s), ChatColor.WHITE.asBungee());
		text.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(components)));
		return;
	}
	
	public void addClickSuggest(TextComponent text, String s) {
		if(s == null) return;
		text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, s));
		return;
	}
	
	public void addClickRun(TextComponent text, String s) {
		if(s == null) return;
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, s));
		return;
	}
	
	public TextComponent getText() {
		return text;
	}
	
	// Only used when relational placeholders is enabled. (If enabled, you have to compute placeholders for all players, not just once)
	public TextComponent getRelationalText(Player to) {
		BaseComponent[] baseComp = new BaseComponent[message.size()];
		for(int i = 0; i < message.size(); i++) {
			ChatObject chatObject = message.getChatObjects().get(i);
			String msg = chatObject.message;
			msg = PlaceholderAPIIntegrator.setBothPlaceholders(p, to, msg);
			if(msg.contains("%message%"))
				msg = msg.replace("%message%", message.messageSent);
			TextComponent textComp = new TextComponent(TextComponent.fromLegacyText(StringHelper.cc(msg)));
			if(chatObject.getHover() != null) {
				BaseComponent[] components = TextComponent.fromLegacyText(StringHelper.cc(PlaceholderAPIIntegrator.setPlaceholders(p, chatObject.getHover())), ChatColor.WHITE.asBungee());
				textComp.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new Text(components)));
			}
			if(chatObject.getColor() != null) {
				textComp.setColor(chatObject.getColor().asBungee());
			}
			if(chatObject.getSuggest() != null) {
				textComp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, PlaceholderAPIIntegrator.setBothPlaceholders(p, to, StringHelper.cc(chatObject.getSuggest()))));
			}
			if(chatObject.getRun() != null) {
				textComp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, PlaceholderAPIIntegrator.setBothPlaceholders(p, to, StringHelper.cc(chatObject.getRun()))));
			}
			if(chatObject.isText()) {
				setTextAttr(textComp, p);
			}
			baseComp[i] = textComp;
		}
		return new TextComponent(baseComp);
	}
	
	public String getConfigString(Player p, String extra) {
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		return pl.getGroups().getString (pl.uufi.get(p.getUniqueId()).getName() + "." + extra);
	}
	
	public boolean getConfigBoolean(Player p, String extra) {
		AdvancedChatTorch pl = AdvancedChatTorch.getInstance();
		return pl.getGroups().getBoolean(pl.uufi.get(p.getUniqueId()).getName() + "." + extra);
	}
	
	public String customPlaceholder(Player p, String s2) {
		String message = s2;
		for(CustomPlaceholder cp: AdvancedChatTorch.getInstance().getCustomPlaceholders()) {
			String id = cp.getId();
			if(!message.contains("{" + id + "}")) continue;
			try {
				SubPlaceholder bestPlaceholder = null;;
				for(SubPlaceholder subPlaceholder: cp.getPlaceholders()) {
					if(subPlaceholder.hasPerm(p) && (subPlaceholder.getLogic() == null || subPlaceholder.getLogic().check(p))) {
						if(bestPlaceholder == null)
							bestPlaceholder = subPlaceholder;
						else {
							if(bestPlaceholder.getPriority() < subPlaceholder.getPriority())
								bestPlaceholder = subPlaceholder;
						}
					}
				}
				if(bestPlaceholder != null) {
					message = message.replace("{" + id + "}", bestPlaceholder.getValue());
				} else {
					message = message.replace("{" + id + "}", "");
				}
			}catch(Exception e1) {
				AdvancedChatTorch.getInstance().getLogger().info("Something went wrong when parsing for the custom placeholder, report this to the author!");
				e1.printStackTrace();
			}
		}
		s2 = message;
		return PlaceholderAPIIntegrator.setPlaceholders(p, s2);
	}
	
}
