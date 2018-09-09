package me.mcgamer00000.act.utils;

public class FormatInfo
{
  private int priority;
  private String name;
  
  public FormatInfo(int p, String name) {
    this.priority = p;
    this.name = name;
  }
  
  public int getPriority() {
    return priority;
  }
  
  public String getName() {
    return name;
  }
}
