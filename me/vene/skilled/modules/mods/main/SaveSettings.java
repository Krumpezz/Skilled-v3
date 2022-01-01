package me.vene.skilled.modules.mods.main;

import com.google.gson.JsonObject;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;

public class SaveSettings extends Module {
  public SaveSettings() {
    super("Save Settings", 0, Category.G);
  }
  
  public void onEnable() {
    JsonObject obj = new JsonObject();
    obj.addProperty("Reach Min", Double.valueOf(3.0D));
    obj.addProperty("Reach Max", Double.valueOf(3.0D));
    setState(false);
  }
}