package me.mcgamer00000.act.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.mcgamer00000.act.AdvancedChatTorch;
import me.mcgamer00000.act.PlaceholderAPIIntegrator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class TextMaker {

	public TextComponent text;
	
	public TextMaker(ChatMessage message, Player p) {
		BaseComponent[] baseComp = new BaseComponent[message.size()];
		for(int i = 0; i < message.size(); i++) {
			ChatObject chatObject = message.getChatObjects().get(i);
			if(chatObject.message.contains("%message%"))
				chatObject.message = chatObject.message.replace("%message%", message.messageSent);
			TextComponent textComp = new TextComponent(TextComponent.fromLegacyText(chatObject.message));
			if(chatObject.getHover() != null) {
				ArrayList<TextComponent> tcs = new ArrayList<TextComponent>();
				tcs.add(new TextComponent(PlaceholderAPIIntegrator.setPlaceholders(p, StringHelper.cc(chatObject.getHover()))));
				TextComponent[] bc = tcs.toArray(new TextComponent[tcs.size() - 1]);
				textComp.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, bc));
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
		ArrayList<TextComponent> tcs = new ArrayList<TextComponent>();
		tcs.add(new TextComponent(StringHelper.cc(s)));
		TextComponent[] bc = tcs.toArray(new TextComponent[tcs.size() - 1]);
		text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, bc));
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
					if(subPlaceholder.hasPerm(p)) {
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
