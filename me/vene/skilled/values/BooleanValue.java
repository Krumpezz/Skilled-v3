package me.vene.skilled.values;

import me.vene.skilled.utilities.StringRegistry;

public class BooleanValue {
  private String name;
  
  private boolean value;
  
  public BooleanValue(String name, boolean value) {
    this.name = StringRegistry.register(name);
    this.value = value;
  }
  
  public String getName() {
    return StringRegistry.register(this.name);
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public boolean getState() {
    return this.value;
  }
  
  public void toggle() {
    this.value = !this.value;
  }
}