package me.mcgamer00000.act.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ChatMessage {

	List<ChatObject> chatObjects = new ArrayList<ChatObject>();
	
	public ChatMessage(String msg, Player p) {
		chatObjects.add(new ChatObject(msg));
	}

	public int size() {
		return chatObjects.size();
	}
	
	public ChatObject get(int i) {
		return chatObjects.get(i);
	}
	
	public List<ChatObject> getChatObjects() {
		return chatObjects;
	}
	
}
