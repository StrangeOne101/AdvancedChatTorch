package me.mcgamer00000.act.utils;

public class Info extends Extend{
	
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
		return cc(name);
	}
	
}