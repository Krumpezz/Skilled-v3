package me.vene.skilled.modules.mods.player;

import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.MathUtil;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WTap extends Module {
  private long nextTapDown = 0L;
  
  private long nextTapUp = 0L;
  
  private NumberValue chanceValue = new NumberValue("Chance", 60.0D, 1.0D, 100.0D);
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public WTap() {
    super(StringRegistry.register("WTap"), 0, Category.C);
    addValue(this.chanceValue);
  }
  
  public void onHurtAnimation() {
    if (this.mc.field_71439_g.field_70128_L)
      return; 
    if (this.mc.field_71441_e == null || this.mc.field_71462_r != null)
      return; 
    if (!this.mc.field_71439_g.func_70051_ag())
      return; 
    if (this.chanceValue.getValue() >= MathUtil.random.nextInt(100) && this.nextTapUp == 0L && this.nextTapDown == 0L)
      this.nextTapUp = System.currentTimeMillis() + 40L + MathUtil.random.nextInt(325); 
  }
  
  public void onRenderTick(TickEvent.RenderTickEvent renderTickEvent) {
    if (this.mc.field_71439_g == null || this.mc.field_71441_e == null || this.mc.field_71462_r != null)
      return; 
    if (this.mc.field_71439_g.field_70128_L)
      return; 
    if (!this.mc.field_71439_g.func_70051_ag() && this.nextTapUp > 0L) {
      this.nextTapUp = 0L;
      return;
    } 
    if (System.currentTimeMillis() - this.nextTapUp > 0L && this.nextTapUp != 0L) {
      int forwardKey = this.mc.field_71474_y.field_74351_w.func_151463_i();
      KeyBinding.func_74510_a(forwardKey, false);
      KeyBinding.func_74507_a(forwardKey);
      this.nextTapDown = System.currentTimeMillis() + 90L + MathUtil.random.nextInt(50);
      this.nextTapUp = 0L;
    } else if (System.currentTimeMillis() - this.nextTapDown > 0L && this.nextTapDown != 0L) {
      int forwardKey = this.mc.field_71474_y.field_74351_w.func_151463_i();
      KeyBinding.func_74510_a(forwardKey, true);
      KeyBinding.func_74507_a(forwardKey);
      this.nextTapDown = 0L;
    } 
  }
}