package me.vene.skilled.gui.parts;

import java.awt.Color;
import java.util.ArrayList;
import me.vene.skilled.gui.component.Component;
import me.vene.skilled.gui.component.Frame;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.main.CombatGUI;
import me.vene.skilled.modules.mods.main.OtherGUI;
import me.vene.skilled.modules.mods.main.PlayerGUI;
import me.vene.skilled.modules.mods.main.RenderGUI;
import me.vene.skilled.modules.mods.main.UtilityGUI;
import me.vene.skilled.modules.mods.other.ClickGUI;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModulesPart extends Component {
  public Module mod;
  
  public Frame parent;
  
  public int offset;
  
  private boolean isHovered;
  
  private ArrayList<Component> subcomponents;
  
  public boolean open;
  
  public ModulesPart(Module mod, Frame parent, int offset) {
    this.mod = mod;
    this.parent = parent;
    this.offset = offset;
    this.subcomponents = new ArrayList<Component>();
    this.open = false;
    int opY = offset + 16;
    if (mod != Module.getModule(CombatGUI.class) && mod != Module.getModule(OtherGUI.class) && mod != Module.getModule(PlayerGUI.class) && mod != Module.getModule(RenderGUI.class) && mod != Module.getModule(UtilityGUI.class)) {
      KeybindPart keybindPart = new KeybindPart(this, opY);
      this.subcomponents.add(keybindPart);
    } 
    opY += 4;
    if (!mod.getOptions().isEmpty())
      for (BooleanValue bool : mod.getOptions()) {
        CheckboxPart check = new CheckboxPart(bool, this, opY);
        this.subcomponents.add(check);
        opY += 12;
      }  
    opY += 2;
    if (!mod.getValues().isEmpty())
      for (NumberValue num : mod.getValues()) {
        SliderPart sliderPart = new SliderPart(num, this, opY);
        this.subcomponents.add(sliderPart);
        opY += 14;
      }  
  }
  
  public void setOff(int newOff) {
    this.offset = newOff;
    int opY = this.offset + 14;
    for (Component comp : this.subcomponents) {
      comp.setOff(opY);
      opY += 14;
    } 
  }
  
  public void render() {
    Gui.func_73734_a(this.parent.getX(), this.parent.getY() - 2 + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.mod.getState() ? ClickGUI.getColor() : ClickGUI.getModuleColor());
    (Minecraft.func_71410_x()).field_71466_p.func_78276_b(StringRegistry.register(this.mod.getName()), this.parent.getX() + this.parent.getWidth() / 2 - (Minecraft.func_71410_x()).field_71466_p.func_78256_a(this.mod.getName()) / 2, this.parent.getY() + 1 + this.offset, -1);
    if (!this.mod.getOptions().isEmpty() || !this.mod.getValues().isEmpty())
      (Minecraft.func_71410_x()).field_71466_p.func_78276_b(this.open ? new String(new char[] { '-' }, ) : new String(new char[] { '+' }, ), this.parent.getX() + this.parent.getWidth() - 10, this.parent.getY() + this.offset + 1, this.isHovered ? -1 : (new Color(150, 150, 150, 255)).getRGB()); 
    if (this.open && !this.subcomponents.isEmpty())
      for (Component comp : this.subcomponents)
        comp.render();  
  }
  
  public int getHeight() {
    if (this.open)
      return 14 * (this.subcomponents.size() + 1); 
    return 14;
  }
  
  public void updateComponent(int mouseX, int mouseY) {
    this.parent.refresh();
    this.isHovered = isMouseOnButton(mouseX, mouseY);
    if (!this.subcomponents.isEmpty())
      for (Component comp : this.subcomponents)
        comp.updateComponent(mouseX, mouseY);  
  }
  
  public void mouseClicked(int mouseX, int mouseY, int button) {
    if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open)
      this.mod.setState(!this.mod.getState()); 
    if (isMouseOnButton(mouseX, mouseY) && button == 1 && this.parent.open) {
      this.open = !this.open;
      this.parent.refresh();
    } 
    for (Component comp : this.subcomponents)
      comp.mouseClicked(mouseX, mouseY, button); 
  }
  
  public void keyTyped(char typedChar, int key) {
    for (Component comp : this.subcomponents)
      comp.keyTyped(typedChar, key); 
  }
  
  private boolean isMouseOnButton(int x, int y) {
    return (x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 14 + this.offset);
  }
}