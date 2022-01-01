package me.vene.skilled.gui.parts;

import com.ibm.icu.math.BigDecimal;
import me.vene.skilled.gui.component.Component;
import me.vene.skilled.modules.mods.other.ClickGUI;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SliderPart extends Component {
  private NumberValue value;
  
  private ModulesPart parent;
  
  private int offset;
  
  private int x;
  
  private int y;
  
  public SliderPart(NumberValue value, ModulesPart modulesPart, int offset) {
    this.value = value;
    this.parent = modulesPart;
    this.x = modulesPart.parent.getX() + modulesPart.parent.getWidth();
    this.y = modulesPart.parent.getY() + modulesPart.offset;
    this.offset = offset;
  }
  
  public void render() {
    int drag = (int)(this.value.getValue() / this.value.getMax() * 104.0D);
    Gui.func_73734_a(this.parent.parent.getX() + 1, this.parent.parent.getY() - 2 + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 2, this.parent.parent.getY() + this.offset + 12, ClickGUI.getModuleColor());
    Gui.func_73734_a(this.parent.parent.getX() + 3, this.parent.parent.getY() + this.offset + 9, this.parent.parent.getX() + 1 + drag + 1, this.parent.parent.getY() + this.offset + 11, ClickGUI.getColor());
    GL11.glPushMatrix();
    (Minecraft.func_71410_x()).field_71466_p.func_78276_b(StringRegistry.register(this.value.getName()), this.parent.parent.getX() + 3, this.parent.parent.getY() + this.offset, -1);
    (Minecraft.func_71410_x()).field_71466_p.func_78276_b(StringRegistry.register(String.valueOf(this.value.getValue())), this.parent.parent.getX() + this.parent.parent.getWidth() - 3 - (Minecraft.func_71410_x()).field_71466_p.func_78256_a(String.valueOf(this.value.getValue())), this.parent.parent.getY() + this.offset, -1);
    GL11.glPopMatrix();
  }
  
  public void setOff(int newOff) {
    this.offset = newOff;
  }
  
  public void updateComponent(int mouseX, int mouseY) {
    boolean hovered = (isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY));
    this.y = this.parent.parent.getY() + this.offset;
    this.x = this.parent.parent.getX();
    if (hovered && !this.parent.parent.isDragging && this.parent.open && Mouse.isButtonDown(0)) {
      int drag = (int)(this.value.getMin() / this.value.getMax() * 104.0D);
      double diff = (mouseX - this.x);
      double value = round(MathUtil.map(diff, drag, (this.parent.parent.getWidth() - 5), this.value.getMin(), this.value.getMax()));
      this.value.setValue(value);
    } 
  }
  
  public void mouseClicked(int mouseX, int mouseY, int button) {
    if (isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
      NumberValue numberValue = this.value;
      double value = numberValue.getValue() - 0.1D;
      numberValue.setValue(Math.round(value * 10.0D) / 10.0D);
    } 
    if (isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
      NumberValue numberValue = this.value;
      double value = numberValue.getValue() + 0.1D;
      numberValue.setValue(Math.round(value * 10.0D) / 10.0D);
    } 
  }
  
  private double round(double doubleValue) {
    BigDecimal bigDecimal = new BigDecimal(doubleValue);
    bigDecimal = bigDecimal.setScale(2, 4);
    return bigDecimal.doubleValue();
  }
  
  public boolean isMouseOnButtonD(int x, int y) {
    return (x > this.x && x < this.x + this.parent.parent.getWidth() / 2 + 1 && y > this.y && y < this.y + 12);
  }
  
  public boolean isMouseOnButtonI(int x, int y) {
    return (x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12);
  }
}