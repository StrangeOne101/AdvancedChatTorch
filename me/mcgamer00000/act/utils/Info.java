package me.mcgamer00000.act.utils;

public class Info {
	
	int p;
	String name;
	
	public Info(int priority, String name) {
		this.p = priority;
		this.name = name;
	}
	
	public int getPri() {
		return p;
	}
	
	public String getName() {
		return StringHelper.cc(name);
	}
	
}