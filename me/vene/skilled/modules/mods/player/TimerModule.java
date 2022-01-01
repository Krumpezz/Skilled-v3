package me.vene.skilled.modules.mods.player;

import java.lang.reflect.Field;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TimerModule extends Module {
  private Field t;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  private NumberValue timerValue = new NumberValue(StringRegistry.register("Speed"), 1.07D, 0.1D, 2.0D);
  
  public TimerModule() {
    super(StringRegistry.register("Timer"), 0, Category.P);
    addValue(this.timerValue);
    try {
      this.t = Minecraft.class.getDeclaredField(new String(new char[] { 
              'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', 
              '8', '_', 'T' }));
    } catch (Exception er) {
      try {
        this.t = Minecraft.class.getDeclaredField(new String(new char[] { 't', 'i', 'm', 'e', 'r' }));
      } catch (Exception exception) {}
    } 
    if (this.t != null)
      this.t.setAccessible(true); 
  }
  
  public void onDisable() {
    (timerField()).field_74278_d = 1.0F;
  }
  
  public void onClientTick(TickEvent.ClientTickEvent event) {
    if (this.mc.field_71441_e != null && this.mc.field_71439_g != null)
      try {
        (timerField()).field_74278_d = (float)this.timerValue.getValue();
      } catch (NullPointerException nullPointerException) {} 
  }
  
  public Timer timerField() {
    try {
      return (Timer)this.t.get(this.mc);
    } catch (IllegalAccessException ex2) {
      Exception ex = null;
      Exception er = ex;
      return null;
    } 
  }
}