package me.vene.skilled.modules.mods.player;

import java.lang.reflect.Field;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastPlace extends Module {
  private Field delayField;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private NumberValue delayValue = new NumberValue(StringRegistry.register("Delay"), 4.0D, 0.0D, 5.0D);
  
  private BooleanValue onlyBlocks = new BooleanValue(StringRegistry.register("Only Blocks"), false);
  
  public FastPlace() {
    super(StringRegistry.register("FastPlace"), 0, Category.P);
    addValue(this.delayValue);
    addOption(this.onlyBlocks);
    try {
      this.delayField = this.mc.getClass().getDeclaredField(new String(new char[] { 
              'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '6', 
              '7', '_', 'a', 'c' }));
    } catch (Exception e) {
      try {
        this.delayField = this.mc.getClass().getDeclaredField(new String(new char[] { 
                'r', 'i', 'g', 'h', 't', 'C', 'l', 'i', 'c', 'k', 
                'D', 'e', 'l', 'a', 'y', 'T', 'i', 'm', 'e', 'r' }));
      } catch (Exception exception) {}
    } 
    if (this.delayField != null)
      this.delayField.setAccessible(true); 
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (event.phase != TickEvent.Phase.START)
      return; 
    if (this.mc.field_71441_e == null)
      return; 
    if (this.mc.field_71439_g == null)
      return; 
    try {
      if (this.onlyBlocks.getState() && (
        this.mc.field_71439_g.func_71045_bC() == null || !(this.mc.field_71439_g.func_71045_bC().func_77973_b() instanceof net.minecraft.item.ItemBlock)))
        return; 
      try {
        if (this.delayField.getInt(Minecraft.func_71410_x()) > this.delayValue.getValue())
          this.delayField.set(Minecraft.func_71410_x(), Integer.valueOf((int)this.delayValue.getValue())); 
      } catch (IllegalAccessException illegalAccessException) {}
    } catch (Exception exception) {}
  }
}