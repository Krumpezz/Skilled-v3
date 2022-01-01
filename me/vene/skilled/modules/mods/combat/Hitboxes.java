package me.vene.skilled.modules.mods.combat;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.ClientUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;

public class HitBoxes extends Module {
  private Minecraft mc = Minecraft.func_71410_x();
  
  private String expandstring = new String(new char[] { 'E', 'x', 'p', 'a', 'n', 'd' });
  
  private NumberValue expandVal = new NumberValue(StringRegistry.register(this.expandstring), 0.0D, 0.0D, 1.0D);
  
  public HitBoxes() {
    super(StringRegistry.register(new String(new char[] { 'H', 'i', 't', 'B', 'o', 'x', 'e', 's' }, )), 0, Category.C);
    addValue(this.expandVal);
  }
  
  public void onMouseOver(float memes) {
    if (this.mc.field_71439_g == null || this.mc.field_71441_e == null || this.mc.field_71462_r != null)
      return; 
    if (this.mc.field_71439_g.func_70115_ae())
      return; 
    if (this.mc.field_71439_g.field_70128_L)
      return; 
    double expand = this.expandVal.getValue();
    MovingObjectPosition object = ClientUtil.getMouseOver(3.0D, expand, memes);
    if (object != null)
      this.mc.field_71476_x = object; 
  }
}