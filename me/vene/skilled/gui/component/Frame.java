package me.vene.skilled.gui.component;

import java.util.ArrayList;
import me.vene.skilled.gui.parts.ConsolePart;
import me.vene.skilled.gui.parts.ModulesPart;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.other.ClickGUI;
import me.vene.skilled.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class Frame {
  public ArrayList<Component> components = new ArrayList<Component>();
  
  public Category category;
  
  public boolean open;
  
  private int width;
  
  private int y;
  
  private int x;
  
  private int barHeight;
  
  public boolean isDragging;
  
  public int dragX;
  
  public int dragY;
  
  public Frame(Category cat) {
    this.category = cat;
    this.width = 118;
    this.x = 5;
    this.y = 5;
    this.barHeight = 15;
    this.dragX = 0;
    this.open = false;
    this.isDragging = false;
    int tY = this.barHeight;
    if (this.category.getID() == 8) {
      ConsolePart consolePart = new ConsolePart(this, tY);
      this.components.add(consolePart);
    } 
    for (Module mod : Module.getCategoryModules(this.category)) {
      ModulesPart modButton = new ModulesPart(mod, this, tY);
      this.components.add(modButton);
      tY += 16;
    } 
  }
  
  public ArrayList<Component> getComponents() {
    return this.components;
  }
  
  public void setX(int newX) {
    this.x = newX;
  }
  
  public void setY(int newY) {
    this.y = newY;
  }
  
  public void setDrag(boolean drag) {
    this.isDragging = drag;
  }
  
  public boolean isOpen() {
    return this.open;
  }
  
  public void setOpen(boolean open) {
    this.open = open;
  }
  
  public void renderFrame() {
    this.width = 110;
    Gui.func_73734_a(this.x, this.y - 3, this.x + this.width, this.y + this.barHeight - 2, ClickGUI.getHudColor());
    GL11.glPushMatrix();
    (Minecraft.func_71410_x()).field_71466_p.func_175063_a(StringRegistry.register(this.category.getName()), (getX() + getWidth() / 2 - (Minecraft.func_71410_x()).field_71466_p.func_78256_a(this.category.getName()) / 2), (float)(this.y + 1.5D), ClickGUI.getColor());
    (Minecraft.func_71410_x()).field_71466_p.func_175063_a(this.open ? "-" : "+", (this.x + getWidth() - 10), (float)(this.y + 1.5D), ClickGUI.getColor());
    GL11.glPopMatrix();
    if (this.open && !this.components.isEmpty())
      for (Component component : this.components)
        component.render();  
  }
  
  public void refresh() {
    int off = this.barHeight;
    for (Component comp : this.components) {
      comp.setOff(off);
      off += comp.getHeight();
    } 
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.barHeight;
  }
  
  public void updatePosition(int mouseX, int mouseY) {
    if (this.isDragging) {
      setX(mouseX - this.dragX);
      setY(mouseY - this.dragY);
    } 
  }
  
  public boolean isWithinHeader(int x, int y) {
    return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight);
  }
}