package me.mcgamer00000.act.cmds;

import org.bukkit.command.CommandSender;

import me.mcgamer00000.act.utils.Extend;

public abstract class ACTCommand extends Extend{
	String name;
	String perm;
	int length;
	
	public ACTCommand(String name, String perm, int length) {
		this.name = name;
		this.perm = perm;
		this.length = length;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPerm() {
		return perm;
	}
	
	public int getLength() {
		return length;
	}
	
	public abstract void execute(CommandSender sender, String[] args);
}
