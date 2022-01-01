/*Its Delayremover idk why it has been named Delay17 in codes XD*/

package me.vene.skilled.modules.mods.combat;

import java.lang.reflect.Field;
import me.vene.skilled.modules.Category;
import me.vene.skilled.modules.Module;
import me.vene.skilled.utilities.StringRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Delay17 extends Module {
  private Field leftClickCounter;
  
  private Minecraft mc = Minecraft.func_71410_x();
  
  public Delay17() {
    super(StringRegistry.register(new String(new char[] { 
              'D', 'e', 'l', 'a', 'y', ' ', 'R', 'e', 'm', 'o', 
              'v', 'e', 'r' }, )), 0, Category.C);
    try {
      this.leftClickCounter = Minecraft.class.getDeclaredField("field_71429_W");
    } catch (Exception var4) {
      try {
        this.leftClickCounter = Minecraft.class.getDeclaredField("leftClickCounter");
      } catch (Exception exception) {}
    } 
    if (this.leftClickCounter != null)
      this.leftClickCounter.setAccessible(true); 
  }
  
  @SubscribeEvent
  public void onPlayerTick(TickEvent.PlayerTickEvent e) {
    if (this.mc.field_71439_g != null && this.mc.field_71441_e != null)
      try {
        this.leftClickCounter.set(this.mc, Integer.valueOf(0));
      } catch (IllegalAccessException illegalAccessException) {} 
  }
}