package me.vene.skilled.modules.mods.combat;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Velocity extends Module {
  private String hor = StringRegistry.register(new String(new char[] { 'H', 'o', 'r', 'i', 'z', 'o', 'n', 't', 'a', 'l' }));
  
  private String ver = StringRegistry.register(new String(new char[] { 'V', 'e', 'r', 't', 'i', 'c', 'a', 'l' }));
  
  private String chanceString = StringRegistry.register(new String(new char[] { 'C', 'h', 'a', 'n', 'c', 'e' }));
  
  private NumberValue HorizontalModifier = new NumberValue(StringRegistry.register(this.hor), 92.0D, 0.0D, 100.0D);
  
  private NumberValue VerticalModifier = new NumberValue(StringRegistry.register(this.ver), 100.0D, 0.0D, 100.0D);
  
  private NumberValue chanceValue = new NumberValue(StringRegistry.register(this.chanceString), 100.0D, 0.0D, 100.0D);
  
  private BooleanValue onlyTargetting = new BooleanValue(StringRegistry.register("Only Targetting"), true);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public Velocity() {
    super(StringRegistry.register(new String(new char[] { 'V', 'e', 'l', 'o', 'c', 'i', 't', 'y' }, )), 0, Category.C);
    addValue(this.HorizontalModifier);
    addValue(this.VerticalModifier);
    addValue(this.chanceValue);
    addOption(this.onlyTargetting);
  }
  
  public void onLivingUpdate(EntityPlayerSP player) {
    if ((Minecraft.func_71410_x()).field_71439_g == null)
      return; 
    if ((Minecraft.func_71410_x()).field_71441_e == null)
      return; 
    if (this.onlyTargetting.getState() && (this.mc.field_71476_x == null || this.mc.field_71476_x.field_72308_g == null))
      return; 
    if (MathUtil.random.nextInt(100) >= (int)this.chanceValue.getValue())
      return; 
    double vertmodifier = this.VerticalModifier.getValue() / 100.0D;
    double horzmodifier = this.HorizontalModifier.getValue() / 100.0D;
    if ((Minecraft.func_71410_x()).field_71439_g.field_70737_aN == (Minecraft.func_71410_x()).field_71439_g.field_70738_aO && (Minecraft.func_71410_x()).field_71439_g.field_70738_aO > 0) {
      this.mc.field_71439_g.field_70159_w *= horzmodifier;
      this.mc.field_71439_g.field_70179_y *= horzmodifier;
      this.mc.field_71439_g.field_70181_x *= vertmodifier;
    } 
  }
}