package me.vene.skilled.gui.parts;

import java.awt.Color;
import me.vene.skilled.gui.component.Component;
import me.vene.skilled.modules.mods.other.ClickGUI;
import me.vene.skilled.utilities.RenderUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class CheckboxPart extends Component {
  private BooleanValue op;
  
  private ModulesPart parent;
  
  private int offset;
  
  private int x;
  
  private int y;
  
  public CheckboxPart(BooleanValue option, ModulesPart modulesPart, int offset) {
    this.op = option;
    this.parent = modulesPart;
    this.x = modulesPart.parent.getX() + modulesPart.parent.getWidth();
    this.y = modulesPart.parent.getY() + modulesPart.offset;
    this.offset = offset;
  }
  
  public void render() {
    Gui.func_73734_a(this.parent.parent.getX() + 1, this.parent.parent.getY() - 2 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, ClickGUI.getModuleColor());
    Gui.func_73734_a(this.parent.parent.getX() + 1, this.parent.parent.getY() + 12 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset, ClickGUI.getModuleColor());
    GL11.glPushMatrix();
    (Minecraft.func_71410_x()).field_71466_p.func_78276_b(StringRegistry.register(this.op.getName()), this.parent.parent.getX() + 15, this.parent.parent.getY() + this.offset, -1);
    GL11.glPopMatrix();
    Gui.func_73734_a(this.parent.parent.getX() + 4, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 12, this.parent.parent.getY() + this.offset + 8, (new Color(0, 0, 0, 255)).getRGB());
    if (this.op.getState())
      RenderUtil.drawCheckmark((float)(this.parent.parent.getX() + 4.5D), (this.parent.parent.getY() + this.offset + 3), -1); 
  }
  
  public void setOff(int newOff) {
    this.offset = newOff;
  }
  
  public void updateComponent(int mouseX, int mouseY) {
    this.y = this.parent.parent.getY() + this.offset;
    this.x = this.parent.parent.getX();
  }
  
  public void mouseClicked(int mouseX, int mouseY, int button) {
    boolean hovered = isMouseOnButton(mouseX, mouseY);
    if (hovered && button == 0 && this.parent.open)
      this.op.toggle(); 
  }
  
  private boolean isMouseOnButton(int x, int y) {
    return (x > this.x && x < this.x + 105 && y > this.y && y < this.y + 12);
  }
}