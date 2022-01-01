package me.vene.skilled.gui.parts;

import java.awt.Color;
import me.vene.skilled.gui.component.Component;
import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.main.CombatGUI;
import me.vene.skilled.modules.mods.main.OtherGUI;
import me.vene.skilled.modules.mods.main.PlayerGUI;
import me.vene.skilled.modules.mods.main.RenderGUI;
import me.vene.skilled.modules.mods.main.UtilityGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class KeybindPart extends Component {
  private boolean binding;
  
  private ModulesPart parent;
  
  private int offset;
  
  private int x;
  
  private int y;
  
  public KeybindPart(ModulesPart button, int offset) {
    this.parent = button;
    this.x = button.parent.getX() + button.parent.getWidth();
    this.y = button.parent.getY() + button.offset;
    this.offset = offset;
  }
  
  public void render() {
    Gui.func_73734_a(this.parent.parent.getX() + 1, this.parent.parent.getY() - 2 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, (new Color(35, 35, 35, 230)).getRGB());
    GL11.glPushMatrix();
    (Minecraft.func_71410_x()).field_71466_p.func_78276_b(this.binding ? "Press a key..." : ("Bind: " + Keyboard.getKeyName(this.parent.mod.getKey())), this.parent.parent.getX() + this.parent.parent.getWidth() / 2 - (Minecraft.func_71410_x()).field_71466_p.func_78256_a(this.binding ? "Press a key..." : ("Bind: " + Keyboard.getKeyName(this.parent.mod.getKey()))) / 2, this.parent.parent.getY() + this.offset + 1, -1);
    GL11.glPopMatrix();
  }
  
  public void updateComponent(int mouseX, int mouseY) {
    this.y = this.parent.parent.getY() + this.offset;
    this.x = this.parent.parent.getX();
  }
  
  public void mouseClicked(int mouseX, int mouseY, int button) {
    if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open)
      this.binding = !this.binding; 
  }
  
  public void keyTyped(char typedChar, int key) {
    if (this.binding && 
      this.parent.mod != Module.getModule(CombatGUI.class) && this.parent.mod != Module.getModule(OtherGUI.class) && this.parent.mod != Module.getModule(PlayerGUI.class) && this.parent.mod != Module.getModule(RenderGUI.class) && this.parent.mod != Module.getModule(UtilityGUI.class)) {
      if (key == 14) {
        this.parent.mod.setKey(0);
        this.binding = false;
        return;
      } 
      this.parent.mod.setKey(key);
      this.binding = false;
    } 
    if (key == 42) {
      this.parent.mod.setKey(0);
      this.binding = false;
    } 
  }
  
  public void setOff(int newOff) {
    this.offset = newOff;
  }
  
  public boolean isMouseOnButton(int x, int y) {
    return (x > this.x && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12);
  }
}