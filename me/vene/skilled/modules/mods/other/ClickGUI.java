package me.vene.skilled.modules.mods.other;

import java.awt.Color;
import me.vene.skilled.SkilledClient;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends Module {
  private static NumberValue red;
  
  private static NumberValue green;
  
  private static NumberValue blue;
  
  private static int hudR = 21;
  
  private static int hudG = 19;
  
  private static int hudB = 21;
  
  public static int moduleR = 26;
  
  public static int moduleG = 26;
  
  public static int moduleB = 26;
  
  private static BooleanValue rainbow = new BooleanValue(StringRegistry.register(new String(new char[] { 'R', 'a', 'i', 'n', 'b', 'o', 'w' }, )), false);
  
  public ClickGUI() {
    super(StringRegistry.register(new String(new char[] { 'G', 'U', 'I', ' ', 'C', 'o', 'l', 'o', 'r' }, )), 54, Category.O);
    addValue(red = new NumberValue(StringRegistry.register(new String(new char[] { 'R', 'e', 'd' }, )), 35.0D, 1.0D, 255.0D));
    addValue(green = new NumberValue(StringRegistry.register(new String(new char[] { 'G', 'r', 'e', 'e', 'n' }, )), 245.0D, 1.0D, 255.0D));
    addValue(blue = new NumberValue(StringRegistry.register(new String(new char[] { 'B', 'l', 'u', 'e' }, )), 15.0D, 1.0D, 255.0D));
    addOption(rainbow);
  }
  
  public void onEnable() {
    setState(false);
    if ((Minecraft.func_71410_x()).field_71439_g != null && (Minecraft.func_71410_x()).field_71462_r == null) {
      Minecraft.func_71410_x().func_147108_a((GuiScreen)SkilledClient.getClickGUI());
      toggle();
    } 
  }
  
  public static int getColor() {
    return rainbow.getState() ? Color.getHSBColor((float)(System.currentTimeMillis() * 0.5D % 3000.0D / 3000.0D), 0.8F, 0.8F).getRGB() : rgb((int)red.getValue(), (int)green.getValue(), (int)blue.getValue());
  }
  
  public static int getHudColor() {
    return rgb(hudR, hudB, hudG);
  }
  
  public static int getModuleColor() {
    return rgb(moduleR, moduleG, moduleB);
  }
  
  public static int rgb(int red, int green, int blue) {
    return -16777216 + (red << 16) + (green << 8) + blue;
  }
}